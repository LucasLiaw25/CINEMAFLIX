import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { forkJoin, map } from 'rxjs';
import { MovieService } from '../../../service/movie.service';
import { SerieService } from '../../../service/serie.service';
import { UserListService } from '../../../service/user-list';
import { Series } from '../../../interfaces/serie';
import { Movies } from '../../../interfaces/movie';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { Card } from '../../../components/logged/card/card';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-find-user-content',
  imports: [Header, Footer, Card, SerieCard, CommonModule],
  templateUrl: './find-user-content.html',
  styleUrl: './find-user-content.css'
})
export class FindUserContent implements OnInit{

  constructor(
    private movieService:MovieService,
    private cdr:ChangeDetectorRef,
    private serieService:SerieService,
    private ulService:UserListService,
    private route:ActivatedRoute
  ){}

  series:Series[] = [];
  movies:Movies[] = [];
  loading:boolean = true;
  errorMessage:string = "";

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      this.loadMovie(+id);
      this.loadSerie(+id);
    }
  }

  loadMovie(id:number): void {
    this.loading = true;
    this.errorMessage = "";

    const userMovies$ = this.movieService.getByUserId(id);

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
        this.errorMessage = "Erro ao carregar os filmes";
        this.movies = []; 
        console.error("Error loading  movies", error);
      }
    });
  }


  loadSerie(id:number): void {
    this.loading = true;
    this.errorMessage = "";

    const userSeries$ = this.serieService.getByUserId(id);

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
        this.errorMessage = "Erro ao carregar os filmes";
        this.movies = []; 
        console.error("Error loading movies", error);
      }
    });
  }
}
