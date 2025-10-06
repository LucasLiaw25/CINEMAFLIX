import { ChangeDetectorRef, Component, HostListener, Input, OnInit } from '@angular/core';
import { Movies } from '../../../interfaces/movie';
import { Card } from '../../../components/logged/card/card';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MovieService } from '../../../service/movie.service';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { UserListService } from '../../../service/user-list';
import { forkJoin, map } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [Card, CommonModule, FormsModule, Header, Footer],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit{
  
  constructor(
    private movieService:MovieService, 
    private cdr: ChangeDetectorRef,
    private ulService:UserListService,
    private route:ActivatedRoute
  ){}

  movies:Movies[] = [];
  loading:boolean = true;
  errorMessage:string = "";
  currentPage:number = 0;
  pageSize:number = 9;
  totalPages:number = 0;
  totalItems:number = 0
  
  ngOnInit(): void {
      this.route.queryParams.subscribe(params => {
        const pageFromUrl = params['page'] ? +params['page'] : 0;
        
        this.currentPage = pageFromUrl;
        this.loadMovie(this.currentPage);
    });
  }

    loadMovie(page:number): void {
      this.loading = true;
      this.errorMessage = "";
    
      forkJoin([
        this.movieService.getMovies(page, this.pageSize),
        this.ulService.getUserList().pipe(
          map(response => response.movies.map(movie => movie.id))
        )
      ]).subscribe({
        next: ([moviePage, watchlistIds]) => {
          console.log("Movie Page Recebida:", moviePage);
          console.log("Conteúdo (moviePage.content):", moviePage.content); 
          this.movies = moviePage.content.map(movie => ({
            ...movie,
            isWatchlisted: watchlistIds.includes(movie.id)
          }));
          this.currentPage = moviePage.number;
          this.totalPages = moviePage.totalPages;
          this.totalItems = moviePage.totalElements;
          this.pageSize = moviePage.size;
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
        if (this.movies.length > 0) {
             this.loading = false;
             this.cdr.detectChanges();
        }
      }

    goToNextPage():void{
      const nextPage = this.currentPage + 1;
      if(nextPage < this.totalPages){
        this.loadMovie(nextPage);
      }
    }

    goToPreviousPage():void{
      const previousPage = this.currentPage - 1;
      if(previousPage>=0){
        this.loadMovie(previousPage);
      }
    }

}
  
  


