# Distributed Task Server API

## Описание проекта

### Task Management Service - сервис для распределенного выполнения задачами.

### Документация Swagger

Документация доступна по адресу ```http://localhost:8080/swagger-ui.html```

#### Сервис поддерживает:

* Регистрацию новых задач
* Отправку задач в топик Kafka
* Проверка состояния задач
* Сохранение промежуточного состояния задачи
* Обработка ошибок


#### Стек технологий:
* Java 17
* PostgreSQL
* Spring Boot
* Spring Data JPA
* Kafka
* REST
* Docker
* Postman

## Запуск приложения

1. Клонируйте репозиторий ```git clone https://github.com/nexign-task/task-management-service.git```
2. Перейдите в директорию ```cd task-management-service```
3. Запустите контейнеры ```docker compose up -d```
4. Приложение будет доступно по адресу ```http://localhost:8080```

## Примеры запросов в Postman

### Создание задачи

Отправьте POST запрос на создание задачи по адресу ```http://localhost:8080/api/task```

Ответ: ```

{
"id": 1,

"name": "Sample Task",

"status": "NEW",

"duration": 5000
}```

### Получение задачи по ID в Postman

Отправьте GET запрос на создание задачи по адресу ```http://localhost:8080/api/task/1```

{
"id": 1,

"name": "Sample Task",

"status": "COMPLETED",

"duration": 5000
}```

Можно увидеть статус **IN_PROGRESS** если длительность задачи большая.

### Получение всех задач в Postman

Отправьте GET запрос на получение списка всех задач по адресу ```http://localhost:8080/api/task```
