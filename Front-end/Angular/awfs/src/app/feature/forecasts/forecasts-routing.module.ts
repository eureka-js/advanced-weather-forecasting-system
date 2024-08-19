import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CityForecastComponent} from "./components/city-forecast/city-forecast.component";


const routes: Routes = [
  { path: "", component: CityForecastComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForecastsRoutingModule { }
