import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {WeatherForecastCollection} from "../../models/weather-forecast-collection.model";


@Injectable({
  providedIn: 'root'
})
export class DataService {
  readonly W_FORECAST_APP_URL: string = "http://localhost:8080/weatherforecastapp/";
  readonly CURR_W_FORECAST_URL: string = "api/weather/current/";
  readonly HOURLY_W_FORECAST_URL: string = "api/weather/hourly/";
  readonly DAILY_W_FORECAST_URL: string = "api/weather/daily/";
  readonly POST_FAVORITE_CITY_URL: string = "api/user/favorite";
  readonly GET_FORECASTS_BY_FAV_CITIES_URL: string = "api/weather/favorites";


  constructor(private readonly httpClient: HttpClient) { }


  reqCurrForecastsByCityName(cityName: string): Observable<WeatherForecastCollection> {
    return this.reqForecastsByCityName(cityName, this.CURR_W_FORECAST_URL);
  }

  reqHourlyForecastCollByCityName(cityName: string): Observable<WeatherForecastCollection> {
    return this.reqForecastsByCityName(cityName, this.HOURLY_W_FORECAST_URL);
  }

  reqDailyForecastCollByCityName(cityName: string): Observable<WeatherForecastCollection> {
    return this.reqForecastsByCityName(cityName, this.DAILY_W_FORECAST_URL);
  }

  reqForecastsByCityName(cityName: string, url: string): Observable<WeatherForecastCollection> {
    return this.httpClient.get<WeatherForecastCollection>(this.W_FORECAST_APP_URL + url + cityName);
  }

  addFavoriteCity(username: string, cityName: string): Observable<any> {
    return this.httpClient.post(this.W_FORECAST_APP_URL + this.POST_FAVORITE_CITY_URL
      , { "id": null, "username": username, "cityName": cityName });
  }

  reqForecastCollByFavoriteCities(username: string): Observable<WeatherForecastCollection[]> {
    return this.httpClient.get<WeatherForecastCollection[]>(this.W_FORECAST_APP_URL
      + this.GET_FORECASTS_BY_FAV_CITIES_URL + "?username=" + username);
  }
}
