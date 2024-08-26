import {Component, Input} from '@angular/core';
import {WeatherForecast} from "../../../../../core/models/weather-forecast.model";


@Component({
  selector: 'app-individual-forecast',
  templateUrl: './individual-forecast.component.html',
  styleUrl: './individual-forecast.component.css'
})
export class IndividualForecastComponent {
  @Input() public wForecast!: WeatherForecast;
  @Input() public divLen!: number;
  @Input() public TEMP_UNIT!: string;
  @Input() public LENGTH_UNIT!: string;
}
