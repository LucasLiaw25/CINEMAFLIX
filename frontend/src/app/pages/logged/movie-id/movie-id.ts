import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MoviesIndividual } from '../../../interfaces/movie'; // Usando a interface detalhada
import { MovieService } from '../../../service/movie.service';
import { ActivatedRoute } from '@angular/router';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Card } from '../../../components/logged/card/card';
import { MovidIndividuals } from '../../../components/logged/movid-individual/movid-individual';


@Component({
  selector: 'app-movie-id',
  standalone: true, 
  imports: [Header, Footer, FormsModule, CommonModule, MovidIndividuals],
  templateUrl: './movie-id.html',
  styleUrl: './movie-id.css'
})
export class MovieId implements OnInit{

  movie: MoviesIndividual | null = null; 
  loading:boolean = true;
  errorMessage:string = "";

  constructor(
    private movieService:MovieService,
    private cdr:ChangeDetectorRef,
    private route:ActivatedRoute
  ){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      const movieId = +id;
      this.loadMovie(movieId);
    }else {
        this.loading = false;
        this.errorMessage = "ID do filme não fornecido na rota.";
    }
  }

  loadMovie(id:number){
    this.loading = true;
    this.errorMessage = "";

    this.movieService.getMovieById(id).subscribe({ 
        next:(m:MoviesIndividual)=>{ 
          this.loading = false;
          this.movie = m; 
          console.log("--- Objeto de Filme Recebido ---");
          console.log(m);
          console.log("Atores:", m.actors);
          console.log("Gêneros:", m.categories);
          console.log("Streaming", m.streamings);
          console.log("Línguas", m.languages);
        },
        error:(error)=>{
          this.loading = false;
          this.movie = null;
          this.errorMessage = "Erro ao carregar detalhes do filme. Verifique se o ID existe.";
          console.log("Error loading movie", error);
        }
    });
  }
}
