import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {WeatherForecastCollection} from "../../models/weather-forecast-collection.model";
import {FavoriteCity} from "../../models/favorite-city.model";


@Injectable({
  providedIn: 'root'
})
export class DataService {
  readonly W_FORECAST_APP_URL: string = "http://localhost:8080/weatherforecastapp/";
  readonly CURR_W_FORECAST_URL: string = "api/weather/current/";
  readonly HOURLY_W_FORECAST_URL: string = "api/weather/hourly/";
  readonly DAILY_W_FORECAST_URL: string = "api/weather/daily/";
  readonly FAVORITE_CITY_URL: string = "api/user/favorite";
  readonly FAV_CITIES_FORECASTS_URL: string = "api/weather/favorites";
  readonly FAVORITE_CITIES_URL: string = "api/user/favorites/";


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
    return this.httpClient.post(this.W_FORECAST_APP_URL + this.FAVORITE_CITY_URL
      , { "id": null, "username": username, "cityName": cityName });
  }

  delFavoriteCity(id: number): Observable<any> {
    return this.httpClient.delete(this.W_FORECAST_APP_URL + this.FAVORITE_CITY_URL + "/" + id);
  }

  getFavoriteCities(cityName: string): Observable<FavoriteCity[]> {
    return this.httpClient.get<FavoriteCity[]>(this.W_FORECAST_APP_URL + this.FAVORITE_CITIES_URL + cityName);
  }

  reqForecastCollByFavoriteCities(username: string): Observable<WeatherForecastCollection[]> {
    return this.httpClient.get<WeatherForecastCollection[]>(this.W_FORECAST_APP_URL
      + this.FAV_CITIES_FORECASTS_URL + "?username=" + username);
  }
}
