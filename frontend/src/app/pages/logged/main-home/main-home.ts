import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Card } from "../../../components/logged/card/card"; 
import { Footer } from "../../../components/logged/footer/footer";
import { Header } from "../../../components/logged/header/header";
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { Series } from '../../../interfaces/serie';
import { MovieService } from '../../../service/movie.service';
import { SerieService } from '../../../service/serie.service';
import { Cateogry, Movies, Streaming } from '../../../interfaces/movie';
import { Router, RouterLink } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { UserListService } from '../../../service/user-list';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-movie',
  imports: [CommonModule, FormsModule, Footer, Card, Header, SerieCard, RouterLink], 
  templateUrl: './main-home.html',
  styleUrl: './main-home.css',
  standalone: true 
})
export class MainHome implements OnInit {

  username: string = 'Cinefilo';
  loading: boolean = true;
  errorMessage: string = ""; 
  categories: Cateogry[] = [];
  streamings:Streaming[] = [];
  movies: Movies[] = []; 
  series: Series[] = []; 

  constructor(
    private movieService: MovieService,
    private seriesService: SerieService,
    private cdr: ChangeDetectorRef,
    private router:Router,
    private ulService:UserListService,
    private authService:AuthService
  ) { }

  ngOnInit(): void {
    this.fetchUserName();
    this.loadMovie();
    this.loadSerie();
    this.loadCategories();
    this.loadStreamings();
  }

  fetchUserName(): void {
    this.authService.getUsername().subscribe({
      next:(response)=>{
        this.username = response.username;
        this.cdr.detectChanges();
      },
      error:(error)=>{
        console.log("Erro ao carregar o nome do usuário");
      }
    });
  }

  continueWatching(): void {
    this.router.navigate(['/create/movie']);
  }
  continueWatching2(): void {
    this.router.navigate(['/create/series']);
  }

  loadMovie(): void {
  this.loading = true;
  this.errorMessage = "";

  forkJoin([
    this.movieService.getAllMovies(),
    this.ulService.getUserList().pipe(
      map(response => response.movies.map(movie => movie.id))
    )
  ]).subscribe({
    next: ([movies, watchlistIds]) => {
      this.movies = movies.map(movie => ({
        ...movie,
        isWatchlisted: watchlistIds.includes(movie.id)
      }));
      this.checkIfLoadingComplete();
    },
    error: (error) => {
      this.loading = false;
      this.errorMessage = "Erro ao carregar os filmes";
      console.error("Erro ao carregar os filmes", error);
    }
  });
}
checkIfLoadingComplete(): void {
    if (this.movies.length > 0 || this.series.length > 0) {
         this.loading = false;
         this.cdr.detectChanges();
    }
  }

  loadSerie(): void {

  forkJoin([
    this.seriesService.getSerie(), 
    this.ulService.getUserList().pipe(
      map(response => response.series.map(serie => serie.id))
    )
  ]).subscribe({
    next: ([series, watchlistSerieIds]) => {
      this.series = series.map(serie => ({
        ...serie,
        isInWatchlist: watchlistSerieIds.includes(serie.id)
      }));
      this.checkIfLoadingComplete();
    },
    error: (error) => {
      this.loading = false;
      this.errorMessage = "Erro ao carregar as séries";
      console.error("Erro ao carregar as séries", error);
    }
  });
}

  

  loadCategories(): void {
    this.movieService.getCategory().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
  }

  loadStreamings():void{
    this.movieService.getStreaming().subscribe({
      next:(streamings)=>{
        this.streamings = streamings;
      },
      error:(error)=>{
        console.log('Error loading streamings:', error);
      }
    });
  }

  
}
