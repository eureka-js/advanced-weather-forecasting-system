import { Component } from '@angular/core';
import {max, Subscription} from "rxjs";
import {ForecastService} from "../../../../core/services/forecast/forecast.service";
import {WeatherForecast} from "../../../../core/models/weather-forecast.model";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";


@Component({
  selector: 'app-city-forecast',
  templateUrl: './city-forecast.component.html',
  styleUrl: './city-forecast.component.css',
  providers: []
})
export class CityForecastComponent {
  protected wForecast?: WeatherForecast;
  private wForecastSub!: Subscription;
  protected cityForm!: FormGroup;
  protected readonly leftDivLen: number = 15 * 7.5;
  protected readonly tempUnit: string = "°C";
  protected readonly lengthUnit: string = "m";


  constructor(private wForecastService: ForecastService, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.wForecastSub = this.wForecastService.getForecasts().subscribe((wForecasts?: WeatherForecast) => {
        this.wForecast= wForecasts;

        this.cityForm = this.wForecast
          ? this.fb.group({ name: [this.wForecast.city
              , [Validators.required, this.isValueChangedValidator(this.wForecast.city)]] })
          : this.fb.group({ name: [null, [Validators.required]] })
    });
  }


  isValueChangedValidator(userAtr?: string) {
    return (control: AbstractControl) => control.value === userAtr ? { "valueMatch": true } : null;
  }

  onWForecastReq() {
    this.wForecastService.requestForecastsByCity(this.cityForm.value.name);
  }
}
