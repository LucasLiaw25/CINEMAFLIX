import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Movies } from '../interfaces/movie';
import { Series } from '../interfaces/serie';

export interface UserListResponse{
  movies:Movies[];
  series:Series[];
}

@Injectable({
  providedIn: 'root'
})
export class UserListService {
  
  constructor(private http:HttpClient){}
  private apiUrl = "http://localhost:8080/api/cineflix/user-list/";

  addMovie(movieId:number):Observable<any>{
    return this.http.post<any>(`${this.apiUrl}movie/${movieId}`, {});
  }

  addSerie(serieId:number):Observable<any>{
    return this.http.post<any>(`${this.apiUrl}serie/${serieId}`, {});
  }

  removeMovie(movieId:number):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}movie/${movieId}`);
  }

  removeSerie(serieId:number):Observable<any>{
    return this.http.delete<any>(`${this.apiUrl}serie/${serieId}`);
  }

  getUserList():Observable<UserListResponse>{
    return this.http.get<UserListResponse>(this.apiUrl);
  }

  getWatchlistedMovieIds(): Observable<number[]> {
  return this.getUserList().pipe(
    map(response => response.movies.map(movie => movie.id))
  );
}

}
