import {Injectable} from '@angular/core';
import {WeatherForecast} from '../../models/weather-forecast.model';
import {BehaviorSubject, catchError, Observable, of, tap, throwError} from 'rxjs';
import {DataService} from "../data/data.service";
import {WeatherForecastCollection} from "../../models/weather-forecast-collection.model";


@Injectable({
  providedIn: 'root'
})
export class ForecastService {
  protected currWForecast?: WeatherForecast;
  protected hourlyWForecastColl?: WeatherForecastCollection;
  protected dailyWForecastColl?: WeatherForecastCollection;
  protected wForecastColls?: WeatherForecastCollection[];
  private readonly currWForecastSubject: BehaviorSubject<WeatherForecast | undefined>
    = new BehaviorSubject<WeatherForecast | undefined>(undefined);
  private readonly hourlyWForecastCollSubject: BehaviorSubject<WeatherForecastCollection | undefined>
    = new BehaviorSubject<WeatherForecastCollection | undefined>(undefined);
  private readonly dailyWForecastCollSubject: BehaviorSubject<WeatherForecastCollection | undefined>
    = new BehaviorSubject<WeatherForecastCollection | undefined>(undefined);
  private readonly wForecastCollsSubject: BehaviorSubject<WeatherForecastCollection[] | undefined>
    = new BehaviorSubject<WeatherForecastCollection[] | undefined>(undefined);


  constructor(private readonly dataService: DataService) { }


  getCurrWForecast(): BehaviorSubject<WeatherForecast | undefined> {
    return this.currWForecastSubject;
  }

  getHourlyWForecastColl(): BehaviorSubject<WeatherForecastCollection | undefined> {
    return this.hourlyWForecastCollSubject;
  }

  getDailyWForecastColl(): BehaviorSubject<WeatherForecastCollection | undefined> {
    return this.dailyWForecastCollSubject;
  }

  getWorecastColls(): BehaviorSubject<WeatherForecastCollection[] | undefined> {
    return this.wForecastCollsSubject;
  }

  reqCurrWForecastByCityName(cityName: string): Observable<WeatherForecastCollection | undefined> {
    return this.dataService.reqCurrForecastsByCityName(cityName).pipe(
      tap( (data: WeatherForecastCollection) => {
        if (data && data.weatherForecasts && data.weatherForecasts.length > 0) {
        this.currWForecast = data.weatherForecasts[0];
        this.currWForecastSubject.next(this.currWForecast);
        }
      })
      , catchError(err => {
        if (err.status === 404) {
          return throwError(() => err);
        }

        console.warn(err);

        return of(undefined);
      }));
  }

  reqHourlyForecastCollByCityName(cityName: string): void {
    this.dataService.reqHourlyForecastCollByCityName(cityName).subscribe({
      next: (data: WeatherForecastCollection) => {
        this.hourlyWForecastColl = data;
        this.hourlyWForecastCollSubject.next(this.hourlyWForecastColl)
      },
      error: (err: any) => console.log(err)
    });
  }

  reqDailyForecastCollByCityName(city: string): void {
    this.dataService.reqDailyForecastCollByCityName(city).subscribe({
      next: (data: WeatherForecastCollection) => {
        this.dailyWForecastColl = data;
        this.dailyWForecastCollSubject.next(this.dailyWForecastColl)
      },
      error: (err: any) => console.log(err)
    });
  }

  reqForecastCollByFavoriteCities(username: string): void {
    this.dataService.reqForecastCollByFavoriteCities(username).subscribe({
      next: (data: WeatherForecastCollection[]) => {
        this.wForecastColls = data;
        this.wForecastCollsSubject.next([...this.wForecastColls])
      },
      error: (err: any) => console.log(err)
    });
  }
}
