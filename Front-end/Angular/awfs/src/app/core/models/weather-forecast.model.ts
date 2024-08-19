export class WeatherForecast {
    constructor(
        public city: string
        , public temperature: number
        , public description: string
        , public dateTime: Date
        , public uvIndex: number
        , public visibility: number
    ) { }
}