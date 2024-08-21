import { Component } from '@angular/core';
import {Subscription} from "rxjs";
import {ForecastService} from "../../../../core/services/forecast/forecast.service";
import {WeatherForecast} from "../../../../core/models/weather-forecast.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";


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

        this.cityForm = this.fb.group({ name: [this.wForecast ? this.wForecast.city : null, [Validators.required]] });
    });
  }


  onWForecastReq() {
    this.wForecastService.requestForecastsByCity(this.cityForm.value.name);
  }
}
