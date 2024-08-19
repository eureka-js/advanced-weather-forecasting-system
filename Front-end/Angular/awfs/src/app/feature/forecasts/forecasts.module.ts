import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ForecastsRoutingModule } from './forecasts-routing.module';
import { CityForecastComponent } from './components/city-forecast/city-forecast.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [CityForecastComponent],
  imports: [
    CommonModule,
    ForecastsRoutingModule,
    ReactiveFormsModule
  ]
})
export class ForecastModule { }
