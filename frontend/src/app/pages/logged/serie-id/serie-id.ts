import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SeriesIndividual } from '../../../interfaces/serie';
import { SerieService } from '../../../service/serie.service';
import { ActivatedRoute, Route } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { SerieIndividual } from '../../../components/logged/serie-individual/serie-individual';

@Component({
  selector: 'app-serie-id',
  imports: [CommonModule, Header, Footer, SerieIndividual],
  templateUrl: './serie-id.html',
  styleUrl: './serie-id.css'
})
export class SerieId implements OnInit{

  serie:SeriesIndividual | null = null; 
  loading:boolean = true;
  errorMessage:string = "";

  constructor(
    private serieService:SerieService,
    private cdr:ChangeDetectorRef,
    private route:ActivatedRoute
  ){}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      const serieId = +id;
      this.loadSeries(serieId);
    }
  }

  loadSeries(serieId:number){
    this.loading = true;
    this.errorMessage = "";
    this.serieService.getSerieById(serieId).subscribe({
      next:(serie)=>{
        this.loading = false;
        this.serie = serie;
        console.log("Load serie", serie);
      },
      error:(error)=>{
        this.loading = false;
        console.log("Error loading series", error);
      }
    });
  }

}
