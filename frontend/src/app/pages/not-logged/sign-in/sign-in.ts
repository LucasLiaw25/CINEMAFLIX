import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService, UserRequest } from '../../../service/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './sign-in.html',
  styleUrl: './sign-in.css'
})
export class SignIn {
  user:UserRequest={
    name: "",
    email: "",
    username: "",
    password: ""
  }

  constructor(private authService:AuthService, private router:Router){}
  errorMessage = "";

  onSubmit():void{
    if(!this.user.name||!this.user.email||!this.user.username||!this.user.password){
        this.errorMessage = "Preencha todos os campos";
        return;
    }

    this.authService.signIn(this.user).subscribe({
      next:(user)=>{
        this.user = {
          name: "",
          email: "",
          username: "",
          password: ""
        };
        localStorage.setItem('token', user.token)
        setTimeout(()=>{
          this.router.navigate(["/edit/profile"]);
        })
      },
      error:(error)=>{
        this.errorMessage = error.error?.message;
      }
    });
  }

}
