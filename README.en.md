# When to Run
[![Version](https://img.shields.io/github/v/release/juanked/CuandoCorrer?display_name=tag)](https://github.com/juanked/CuandoCorrer/releases/latest)
[![Spanish](https://img.shields.io/badge/lang-es-blue)](https://github.com/juanked/CuandoCorrer/blob/main/README.md)

This application allows its users to set weather conditions in which the user feels comfortable to do sport outdoors. It prevents sunstroke and other health problems that can be caused by weather conditions.

## Basic operation

### Main screen
Once the application is open, it will request location permissions in order to provide weather data for the user's location.

The current weather will be displayed on a card at the top and its colour will vary between red if it does not meet the user's settings and green if it does and is therefore a good time to exercise. Below this card there is a list showing the same information but for the next 5 days in 3h sections.

### Settings menu
By accessing the _Settings_ menu, it is possible to adjust the parameters in which the user feels comfortable running or doing any type of outdoor exercise. The following settings are available:

 - Allow running in rain: if this mode is selected, intervals when it is raining will appear as available times. If unchecked, they will not appear.
 - Minimum temperature: temperature below which the user does not want to exercise outside.
 - Maximum temperature: temperature above which the user does not want to exercise outside.
 - Maximum wind speed: wind speed above which the user does not want to exercise outdoors.

At the bottom of this screen a button allows you to reset the application settings to default.

### About menu
By accessing the _Info_ menu you can see the purpose of this application and the creator's information, as well as a direct link to my [GitHub profile](https://github.com/juanked).

In the last line, the installed version of the application will be displayed.

## Languages
The application is available in both English and Spanish, the first being the default language. The language of the application will depend on the language of the device.

## Installation
You can download the project and compile it yourself using Android Studio, either by cloning the repository in its current state or by going to the stable [latest version](https://github.com/juanked/CuandoCorrer/releases/latest) of the application. To do this you need to rename the file: `/app/main/res/values/secrets_example.xml` to `secrets.xml` and include (replacing the Xs) an API key that can be obtained from [OpenWeatherMap](https://openweathermap.org/api) for free. This application uses two endpoints of this API: [Current Weather](https://openweathermap.org/current) and [3-hour Forecast 5 days](https://openweathermap.org/forecast5), both free of charge and with a sufficient limit of daily calls.

To install this application it is necessary to create a signed _.apk_ using the [Android Studio](https://developer.android.com/studio) program. To do this you can follow Google's [official guide](https://developer.android.com/studio/publish/app-signing?hl=es-419#sign-apk). Once this _.apk_ file is generated, you can install it on any Android device that meets the SDK requirements (from minimum 25 to recommended 32).
