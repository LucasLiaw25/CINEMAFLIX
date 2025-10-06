import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { SerieCard } from '../../../components/logged/serie-card/serie-card';
import { SerieService } from '../../../service/serie.service';
import { Series } from '../../../interfaces/serie';
import { forkJoin, map } from 'rxjs';
import { UserListService } from '../../../service/user-list';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-serie',
  imports: [CommonModule, FormsModule, Header, Footer, SerieCard],
  templateUrl: './serie.html',
  styleUrl: './serie.css'
})
export class Serie implements OnInit{

    constructor(
      private serieService:SerieService,
      private cdr: ChangeDetectorRef,
      private ulService:UserListService,
      private route:ActivatedRoute
    ){}

    series:Series[]=[];
    loading:boolean = true;
    errorMessage:string = "";
    currentPage:number = 0;
    pageSize:number = 9;
    totalPages:number = 0;
    totalItems:number = 0

    ngOnInit(): void {
      this.route.queryParams.subscribe(params=>{
        const pageFromUrl = params['page'] ? +params['page'] : 0;
        
        this.currentPage = pageFromUrl;
        this.loadSerie(this.currentPage);
      });
    }

    loadSerie(page:number): void {
          this.loading = true;
          this.errorMessage = "";
        
          forkJoin([
            this.serieService.getSeriePage(page, this.pageSize),
            this.ulService.getUserList().pipe(
              map(response => response.series.map(serie => serie.id))
            )
          ]).subscribe({
            next: ([seriePage, watchlistIds]) => {
              this.series = seriePage.content.map(series => ({
                ...series,
                isInWatchlist: watchlistIds.includes(series.id)
              }));
              this.currentPage = seriePage.number;
              this.pageSize = seriePage.size;
              this.totalPages = seriePage.totalPages;
              this.totalItems = seriePage.totalElements;
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
            if (this.series.length > 0) {
                 this.loading = false;
                 this.cdr.detectChanges();
            }
          }

      goToNextPage():void{
      const nextPage = this.currentPage + 1;
      if(nextPage < this.totalPages){
        this.loadSerie(nextPage);
      }
    }

    goToPreviousPage():void{
      const previousPage = this.currentPage - 1;
      if(previousPage>=0){
        this.loadSerie(previousPage);
      }
    }

}
