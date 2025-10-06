import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProfileInter } from '../interfaces/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  constructor(
    private http:HttpClient
  ){}

  apiUrl = "http://localhost:8080/api/cineflix/profile/";

  getProfile():Observable<ProfileInter>{
    return this.http.get<ProfileInter>(this.apiUrl);
  }

  updateProfile(profile:Partial<ProfileInter>):Observable<ProfileInter>{
    return this.http.patch<ProfileInter>(this.apiUrl, profile);
  }

  getById(id:number):Observable<ProfileInter>{
    return this.http.get<ProfileInter>(`${this.apiUrl}${id}`);
  }
}
