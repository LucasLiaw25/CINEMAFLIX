import { Component, Input } from '@angular/core';
import { Actor, Cateogry, Language, MoviesIndividual, Streaming } from '../../../interfaces/movie';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserRequest } from '../../../service/auth.service';
import { ProfileInter } from '../../../interfaces/profile';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-movid-individual',
  imports: [CommonModule, FormsModule],
  templateUrl: './movid-individual.html',
  styleUrl: './movid-individual.css'
})
export class MovidIndividuals {

  @Input() movie: MoviesIndividual | null = null; 
  @Input() title: string = '';
  @Input() synopsis: string = '';
  @Input() duration: number = 0;
  @Input() rating: number = 0;
  @Input() age_rating:number = 0;
  @Input() publication_year = 0;
  @Input() categoryIds: Cateogry[] = [];
  @Input() streamingsIds:Streaming[] = [];
  @Input() top10: boolean = false;
  @Input() actor:Actor[]=[]
  @Input() language:Language[]=[]
  @Input() user:ProfileInter | null = null; 


  constructor(
    private router:Router
  ){}

  navigateToProfile() {
    if (this.movie?.user?.id) {
        this.router.navigate(['/profile/user/', this.movie.user.id]);
    }
}

}


