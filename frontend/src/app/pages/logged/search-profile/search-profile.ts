import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProfileService } from '../../../service/profile.service';
import { AuthService, UserRequest } from '../../../service/auth.service';
import { ProfileInter } from '../../../interfaces/profile';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-search-profile',
  imports: [Header, Footer, CommonModule],
  templateUrl: './search-profile.html',
  styleUrl: './search-profile.css'
})
export class SearchProfile implements OnInit{

  constructor(
    private profileService:ProfileService,
    private authService:AuthService,
    private cdr:ChangeDetectorRef,
    private route:ActivatedRoute,
    private router:Router
  ){}

  id: number | null = null; 

  ngOnInit():void{
    const routeId = this.route.snapshot.paramMap.get('id');
    if(routeId){
      this.id = +routeId;
      this.loadProfile(+routeId);
    }
  }

  navigateToUser() {
    if (this.id !== null) {
      this.router.navigate(['/content/user/', this.id]);
    }
  }

  username:string = "";
  profile: ProfileInter = {};
  isLoading: boolean = true;
  errorMessage: string = '';

  loadProfile(id:number) {
    this.profileService.getById(id).subscribe({
      next: (data) => {
        this.profile = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erro ao carregar o perfil.';
        this.isLoading = false;
        console.log("Erro ao carregar o perfil", error)
      }
    });
  }

}
