import { CommonModule } from "@angular/common";
import { ChangeDetectorRef, Component, OnInit } from "@angular/core";
import { Header } from "../logged/header/header";
import { Footer } from "../logged/footer/footer";
import { Card } from "../logged/card/card";
import { SerieCard } from "../logged/serie-card/serie-card";
import { UserListResponse, UserListService } from "../../service/user-list";
import { Movies } from "../../interfaces/movie";
import { Series } from "../../interfaces/serie";

@Component({
  selector: 'app-user-list',
  imports: [CommonModule, Header, Footer, Card, SerieCard],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css'
})
export class UserList implements OnInit {

  constructor(
    private ulService: UserListService,
    private cdr: ChangeDetectorRef
  ) { }

  movies: Movies[] = [];
  series: Series[] = [];
  loading: boolean = true;
  errorMessage: string = "";

  ngOnInit(): void {
    this.ulService.getUserList().subscribe({
      next: (req: UserListResponse) => {
        this.movies = req.movies.map(movie => ({ ...movie, isWatchlisted: true }));
        this.series = req.series.map(serie => ({ ...serie, isInWatchlist: true }));
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = "Erro ao carregar a lista do usuário";
        console.error("Erro ao carregar séries e filmes", error);
      }
    });
  }
}