import { Component } from '@angular/core';
import {WeatherForecastCollection} from "../../../../core/models/weather-forecast-collection.model";
import {ForecastService} from "../../../../core/services/forecast/forecast.service";
import {Subscription} from "rxjs";
import {CityService} from "../../../../core/services/city/city.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FavoriteCity} from "../../../../core/models/favorite-city.model";


@Component({
  selector: 'app-favorite-cities',
  templateUrl: './favorite-cities.component.html',
  styleUrl: './favorite-cities.component.css'
})
export class FavoriteCitiesComponent {
  protected favCities: FavoriteCity[] = [];
  protected cityNamesSub?: Subscription;
  protected forecastColls?: WeatherForecastCollection[];
  protected forecastCollsSub!: Subscription;
  protected addFavCityForm!: FormGroup;
  protected selectedFavCity?: string;
  protected message: string = "";
  protected readonly leftDivLen: number = 15 * 7.5;
  protected readonly TEMP_UNIT: string = "°C";
  protected readonly LENGTH_UNIT: string = "m";


  constructor(private forecastService: ForecastService, private cityService: CityService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.cityNamesSub = this.cityService.getFavCities()
      .subscribe((cityNames: FavoriteCity[]) => this.favCities = cityNames);

    this.addFavCityForm = this.fb.group({
      username: [null, Validators.required]
      , cityName: [null, Validators.required]
    });

    this.forecastCollsSub = this.forecastService.getWorecastColls()
      .subscribe((forecastColls?: WeatherForecastCollection[]) => this.forecastColls = forecastColls);
  }


  onGetForecastsButt(): void {
    this.forecastService.reqForecastCollByFavoriteCities(this.addFavCityForm.get("username")?.value);
  }

  onGetFavoriteCitiesButt(): void {
    this.cityService.getFavoriteCities(this.addFavCityForm.get("username")?.value);
  }

  onDelFavoriteCityButt(): void {
    this.cityService.delFavoriteCity(this.selectedFavCity!).subscribe({
      next: (data: any) => {
        if (!data) {
          this.selectedFavCity = undefined;
        }
      }
    });
  }

  onAddFavoriteCityButt(): void {
    this.cityService.addFavoriteCity(this.addFavCityForm.get("username")?.value, this.addFavCityForm.get("cityName")?.value)
      .subscribe({
        next: (data: any) => {
          this.message = data.message;
        }
        , error: (data: any) => {
          if (data.status === 404 || data.status === 409) {
            this.message = data.error.message;
          }
        }
      });
  }
}
