import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { ProfileService } from '../../../service/profile.service';
import { ProfileInter } from '../../../interfaces/profile';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-profile',
  imports: [Header, Footer, CommonModule, FormsModule, RouterLink],
  templateUrl: './get-profile.html',
  styleUrl: './get-profile.css'
})
export class getProfile implements OnInit{

  constructor(
    private profileService:ProfileService,
    private authService:AuthService,
    private cdr:ChangeDetectorRef
  ){}

  ngOnInit(){
    this.loadProfile();
    this.fetchUsername();
  }

  username:string = "";
  profile: ProfileInter = {};
  isLoading: boolean = true;
  errorMessage: string = '';

  fetchUsername():void{
    this.authService.getUsername().subscribe({
      next:(response)=>{
        this.username = response.username;
        this.cdr.detectChanges();
      },
      error:(error)=>{
        console.log("Erro ao carregar o username", error);
        this.username = "usuario";
      }
    });
  }

  loadProfile() {
    this.profileService.getProfile().subscribe({
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