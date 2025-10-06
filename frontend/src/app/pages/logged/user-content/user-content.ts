import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MovieService } from '../../../service/movie.service';
import { SerieService } from '../../../service/serie.service';
import { UserListService } from '../../../service/user-list';
import { Series } from '../../../interfaces/serie';
import { Movies } from '../../../interfaces/movie';
import { forkJoin, map } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Footer } from '../../../components/logged/footer/footer';
import { Card } from '../../../components/logged/card/card';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { Header } from '../../../components/logged/header/header';

@Component({
  selector: 'app-user-content',
  imports: [CommonModule, Header, Footer, Card, SerieCard],
  templateUrl: './user-content.html',
  styleUrl: './user-content.css'
})
export class UserContent implements OnInit{

  constructor(
    private movieService:MovieService,
    private cdr:ChangeDetectorRef,
    private serieService:SerieService,
    private ulService:UserListService
  ){}

  series:Series[] = [];
  movies:Movies[] = [];
  loading:boolean = true;
  errorMessage:string = "";

  ngOnInit(): void {
    this.loadMovie();
    this.loadSerie();
  }

  loadMovie(): void {
    this.loading = true;
    this.errorMessage = "";

    const userMovies$ = this.movieService.getByUser();

    const watchlistIds$ = this.ulService.getUserList().pipe(
      map(response => response.movies.map((movie: Movies) => movie.id))
    );

    forkJoin([
      userMovies$,
      watchlistIds$
    ]).subscribe({
      next: ([userMovies, watchlistIds]) => {
        this.movies = userMovies.map(movie => ({
          ...movie,
          isWatchlisted: watchlistIds.includes(movie.id)
        }));
        
        this.loading = false;
        this.cdr.detectChanges(); 
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = "Erro ao carregar os filmes Top 10";
        this.movies = []; 
        console.error("Error loading top 10 movies", error);
      }
    });
  }


  loadSerie(): void {
    this.loading = true;
    this.errorMessage = "";

    const userSeries$ = this.serieService.getByUser();

    const watchlistIds$ = this.ulService.getUserList().pipe(
      map(response => response.movies.map((movie: Movies) => movie.id))
    );

    forkJoin([
      userSeries$,
      watchlistIds$
    ]).subscribe({
      next: ([userSeries, watchlistIds]) => {
        this.series = userSeries.map(serie => ({
          ...serie,
          isInWatchlist: watchlistIds.includes(serie.id)
        }));
        
        this.loading = false;
        this.cdr.detectChanges(); 
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = "Erro ao carregar os filmes Top 10";
        this.movies = []; 
        console.error("Error loading top 10 movies", error);
      }
    });
  }

}
