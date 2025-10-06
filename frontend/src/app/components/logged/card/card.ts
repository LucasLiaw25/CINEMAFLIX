import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { UserListService } from '../../../service/user-list';
import { UserRequest } from '../../../service/auth.service';

@Component({
  selector: 'app-card',
  imports: [CommonModule],
  templateUrl: './card.html',
  styleUrl: './card.css'
})
export class Card {

  @Input() id: number | null = null; 
  @Input() title:string = "";
  @Input() description:string="";
  @Input() duration:number = 0;
  @Input() rating:number = 0;
  @Input() age_rating:number = 0;
  @Input() top10:boolean = false;
  @Input() isInWatchlist: boolean = false;
  @Input() user:UserRequest | null = null;
  
  constructor(
    private ulService:UserListService,
    private router:Router
  ){}

  navigateToMovie() {
    if (this.id !== null) {
      this.router.navigate(['/movie', this.id]);
    }
  }

  toggleWatchlist(): void {
    if (this.id === null) {
      console.warn("Ação bloqueada: ID do filme é nulo.");
      return;
    }

    this.isInWatchlist = !this.isInWatchlist;

    if(this.isInWatchlist){
      this.ulService.addMovie(this.id).subscribe({
        next:()=>{
          console.log(`Filme ${this.id} adicionado à lista do usuário.`);
        },
        error:(error)=>{
          console.log("Erro ao adicionar o filme à lista");
          this.isInWatchlist = false;
        }
      });
    }else {
      this.ulService.removeMovie(this.id).subscribe({
        next: () => {
          console.log(`Filme ${this.id} removido da lista do usuário.`);
        },
        error: (error) => {
          console.error(`Erro ao remover filme ${this.id}.`, error);
          this.isInWatchlist = true; 
        }
      });
    }
  }
}
