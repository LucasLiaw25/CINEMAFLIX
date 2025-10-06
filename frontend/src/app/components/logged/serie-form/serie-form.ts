import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Actor, Cateogry, Language, Streaming } from '../../../interfaces/movie';
import { CommonModule } from '@angular/common';
import { SerieService } from '../../../service/serie.service';
import { MovieService } from '../../../service/movie.service';
import { CreateSeries } from '../../../interfaces/serie';

@Component({
  selector: 'app-serie-form',
  imports: [CommonModule, RouterLink, ReactiveFormsModule], 
  templateUrl: './serie-form.html',
  styleUrls: ['./serie-form.css'] 
})
export class SerieForm implements OnInit {
  
  seriesForm: FormGroup; 

  availableCategories: Cateogry[] = [];
  availableStreamings: Streaming[] = []; 
  availableActors: Actor[] = [];
  availableLanguages: Language[] = [];

  errorMessage: string = "";
  successMessage: string = "";
  loading: boolean = false;
  
  constructor(
    private fb: FormBuilder, 
    private seriesService: SerieService, 
    private movieService:MovieService,
    private router: Router
  ) {

    this.seriesForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      synopsis: ['', Validators.required],
      duration: [0, [Validators.required, Validators.min(1)]], 
      rating: [1, [Validators.required, Validators.min(1), Validators.max(100)]],
      age_rating: [0, [Validators.required, Validators.min(0)]],
      publication_year: [new Date().getFullYear(), [Validators.required, Validators.min(1900)]],
      episode: [1, [Validators.required, Validators.min(1)]],
      season: [1, [Validators.required, Validators.min(1)]],
      categories: [[], [Validators.required, this.arrayMinLength(1)]], 
      streamings: [[], [Validators.required, this.arrayMinLength(1)]],
      actors: [[], [Validators.required, this.arrayMinLength(1)]],
      languages: [[], [Validators.required, this.arrayMinLength(1)]],
      top10: [false]
    });
  }

  private arrayMinLength(min: number) {
    return (control: any) => {
      const array = control.value;
      if (!array || array.length < min) {
        return { arrayMinLength: { requiredLength: min, actualLength: array?.length || 0 } };
      }
      return null;
    };
  }

  ngOnInit(): void {
    this.loadActorAndLanguage();
    this.loadCategoriesAndStreamings();
  }

  loadCategoriesAndStreamings(): void {
    this.movieService.getCategory().subscribe({
      next:(data)=>this.availableCategories = data,
      error:(error)=> console.log("Erro ao carregar categorias", error)
    });
    
    this.movieService.getStreaming().subscribe({
      next: (data) => this.availableStreamings = data,
      error: (error) => console.log("Erro ao carregar os streamings", error)
    });
  }

  loadActorAndLanguage(): void {
    this.movieService.getActor().subscribe({
      next: (data) => this.availableActors = data,
      error: (error) => console.log("Erro ao carregar os atores", error)
    });

    this.movieService.getLanguage().subscribe({
      next: (data) => this.availableLanguages = data,
      error: (error) => console.log("Erro ao carregar as línguas", error)
    });
  }

  onCheckboxChange(event: any, controlName: string) {
    const checked = event.target.checked;
    const value = Number(event.target.value);
    const currentArray: number[] = this.seriesForm.get(controlName)?.value || [];

    let newArray: number[];
    
    if (checked) {
      newArray = [...currentArray, value];
    } else {
      newArray = currentArray.filter(item => item !== value);
    }
    
    this.seriesForm.get(controlName)?.setValue(newArray);
    this.seriesForm.get(controlName)?.markAsTouched();
    
    console.log(`${controlName} atualizado:`, newArray);
  }

  isSelected(id: number, controlName: string): boolean {
    const currentArray: number[] = this.seriesForm.get(controlName)?.value || [];
    return currentArray.includes(id);
  }
  private findById<T extends { id: number }>(id: number, array: T[]): T | undefined {
    return array.find(item => item.id === id);
  }

  onSubmit() {
    if (this.seriesForm.invalid) {
      this.errorMessage = "Por favor, preencha todos os campos obrigatórios e selecione pelo menos uma opção em cada categoria.";
      this.seriesForm.markAllAsTouched();
      return; 
    }

    this.loading = true;
    this.errorMessage = "";
    this.successMessage = "";

    const formValue = this.seriesForm.value;
    
    const payload: CreateSeries = {
      title: formValue.title,
      description: formValue.description,
      synopsis: formValue.synopsis,
      duration: formValue.duration,
      rating: formValue.rating,
      age_rating: formValue.age_rating,
      publication_year: formValue.publication_year,
      episode: formValue.episode,
      season: formValue.season,
      top10: formValue.top10,

      categories: formValue.categories.map((id: number) => 
        this.findById(id, this.availableCategories)!
      ).filter(Boolean),
      streamings: formValue.streamings.map((id: number) => 
        this.findById(id, this.availableStreamings)!
      ).filter(Boolean),
      actors: formValue.actors.map((id: number) => 
        this.findById(id, this.availableActors)!
      ).filter(Boolean),
      languages: formValue.languages.map((id: number) => 
        this.findById(id, this.availableLanguages)!
      ).filter(Boolean)
    };

    console.log('📦 Payload completo para envio:', payload);

    this.seriesService.createSerie(payload).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = 'Série criada com sucesso ✅';
        console.log('Resposta do servidor:', response);
        
        setTimeout(() => {
          this.router.navigate(['/home']);
        }, 1500);
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error.error?.message || "Erro ao criar série. Verifique os dados.";
        console.error('Erro completo:', error);
        
        if (error.error) {
          console.error('Erro da API:', error.error);
        }
      }
    });
  }

  getArrayErrorMessage(controlName: string): string {
    const control = this.seriesForm.get(controlName);
    if (control?.errors?.['arrayMinLength']) {
      return `Selecione pelo menos 1 opção`;
    }
    if (control?.errors?.['required']) {
      return 'Este campo é obrigatório';
    }
    return '';
  }
}

function subscribe(arg0: { next: (data: any) => any; error: (error: any) => void; }) {
  throw new Error('Function not implemented.');
}
