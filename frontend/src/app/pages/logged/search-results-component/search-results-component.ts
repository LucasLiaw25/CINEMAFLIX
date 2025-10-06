import { CommonModule } from '@angular/common';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Footer } from '../../../components/logged/footer/footer';
import { Header } from '../../../components/logged/header/header';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Subscription, forkJoin, map } from 'rxjs'; // ✅ Importe forkJoin
import { MovieService } from '../../../service/movie.service';
import { Movies } from '../../../interfaces/movie';
import { Card } from '../../../components/logged/card/card';
import { SerieService } from '../../../service/serie.service';
import { Series } from '../../../interfaces/serie';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { UserListService } from '../../../service/user-list';

@Component({
  selector: 'app-search-results-component',
  imports: [CommonModule, FormsModule, Footer, Header, Card, SerieCard],
  templateUrl: './search-results-component.html',
  styleUrl: './search-results-component.css'
})
export class SearchResultsComponent implements OnInit, OnDestroy {
  movies: Movies[] = [];
  series: Series[] = [];
  searchTerm: string = '';
  loading: boolean = true;
  errorMessage: string = '';
  private routeSub!: Subscription;

  constructor(
    private route: ActivatedRoute, 
    private movieService: MovieService,
    private serieService: SerieService, 
    public router: Router,
    private ulService:UserListService
  ) {}

  ngOnInit(): void {
    this.routeSub = this.route.queryParams.subscribe(params => {
      const term = params['title'] || '';
      
      console.log('🔍 Parâmetros recebidos:', params);
      console.log('🔍 Termo de busca:', term);
      
      if (term) {
        this.searchTerm = term;
        this.performSearch(term);
      } else {
        this.loading = false;
        this.errorMessage = 'Nenhum termo de busca fornecido';
      }
    });
  }

  performSearch(term: string): void {
    this.loading = true;
    this.errorMessage = '';
    this.movies = [];
    this.series = [];
    
    console.log('🚀 Iniciando busca com termo:', term);
    
    const searchParams = { title: term };
    const userWatchlist$ = this.ulService.getUserList().pipe(
      map(response => ({
        movieIds: response.movies.map((m: Movies) => m.id),
        seriesIds: response.series.map((s: any) => s.id) 
      }))
    );

    forkJoin({
      movies: this.movieService.searchMovie(searchParams),
      series: this.serieService.searchSerie(searchParams),
      userWatchlist: userWatchlist$
    }).subscribe({
      next: (results) => {
        this.movies = results.movies.map(movie => ({
          ...movie,
          isWatchlisted: results.userWatchlist.movieIds.includes(movie.id)
        }));
        this.series = results.series.map(serie => ({
          ...serie,
          isInWatchlist: results.userWatchlist.seriesIds.includes(serie.id)
        }));

        this.loading = false;
        
        console.log('✅ Busca completa - Filmes:', this.movies.length, 'Séries:', this.series.length);
      },
      error: (err) => {
        console.error('❌ Erro na busca:', err);
        this.errorMessage = 'Erro ao buscar conteúdo';
        this.loading = false;
        this.movies = [];
        this.series = [];
      }
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }
}