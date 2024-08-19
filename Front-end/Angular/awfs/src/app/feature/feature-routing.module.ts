import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  { path: "", loadChildren: () => import("./forecasts/forecasts.module").then(m => m.ForecastModule) }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeatureRoutingModule { }
