import { Component, OnInit } from '@angular/core';
import { Cateogry, Movies } from '../../../interfaces/movie';
import { ActivatedRoute, Router } from '@angular/router';
import { MovieService } from '../../../service/movie.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { Card } from '../../../components/logged/card/card';
import { Series } from '../../../interfaces/serie';
import { SerieService } from '../../../service/serie.service';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { forkJoin, map } from 'rxjs';
import { UserListResponse, UserListService } from '../../../service/user-list';

@Component({
  selector: 'app-category',
  imports: [CommonModule, FormsModule, Header, Footer, Card, SerieCard],
  templateUrl: './category.html',
  styleUrl: './category.css'
})
export class Category implements OnInit{
  series:Series[] = [];
  movies: Movies[] = [];
  category: Cateogry | null = null;
  categories: Cateogry[] = [];
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
      const categoryId = Number(params.get('id'));
      if (categoryId) {
        this.loadStreamingContent(categoryId); 
      }
    });
    this.loadAllCategories();
  }

  loadStreamingContent(categoryId: number): void {
    this.loading = true;
    this.errorMessage = '';
    this.movies = []; 
    this.series = []; 
    this.category = null; 

    const userList$ = this.ulService.getUserList().pipe(
      map(response => ({
        movieIds: response.movies.map((m: Movies) => m.id), 
        seriesIds: response.series.map((s: Series) => s.id) 
      }))
    );

    forkJoin({
      movies: this.movieService.getMoviesByCategory(categoryId),
      series: this.serieService.getSeriesByCategory(categoryId),
      categoryDetails: this.movieService.getCategoryById(categoryId),
      userWatchlist: userList$ 
    }).subscribe({
      next: ({ movies, series, categoryDetails, userWatchlist }) => {
        this.movies = movies.map(movie => ({
          ...movie,
          isWatchlisted: userWatchlist.movieIds.includes(movie.id)
        }));

        this.series = series.map(serie => ({
          ...serie,
          isInWatchlist: userWatchlist.seriesIds.includes(serie.id)
        }));
        
        this.category = categoryDetails;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar conteúdo do streaming:', error);
        this.errorMessage = 'Erro ao carregar filmes e séries do streaming.';
        this.loading = false;
      }
    });
  }


  loadAllCategories(): void {
    this.movieService.getCategory().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
      }
    });
  }

  onCategorySelect(category: Cateogry): void {
    this.router.navigate(['/category', category.id]);
  }

  goBack(): void {
    this.router.navigate(['/home']);
  }

  getCategoryName(categoryId: number): string {
    const category = this.categories.find(cat => cat.id === categoryId);
    return category ? category.name : 'Categoria Desconhecida';
  }
}
