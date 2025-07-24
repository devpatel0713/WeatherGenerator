# 🌦️ WeatherGenerator

A Java program that forecasts wet and dry weather for a given location and month based on historical transition probabilities.

## 📌 Description

The `WeatherGenerator` reads weather transition probabilities from data files (`drywet.txt`, `wetwet.txt`) and uses them to simulate day-by-day weather forecasts. It supports:
- Simulating daily weather for a specific location and month
- Analyzing forecast data to identify spells of weather
- Suggesting the best week to travel based on dry days

---

## 🧪 Example Usage

To generate weather for a location at **longitude `-98.76`** and **latitude `26.70`** for the month of **February**:

```bash
java WeatherGenerator -98.76 26.70 2
```

## 📂 Input Files

drywet.txt – Probability of a wet day following a dry day

wetwet.txt – Probability of a wet day following a wet day
