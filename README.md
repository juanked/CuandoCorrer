# Cuando Correr
[![Version](https://img.shields.io/github/v/release/juanked/CuandoCorrer?display_name=tag)](https://github.com/juanked/CuandoCorrer/releases/latest)
[![English](https://img.shields.io/badge/lang-en-blue)](https://github.com/juanked/CuandoCorrer/tree/v1.0.0#readme.en)

Esta aplicación permite a sus usuarios fijar cuáles son las condiciones climáticas en las que se siente cómodo realizando ejercicio en el exterior. Ayuda a prevenir golpes de calor y otros problemas de salud causados por las condiciones climáticas.

## Funcionamiento básico

### Pantalla principal
Una vez abierta la aplicación, esta solicitará permisos de ubicación para poder proporcionar los datos meteorológicos para la ubicación en la que se encuentre el usuario.

Aparecerá arriba, en una tarjeta, la meteorología actual y su color variará entre el rojo si no cumple con los ajustes del usario y el verde si sí que lo hace y, por lo tanto, es un buen momento para hacer ejercicio. Debajo de esta tarjeta hay una lista en la que se indica la misma información pero para los siguientes 5 días en tramos de 3h.

### Menú configuración
Accediendo al menú _Configuración_ se podrán ajustar cuáles son los parámetros en los que el usuario se siente cómodo corriendo o haciéndo cualquier tipo de ejercicio en el exterior. Para ello cuenta con los siguientes ajustes:

 - Permitir correr bajo la lluvia: si se selecciona este modo, aparecerán como horarios disponibles los intervalos en los que esté lloviendo. Si no está marcada, no aparecerán.
 -  Temperatura mínima: temperatura por debajo de la que el usuario no quiere hacer ejercicio en el exterior.
 -  Temperatura máxima: temperatura por encima de la que el usuario no quiere hacer ejercicio en el exterior.
 -  Velocidad máxima del viento: velocidad del viento a partir de la cual el usuario no desea hacer ejercicio en el exterior.

En la parte inferior de esta pantalla un botón permite reiniciar la configuración de la aplicación a la de por defecto.

### Menú información
Accediendo al menú _Información_ se puede ver el propósito de esta aplicación y la información del creador, así como un enlace directo a mi [perfil de GitHub](https://github.com/juanked).

En la última línea, aparecerá la versión instalada de la aplicación.

## Idiomas
La aplicación está disponible tanto en español como en inglés, siendo este último idioma el predeterminado. El idioma de la aplicación dependerá del idioma del dispositivo.

## Instalación
Puedes descargar el proyecto y compilarla tú mismo utilizando Android Studio, para ello puedes clonar el repositorio en su estado actual o acudir a la [última versión](https://github.com/juanked/CuandoCorrer/releases/latest) estable de la aplicación. Para ello es necesario renombrar el fichero: `/app/main/res/values/secrets_example.xml` a `secrets.xml` e incluir (sustituyendo las X) una clave API que se puede obtener en [OpenWeatherMap](https://openweathermap.org/api) de forma gratuita. Esta aplicación utiliza dos endpoints de dicha API: [Current Weather](https://openweathermap.org/current) y [3-hour Forecast 5 days](https://openweathermap.org/forecast5), ambos gratuitos y con un límite suficiente de llamadas diarias.

Para instalar esta aplicación es necesario crear un _.apk_ firmado utilizando para ello el programa [Android Studio](https://developer.android.com/studio). Para ello se puede seguir la [guía oficial](https://developer.android.com/studio/publish/app-signing?hl=es-419#sign-apk) de Google. Una vez generado este archivo _.apk_ podrás instalarlo en cualquier dispositivo Android que cumpla con los requisitos de SDK (del mínimo 25 al recomendado 32).
