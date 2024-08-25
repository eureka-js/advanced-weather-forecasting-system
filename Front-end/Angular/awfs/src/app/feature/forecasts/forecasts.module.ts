import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ForecastsRoutingModule } from './forecasts-routing.module';
import { CityForecastComponent } from './components/city-forecast/city-forecast.component';
import {ReactiveFormsModule} from "@angular/forms";
import { FavoriteCitiesComponent } from './components/favorite-cities/favorite-cities.component';


@NgModule({
  declarations: [CityForecastComponent, FavoriteCitiesComponent],
  imports: [
    CommonModule,
    ForecastsRoutingModule,
    ReactiveFormsModule
  ]
})
export class ForecastModule { }
