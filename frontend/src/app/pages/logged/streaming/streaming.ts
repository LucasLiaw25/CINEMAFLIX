import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { Card } from '../../../components/logged/card/card';
import { Movies } from '../../../interfaces/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../../service/movie.service';
import { Streaming } from '../../../interfaces/movie';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { Series } from '../../../interfaces/serie';
import { SerieService } from '../../../service/serie.service';
import { forkJoin, map } from 'rxjs'; // Importar forkJoin
import { UserListService } from '../../../service/user-list';

@Component({
  selector: 'app-streaming',
  imports: [CommonModule, FormsModule, Header, Footer, Card, SerieCard],
  templateUrl: './streaming.html',
  styleUrl: './streaming.css'
})
export class Streamings implements OnInit {

  series:Series[] = [];
  movies: Movies[] = [];
  streaming: Streaming | null = null;
  streamings: Streaming[] = [];
  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private movieService: MovieService,
    private serieService:SerieService,
    private ulService:UserListService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const streamingId = Number(params.get('id'));
      if (streamingId) {
        this.loadStreamingContent(streamingId); 
      }
    });
    this.loadAllStreamings();
  }

  loadStreamingContent(streamingId: number): void {
    this.loading = true;
    this.errorMessage = '';
    this.movies = []; 
    this.series = []; 
    this.streaming = null; 

    const userList$ = this.ulService.getUserList().pipe(
      map(response => ({
        movieIds: response.movies.map((m: Movies) => m.id), 
        seriesIds: response.series.map((s: Series) => s.id) 
      }))
    );

    forkJoin({
      movies: this.movieService.getMoviesByStreaming(streamingId),
      series: this.serieService.getSeriesByStreaming(streamingId),
      streamingDetails: this.movieService.getStreamingById(streamingId),
      userWatchlist: userList$ 
    }).subscribe({
      next: ({ movies, series, streamingDetails, userWatchlist }) => {
        this.movies = movies.map(movie => ({
          ...movie,
          isWatchlisted: userWatchlist.movieIds.includes(movie.id)
        }));

        this.series = series.map(serie => ({
          ...serie,
          isInWatchlist: userWatchlist.seriesIds.includes(serie.id)
        }));
        
        this.streaming = streamingDetails;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar conteúdo do streaming:', error);
        this.errorMessage = 'Erro ao carregar filmes e séries do streaming.';
        this.loading = false;
      }
    });
  }

  loadAllStreamings(): void {
    this.movieService.getStreaming().subscribe({
      next: (streamings) => {
        this.streamings = streamings;
      },
      error: (error) => {
        console.error('Error loading streamings:', error);
      }
    });
  }

  onStreamingSelect(streaming: Streaming): void {
    this.router.navigate(['/streaming', streaming.id]);
  }

  goBack(): void {
    this.router.navigate(['/home']);
  }

  getStreamingName(streamingId: number): string {
    const streaming = this.streamings.find(str => str.id === streamingId);
    return streaming ? streaming.name : 'Streaming Desconhecido';
  }
}
