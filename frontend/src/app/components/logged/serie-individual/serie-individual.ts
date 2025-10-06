import { Component, Input } from '@angular/core';
import { Actor, Cateogry, Language, Streaming } from '../../../interfaces/movie';
import { SeriesIndividual } from '../../../interfaces/serie';
import { CommonModule } from '@angular/common';
import { ProfileInter } from '../../../interfaces/profile';
import { Router } from '@angular/router';

@Component({
  selector: 'app-serie-individual',
  imports: [CommonModule],
  templateUrl: './serie-individual.html',
  styleUrl: './serie-individual.css'
})
export class SerieIndividual {
  @Input() serie: SeriesIndividual | null = null; 
  @Input() title:string = "";
  @Input() description:string = "";
  @Input() synopsis: string = "";
  @Input() duration:number = 0;
  @Input() rating:number = 0;
  @Input() age_rating:number = 0;
  @Input() publication_year:number = 0;
  @Input() episode:number = 0
  @Input() season:number = 0;
  @Input() categoryIds: Cateogry[] = [];
  @Input() streamingsIds:Streaming[] = [];
  @Input() top10: boolean = false;
  @Input() actor:Actor[]=[]
  @Input() language:Language[]=[]
  @Input() user:ProfileInter | null = null; 

  constructor(
    private router:Router
  ){}

  navigateToProfile(){
    if(this.user?.id){
        this.router.navigate(['/profile/user/', this.user.id]);
    }
  }
}
   
