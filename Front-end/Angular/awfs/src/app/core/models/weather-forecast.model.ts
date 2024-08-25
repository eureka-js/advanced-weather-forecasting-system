import {ForecastType} from "../enums/ForecastType.enum";


export class WeatherForecast {
    constructor(
      public id: number
      , public city: string
      , public temperature: number
      , public description: string
      , public dateTime: Date
      , public uvIndex: number
      , public visibility: number
      , public humidity: number
      , public windSpeed: number
      , public forecastType: ForecastType
      , public feelsLikeTemperature: number
      , public pressure: number
    ) { }
}
