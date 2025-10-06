import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindUserContent } from './find-user-content';

describe('FindUserContent', () => {
  let component: FindUserContent;
  let fixture: ComponentFixture<FindUserContent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FindUserContent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindUserContent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
