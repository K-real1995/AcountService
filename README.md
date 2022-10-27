Создать базу данных для работы с приложением в PostgreSQL.

В файле application.properites заполнить следующие поля своими данными:

Пример:
spring.datasource.url=jdbc:postgresql://localhost:5432/itech - адрес подключения к базе данных PostgreSQL для liquibase

spring.liquibase.url = jdbc:postgresql://localhost:5432/itech - адрес подключения к базе данных PostgreSQL 


spring.datasource.username=admin - логин базы данных 

spring.datasource.password=admin - пароль базы данных

rCount = 100 - количество желаемых запросов на чтение для тестового клиента

wCount = 100 - количество желаемых запросов на запись для тестового клиента

idList = 1-16 - диапазон id к которым выполнять запросы на чтение и запись

Доступные запросы к эндпоинтам:

POST http://localhost:8080/api/v1/accounts/{id}?amount={amount} - добавление/обновление баланса пользователя в базе

GET http://localhost:8080/api/v1/accounts/{id} - получение amount из базы

GET http://localhost:8080/api/v1/test_client - запуск тестового клиента

GET http://localhost:8080/api/v1/reset_statistic- запрос на сброс статистики
