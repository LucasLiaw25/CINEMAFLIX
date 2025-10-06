import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Footer } from '../../../components/logged/footer/footer';
import { Card } from '../../../components/logged/card/card';

import { MovieService } from '../../../service/movie.service';
import { Header } from '../../../components/logged/header/header';
import { Movies } from '../../../interfaces/movie';
import { SerieService } from '../../../service/serie.service';
import { Series } from '../../../interfaces/serie';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { forkJoin, map } from 'rxjs';
import { UserListService } from '../../../service/user-list';

@Component({
  selector: 'app-top-ten',
  imports: [CommonModule, FormsModule, Header, Footer, Card, SerieCard],
  templateUrl: './top-ten.html',
  styleUrl: './top-ten.css'
})
export class TopTen implements OnInit{

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

    const top10Movies$ = this.movieService.getTop10Movie();

    const watchlistIds$ = this.ulService.getUserList().pipe(
      map(response => response.movies.map((movie: Movies) => movie.id))
    );

    forkJoin([
      top10Movies$,
      watchlistIds$
    ]).subscribe({
      next: ([top10Movies, watchlistIds]) => {
        this.movies = top10Movies.map(movie => ({
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

    const top10Movies$ = this.serieService.top10Series();

    const watchlistIds$ = this.ulService.getUserList().pipe(
      map(response => response.movies.map((movie: Movies) => movie.id))
    );

    forkJoin([
      top10Movies$,
      watchlistIds$
    ]).subscribe({
      next: ([top10Series, watchlistIds]) => {
        this.series = top10Series.map(serie => ({
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
