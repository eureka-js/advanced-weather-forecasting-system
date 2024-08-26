import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, tap} from "rxjs";
import {DataService} from "../data/data.service";
import {FavoriteCity} from "../../models/favorite-city.model";


@Injectable({
  providedIn: 'root'
})
export class CityService {
  private favCities: FavoriteCity[] = [];
  private favCityNamesSubject: BehaviorSubject<FavoriteCity[]> = new BehaviorSubject<FavoriteCity[]>([]);


  constructor(private readonly dataService: DataService) { }


  getFavCities(): BehaviorSubject<FavoriteCity[]> {
    return this.favCityNamesSubject;
  }

  addFavoriteCity(username: string, cityName: string): Observable<any> {
    return this.dataService.addFavoriteCity(username, cityName);
  };

  delFavoriteCity(cityName: string): Observable<any> {
    let i: number = this.favCities.findIndex((fc) => fc.cityName === cityName);

    return this.dataService.delFavoriteCity(this.favCities[i]!.id)
      .pipe(tap((data: any) => {
        if (!data) {
          this.favCities.splice(i);
          this.favCityNamesSubject.next([...this.favCities]);
        }
      }));
  };

  getFavoriteCities(username: string): void {
    this.dataService.getFavoriteCities(username).subscribe({
      next: (data: FavoriteCity[]) => {
        this.favCities = data;
        this.favCityNamesSubject.next([...this.favCities]);
      },
      error: (err: any) => console.warn(err)
    });
  };
}
