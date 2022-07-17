# MyDiplom
Этот файл содержит инструкции по подготовке системы и запуску автотестов.
## Предварительно установленные компоненты:
* IntelliJ IDEA Community Edition 2021.3.2 (version 213.6777.52)
* Docker Desktop (version 4.9.0)
* Node.js (version 16.15.1)
## Загруженные docker container images:
* mysql:latest
* postgres:latest
#### Команды для скачивания образов:
##### Скачать MySql image:
    docker pull mysql:latest
    
##### Скачать PostgreSql image:
    docker pull postgres:latest

# 1. Запуск тестируемого приложения в режиме работы с MySql
### A. Запустить симулятор банковского сервиса на порту 9999
* На Windows системаx:  в директории `MyDiplom\gate-simulator` запустить .bat файл:

        gate-sim-start.bat
        
* На других ОС: зайти в директорию `MyDiplom\gate-simulator` и выполнить команду 

        npm start

### B. Запустить сервис баз данных MySql на порту 3306
* Зайти в директорию `MyDiplom\docker\mysql` и выполнить команду 

        docker-compose up
        
### C. Запустить сервис тестируемого приложения на порту 8080
* На Windows системаx:  в директории `MyDiplom\SUT` запустить .bat файл:

        sut-mysql.bat

* На других ОС: зайти в директорию `MyDiplom\SUT` и выполнить команду 

        java -jar aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/sample-app

### D. Открываем проект MyDiplom" в "IDEA"  и запускаем тесты.

# 2. Запуск тестируемого приложения в режиме работы с PostgreSql
### A. Запустить симулятор банковского сервиса на порту 9999
* На Windows системаx:  в директории `MyDiplom\gate-simulator` запустить .bat файл:

        gate-sim-start.bat
        
* На других ОС: зайти в директорию `MyDiplom\gate-simulator` и выполнить команду 

        npm start

### B. Запустить сервис баз данных PostgreSql на порту 5432
* Зайти в директорию `MyDiplom\docker\postgresql` и выполнить команду 

        docker-compose up
        
### C. Запустить сервис тестируемого приложения на порту 8080
* На Windows системаx:  в директории `MyDiplom\SUT` запустить .bat файл:

        sut-postgresql.bat

* На других ОС: зайти в директорию `MyDiplom\SUT` и выполнить команду 

        java -jar aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/sample-app

### D. Открываем проект MyDiplom" в "IDEA"  и запускаем тесты.
