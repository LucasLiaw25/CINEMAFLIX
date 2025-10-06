import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateSeries, Series, SeriesIndividual } from "../interfaces/serie";
import { Observable, tap } from "rxjs";
import { Movies, Page } from "../interfaces/movie";

@Injectable({
  providedIn: 'root'
})
export class SerieService{

    apiSerieUrl = "http://localhost:8080/api/cineflix/serie/"

    constructor(
        private http:HttpClient
    ){}

    createSerie(serie:CreateSeries):Observable<any>{
        return this.http.post(this.apiSerieUrl, serie);
    }

    getByUserId(id:number):Observable<Series[]>{
      return this.http.get<Series[]>(`${this.apiSerieUrl}user/${id}`)
    }

    getByUser():Observable<Series[]>{
        return this.http.get<Series[]>(`${this.apiSerieUrl}user`);
    }

    getSeriePage(page:number, items:number):Observable<Page<Series>>{
      let params = new HttpParams();
      params = params.set('page', page.toString());
      params = params.set('items', items.toString());
      return this.http.get<Page<Series>>(this.apiSerieUrl, {params});
    }

    getSerie():Observable<Series[]>{
        return this.http.get<Series[]>(`${this.apiSerieUrl}all`);
    }

    searchSerie(params: any): Observable<Series[]> {
  let httpParams = new HttpParams();

  for (const key in params) {
    if (params.hasOwnProperty(key)) {
      const value = params[key];
      if (value !== null && value !== undefined && value !== '') {
        httpParams = httpParams.set(key, value.toString());
      }
    }
  }

  console.log('🌐 URL completa:', `${this.apiSerieUrl}search?${httpParams.toString()}`);
  
  return this.http.get<Series[]>(`${this.apiSerieUrl}search`, { 
    params: httpParams,
    headers: {
      'Content-Type': 'application/json'
    }
  }).pipe(
    tap(results => console.log('📦 Dados recebidos do backend:', results))
  );
}

  top10Series():Observable<Series[]>{
    return this.http.get<Series[]>(`${this.apiSerieUrl}top10`);
  }

  getSeriesByCategory(categoryId:number):Observable<Series[]>{
    return this.http.get<Series[]>(`${this.apiSerieUrl}category/${categoryId}`);
  }

  getSeriesByStreaming(streamingId:number):Observable<Series[]>{
    return this.http.get<Series[]>(`${this.apiSerieUrl}streaming/${streamingId}`);
  }

  getSerieById(id:number):Observable<SeriesIndividual>{
    return this.http.get<SeriesIndividual>(`${this.apiSerieUrl}${id}`);
  }

}