import { Component, OnInit } from '@angular/core';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { ProfileService } from '../../../service/profile.service';
import { ProfileInter } from '../../../interfaces/profile';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [Header, Footer, CommonModule, FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile implements OnInit{

  constructor(
    private profileService:ProfileService,
    private router:Router
  ){}

  ngOnInit(){
    this.loadProfile();
  }

  profile: ProfileInter = {};
  isLoading: boolean = true;
  isSaving: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';

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

  saveProfile(){
    this.isSaving = true;
    this.errorMessage = '';
    this.successMessage = '';

    const updateProfile:Partial<ProfileInter> = {
      bio:this.profile.bio,
      name:this.profile.name,
      username:this.profile.username,
      location:this.profile.location,
      age:this.profile.age,
      favoriteMovie:this.profile.favoriteMovie,
      favoriteSerie:this.profile.favoriteSerie
    }

    this.profileService.updateProfile(updateProfile).subscribe({
        next:(update)=>{
          this.profile = update; 
          this.isSaving = false;
          this.successMessage = 'Perfil atualizado com sucesso!';
          console.log("Perfil atualizado com sucesso", update);
          setTimeout(() => {
            this.router.navigate(['/profile'])
          }, 1500);
        },
        error:(error)=>{
          this.isSaving = false;
          this.errorMessage = 'Falha ao salvar. Tente novamente.';
          console.log('Erro ao alterar o perfil', error);
        }
    });
  }
}