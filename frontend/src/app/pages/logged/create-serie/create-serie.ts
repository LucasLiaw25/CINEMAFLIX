import { Component } from '@angular/core';
import { Header } from '../../../components/logged/header/header';
import { Footer } from '../../../components/logged/footer/footer';
import { SerieForm } from '../../../components/logged/serie-form/serie-form';

@Component({
  selector: 'app-create-serie',
  imports: [Header, Footer, SerieForm],
  templateUrl: './create-serie.html',
  styleUrl: './create-serie.css'
})
export class CreateSerie {

}
