import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Route, Router } from '@angular/router';
import { UserListService } from '../../../service/user-list';

@Component({
  selector: 'app-serie-card',
  imports: [CommonModule],
  templateUrl: './serie-card.html',
  styleUrl: './serie-card.css'
})
export class SerieCard {
  @Input() id: number | null = null; 
  @Input() title:string = "";
  @Input() description:string="";
  @Input() duration:number = 0;
  @Input() rating:number = 0;
  @Input() age_rating:number = 0;
  @Input() episode:number = 0;
  @Input() season:number = 0;
  @Input() top10:boolean = false;
  @Input() isInWatchlist: boolean = false;

  constructor(
    private router:Router,
    private ulService:UserListService
  ){}

  navigateToMovie() {
    if (this.id !== null) {
      this.router.navigate(['/serie', this.id]);
    }
  }

  toggleWatchlist(): void {
    if (this.id === null) {
      console.warn("Ação bloqueada: ID do filme é nulo.");
      return;
    }

    this.isInWatchlist = !this.isInWatchlist;

    if(this.isInWatchlist){
      this.ulService.addSerie(this.id).subscribe({
        next:()=>{
          console.log(`Série ${this.id} adicionado à lista do usuário.`);
        },
        error:(error)=>{
          console.log("Erro ao adicionar a série à lista");
        }
      });
    }else {
      this.ulService.removeSerie(this.id).subscribe({
        next:()=>{
          console.log(`Filme ${this.id} removido da lista do usuário.`);
        },
        error:(error)=>{
          console.log(`Erro ao remover filme ${this.id}.`, error);
        }
      });
    }
  }
}
