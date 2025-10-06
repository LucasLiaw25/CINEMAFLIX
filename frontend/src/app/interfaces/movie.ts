import { UserRequest } from "../service/auth.service";
import { ProfileInter } from "./profile";

export interface Movies{
    id:number;
    title:string;
    description:string;
    duration:number;
    rating:number;
    age_rating:number;
    publication_year: number;
    categoryIds:number[];
    streamingIds:number[];
    top10: boolean; 
    isWatchlisted: boolean;
    user:UserRequest;
}

export interface CreateMovies {
    title: string;
    description: string;
    synopsis: string;
    duration: number;
    rating: number;
    age_rating: number;
    publication_year: number;
    categories: Cateogry[];   
    streamings: Streaming[];
    actors: Actor[];          
    languages: Language[];     
    top10: boolean;
}


export interface MoviesIndividual{
    id:number;
    title:string;
    description:string;
    synopsis:string;
    duration:number;
    rating:number;
    age_rating:number;
    publication_year: number;
    categories: Cateogry[];
    streamings: Streaming[]; 
    actors: Actor[];      
    languages: Language[];
    top10:boolean;
    user:ProfileInter;
}

export interface Page<T> {
  content: T[];           
  totalElements: number;  
  totalPages: number;      
  number: number;           
  size: number;            
  first: boolean;         
  last: boolean;           
}

export interface Cateogry{
    id:number;
    name:string;
}

export interface Actor{
    id:number;
    name:string;
}

export interface Language{
    id:number;
    name:string;
}

export interface Streaming{
    id:number;
    name:string
}

