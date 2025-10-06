import { Component } from '@angular/core';
import {Router, RouterLink } from '@angular/router';
import { AuthService, LoginRequest } from '../../../service/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  constructor(
    private authService:AuthService,
    private router:Router
  ){}

  user:LoginRequest = {
    username:'',
    password:''
  };
  errorMessage = "";

  onSubmit():void{
    this.authService.login(this.user).subscribe({
      next:(response)=>{
        localStorage.setItem('token', response.token);
        this.router.navigate(['/home']);
      },
      error:(error)=>{
        if(error.error?.message){
          this.errorMessage = error.error.message;
        }else{
          this.errorMessage = "Erro no login";
        }
      }
    });
  }

}
