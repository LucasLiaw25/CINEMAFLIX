import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../../service/movie.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { MovieForm } from '../../../components/logged/movie-form/movie-form';
import { RouterLink } from '@angular/router';
import { Card } from '../../../components/logged/card/card';


@Component({
  selector: 'app-movie',
  imports: [CommonModule, FormsModule, Header, Footer, MovieForm],
  templateUrl: './movie.html',
  styleUrl: './movie.css'
})
export class Movie{

  

}
