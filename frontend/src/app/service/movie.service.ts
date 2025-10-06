import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Actor, Cateogry, CreateMovies, Language, Movies, MoviesIndividual, Page, Streaming} from '../interfaces/movie';
import { __param } from 'tslib';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  
  constructor(private http:HttpClient){}

  apiStreamingUrl = "http://localhost:8080/api/cineflix/streaming/";
  apiMovieUrl = "http://localhost:8080/api/cineflix/movie/";
  apiCategoryUrl = "http://localhost:8080/api/cineflix/category/";
  apiActorUrl = "http://localhost:8080/api/cineflix/actor/";
  apiLanguageUrl = "http://localhost:8080/api/cineflix/language/";

  searchMovie(params: any): Observable<Movies[]> {
  let httpParams = new HttpParams();

  for (const key in params) {
    if (params.hasOwnProperty(key)) {
      const value = params[key];
      if (value !== null && value !== undefined && value !== '') {
        httpParams = httpParams.set(key, value.toString());
      }
    }
  }

  console.log('🌐 URL completa:', `${this.apiMovieUrl}search?${httpParams.toString()}`);
  
  return this.http.get<Movies[]>(`${this.apiMovieUrl}search`, { 
    params: httpParams,
    headers: {
      'Content-Type': 'application/json'
    }
  }).pipe(
    tap(results => console.log('📦 Dados recebidos do backend:', results))
  );
}

  getMovies(page:number, items:number):Observable<Page<Movies>>{
  let params = new HttpParams();
  params = params.set('page', page.toString());
  params = params.set('items', items.toString());
    
  return this.http.get<Page<Movies>>(this.apiMovieUrl, {params});
}

  getCategory():Observable<Cateogry[]>{
    return this.http.get<Cateogry[]>(this.apiCategoryUrl);
  }

  getAllMovies():Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}all`);
  }

  getStreaming():Observable<Streaming[]>{
    return this.http.get<Streaming[]>(this.apiStreamingUrl);
  }

  createMovie(movie:CreateMovies):Observable<any>{
    return this.http.post(this.apiMovieUrl, movie);
  }

  getMoviesByCategory(categoryId:number):Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}category/${categoryId}`);
  }

  getCategoryById(categoryId:number):Observable<Cateogry>{
    return this.http.get<Cateogry>(`${this.apiCategoryUrl}${categoryId}`);
  }

  getStreamingById(streamingId:number):Observable<Streaming>{
    return this.http.get<Streaming>(`${this.apiStreamingUrl}${streamingId}`);
  }

  getMoviesByStreaming(streamingId:number):Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}streaming/${streamingId}`);
  }

  getTop10Movie():Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}top10`);
  }

  getMovieById(id:number):Observable<MoviesIndividual>{
    return this.http.get<MoviesIndividual>(`${this.apiMovieUrl}${id}`);
  }

  getActor():Observable<Actor[]>{
    return this.http.get<Actor[]>(this.apiActorUrl);
  }

  getLanguage():Observable<Language[]>{
    return this.http.get<Language[]>(this.apiLanguageUrl);
  }

  getByUser():Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}user`);
  }

  getByUserId(id:number):Observable<Movies[]>{
    return this.http.get<Movies[]>(`${this.apiMovieUrl}user/${id}`);
  }

}
