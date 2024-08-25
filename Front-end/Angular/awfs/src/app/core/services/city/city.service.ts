import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {DataService} from "../data/data.service";


@Injectable({
  providedIn: 'root'
})
export class CityService {
  constructor(private readonly dataService: DataService) { }


  addFavoriteCity(username: string, cityName: string): Observable<any> {
    return this.dataService.addFavoriteCity(username, cityName);
  };
}
