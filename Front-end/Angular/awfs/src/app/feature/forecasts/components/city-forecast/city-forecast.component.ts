import { Component } from '@angular/core';
import {Subscription} from "rxjs";
import {ForecastService} from "../../../../core/services/forecast/forecast.service";
import {WeatherForecast} from "../../../../core/models/weather-forecast.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {WeatherForecastCollection} from "../../../../core/models/weather-forecast-collection.model";


@Component({
  selector: 'app-city-forecast',
  templateUrl: './city-forecast.component.html',
  styleUrl: './city-forecast.component.css',
  providers: []
})
export class CityForecastComponent {
  protected currWForecast?: WeatherForecast;
  protected hourlyWFColl?: WeatherForecastCollection;
  protected dailyWFColl?: WeatherForecastCollection;
  private currWForecastSub!: Subscription;
  private hourlyWForecastSub!: Subscription;
  private dailyWForecastSub!: Subscription;
  protected cityForm!: FormGroup;
  protected readonly leftDivLen: number = 15 * 7.5;
  protected readonly TEMP_UNIT: string = "°C";
  protected readonly LENGTH_UNIT: string = "m";
  protected message: string = "";


  constructor(private wForecastService: ForecastService, private fb : FormBuilder) { }

  ngOnInit(): void {
    this.currWForecastSub = this.wForecastService.getCurrWForecast().subscribe((currWForecast?: WeatherForecast) => {
        this.currWForecast= currWForecast;

        this.cityForm
          = this.fb.group({ name: [this.currWForecast ? this.currWForecast.city : null, [Validators.required]] });
    });
    this.hourlyWForecastSub = this.wForecastService.getHourlyWForecastColl()
      .subscribe((hourlyWForecast?: WeatherForecastCollection) => { this.hourlyWFColl = hourlyWForecast });
    this.dailyWForecastSub = this.wForecastService.getDailyWForecastColl()
      .subscribe((dailyWForecast?: WeatherForecastCollection) => { this.dailyWFColl = dailyWForecast });
  }


  onWForecastReq(): void {
    this.wForecastService.reqCurrWForecastByCityName(this.cityForm.value.name).subscribe({
      next : () => {
        this.hourlyWFColl = undefined;
        this.dailyWFColl = undefined;

        if (this.message !== "") {
          this.message = "";
        }
      },
      error: (err) => {
        if (err.status === 404) {
          this.message = "The city doesn't exist";
        }
      }
    });
  }

  onHourlyForecastButt(): void {
    this.wForecastService.reqHourlyForecastCollByCityName(this.currWForecast!.city);
  }

  onDailyForecastButt(): void {
    this.wForecastService.reqDailyForecastCollByCityName(this.currWForecast!.city);
  }
}
