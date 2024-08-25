import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CityForecastComponent} from "./components/city-forecast/city-forecast.component";
import {FavoriteCitiesComponent} from "./components/favorite-cities/favorite-cities.component";


const routes: Routes = [
  { path: "", component: CityForecastComponent },
  { path: "favorite", component: FavoriteCitiesComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ForecastsRoutingModule { }
