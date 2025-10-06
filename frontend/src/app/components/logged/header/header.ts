import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MovieService } from '../../../service/movie.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './header.html',
  styleUrl: './header.css'
})
export class Header implements OnInit {

  isMenuOpen = false;
  isMobile = false;

  searchTerm: string = ''; 

  constructor(
    private movieService: MovieService, 
    private router: Router            
  ) {}

  ngOnInit(): void {
      this.checkScreenSize();
  }

  @HostListener('window:resize')
  onResize() {
    this.checkScreenSize();
  }

  private checkScreenSize() {
    this.isMobile = window.innerWidth <= 768;
    if (!this.isMobile) {
      this.isMenuOpen = false;
    }
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  closeMenu() {
    this.isMenuOpen = false;
  }

  performSearch(): void {
    const term = this.searchTerm.trim();

    if (term === '') {
        return; 
    }

    const params = { title: term };

    this.router.navigate(['/search-results'], { queryParams: params });

    this.searchTerm = '';
  }
}