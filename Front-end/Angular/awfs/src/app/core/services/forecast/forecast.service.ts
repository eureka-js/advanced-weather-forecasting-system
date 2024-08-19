import { Injectable } from '@angular/core';
import { WeatherForecast } from '../../models/weather-forecast.model';
import {BehaviorSubject} from 'rxjs';
import {DataService} from "../data/data.service";


@Injectable({
  providedIn: 'root'
})
export class ForecastService {
  private wForecast?: WeatherForecast;
  private readonly wForecastSubject: BehaviorSubject<WeatherForecast | undefined>
    = new BehaviorSubject<WeatherForecast | undefined>(undefined);


  constructor(private readonly dataService: DataService) { }


  getForecasts(): BehaviorSubject<WeatherForecast | undefined> {
    return this.wForecastSubject;
  }

  requestForecastsByCity(city: string): void {
    this.dataService.requestForecastsByCity(city).subscribe({
      next: (data: WeatherForecast) => {
        this.wForecast = data;
        this.wForecastSubject.next(this.wForecast)
      },
      error: err => console.log(err)
    });
  }
}
