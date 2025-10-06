import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';

export interface LoginRequest{
  username:string;
  password:string;
}

export interface LoginResponse{
  token:string;
}

export interface UserRequest{
  name:string;
  email:string;
  username:string;
  password:string
}

export interface UserResponse{
  token:string;
}

export interface UsernameResponse{
  username:string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private apiUrl = 'http://localhost:8080/';

  constructor(private http:HttpClient){}


  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // Mudei de 'authToken' para 'token'
    
    console.log('🔐 Token recuperado no MovieService:', token);
    
    if (!token) {
      console.error('❌ Token JWT não encontrado no localStorage');
      return new HttpHeaders({
        'Content-Type': 'application/json'
      });
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  login(request:LoginRequest):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.apiUrl}api/user/login`, request);
  }

  signIn(request:UserRequest):Observable<LoginResponse>{
    return this.http.post<LoginResponse>(`${this.apiUrl}api/user/`, request);
  }

  getUsername():Observable<UsernameResponse>{
    return this.http.get<UsernameResponse>(`${this.apiUrl}api/user/username`)
  }
  
  validateToken():Observable<boolean>{
    const token = this.getToken();
    console.log("Token resgatado", token);

    if(!token){
      console.log("Erro ao resgatar o token", token);
      return of(false);
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.head(`${this.apiUrl}api/user/validate`, {
      headers: headers,
      observe: 'response',
      responseType: 'text'
    })
    .pipe(
      map(response=>response.status===200),
      catchError((error)=>{
        console.log("Erro na validação do token", error);
        localStorage.removeItem('token');
        return of(false);
      })
    );


  }

  getToken():string|null{
    return localStorage.getItem('token');
  }

  isAuthenticate():boolean{
    return !!this.getToken();
  }

  logOut():void{
    localStorage.removeItem('token');
  }

}


