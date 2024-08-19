import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CityForecastComponent } from './city-forecast.component';


describe('CityForecastComponent', () => {
  let component: CityForecastComponent;
  let fixture: ComponentFixture<CityForecastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CityForecastComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CityForecastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
