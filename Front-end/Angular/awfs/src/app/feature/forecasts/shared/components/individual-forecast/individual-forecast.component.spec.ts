import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndividualForecastComponent } from './individual-forecast.component';

describe('IndividualForecastComponent', () => {
  let component: IndividualForecastComponent;
  let fixture: ComponentFixture<IndividualForecastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [IndividualForecastComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndividualForecastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
