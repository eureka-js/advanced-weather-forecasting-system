import {WeatherForecast} from "./weather-forecast.model";
import {ForecastType} from "../enums/ForecastType.enum";


export class WeatherForecastCollection {
  constructor(
    public id: number
    , public city: string
    , public dateTime: Date
    , public forecastType: ForecastType
    , public weatherForecasts: WeatherForecast[]
  ) { }
}
