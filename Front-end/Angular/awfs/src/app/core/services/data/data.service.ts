import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {WeatherForecast} from "../../models/weather-forecast.model";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class DataService {
  readonly FORECAST_URL: string = "http://localhost:8080/weatherforecastapp/";
  readonly CURR_FORECAST_URL: string = "api/weather/current/";


  constructor(private readonly httpClient: HttpClient) { }


  requestForecastsByCity(city: string): Observable<WeatherForecast> {
    return this.httpClient.get<WeatherForecast>(this.FORECAST_URL + this.CURR_FORECAST_URL + city);
  }
}
