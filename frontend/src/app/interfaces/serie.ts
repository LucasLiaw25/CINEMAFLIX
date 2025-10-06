import { Actor, Cateogry, Language, Streaming } from "./movie";
import { ProfileInter } from "./profile";

export interface Series{
    id:number;
    title:string;
    description:string;
    duration:number;
    rating:number;
    age_rating:number;
    publication_year: number;
    episode:number;
    season:number;
    categoryIds:number[];
    streamingIds:number[];
    top10: boolean; 
    isInWatchlist: boolean;
}

export interface CreateSeries{
    title:string;
    description:string;
    synopsis:string;
    duration:number;
    rating:number;
    age_rating:number;
    publication_year: number;
    episode:number;
    season:number;
    categories: Cateogry[];   
    streamings: Streaming[];
    actors: Actor[];          
    languages: Language[];     
    top10: boolean;
}

export interface SeriesIndividual{
    id:number;
    title:string;
    description:string;
    synopsis:string;
    duration:number;
    rating:number;
    age_rating:number;
    publication_year: number;
    episode:number;
    season:number;
    categories: Cateogry[];
    streamings: Streaming[]; 
    top10:boolean;
    actors: Actor[];      
    languages: Language[];
    user:ProfileInter;
}
