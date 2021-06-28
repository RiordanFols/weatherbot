# Weatherbot
## Телеграм-бот погоды. Информация о текущей погоде и прогноз до 14 дней
##### Java 15 + Spring boot 2 + Maven
#####Использует Telegram Bot Api и openweather.api


##### Пример работы:
![Пример работы](pics/Weatherbot.png)

##### Для запуска:
1) Зарегистрировать бота с помощью BotFather (Отдельный бот в telegram)

2) Полученные токен и юзернейм записать как **telegram.bot.token** и **telegram.bot.username** 
в файл *main/resources/application.properties* или задать как параметры запуска приложения 

3) Зарегистрироваться на www.openweathermap.org/api, получить ключ

4) Полученный ключ также записать в параметры как **openweather.api.key**

5) Запустить **WeatherbotApplication.java**

6) Для тестирования занести те же параметры в *test/resources/test.properties*