
# Task Time Tracker API

### Описание проекта:
API позволяет:
- создавать и управлять задачами
- фиксировать затраченное время сотрудников на задачи
- получать записи времени за период
- авторизовываться с помощью JWT

### Технологии
- Java 17+
- Spring Boot 3.x
- MyBatis
- PostgreSQL (Docker)
- Flyway
- Spring Security + JWT
- Bean Validation
- Swagger (SpringDoc OpenAPI)
- JUnit 5, Mockito

### Сущности
#### Task
- id
- name
- description
- status
#### TimeRecord
- id
- employeeId
- taskId
- startTime
- endTime
- description

#### User
- id
- username
- password
### База Данных
PostgreSQL 15 (запускается в Docker).  
Схема создаётся автоматически через Flyway при старте приложения.



### Авторизация (JWT)
Для доступа к защищённым эндпоинтам используется Bearer Token.
он должен передаваться в заголовке Authorization: Bearer token.
- Регистрация:
	- POST /auth/register
- Логин:
	- POST /auth/login
	  В ответе возвращается JWT токен:    
	  {

	  "token": "eyJhbGciOiJIUzI1NiIs..."  
	  }

###  Тестирование
- Unit тесты: JUnit 5 + Mockito
- WebMvc тесты
### Swagger (документация API)
После запуска доступна документация:
http://localhost:8080/swagger-ui/index.html
### Запуск проекта

- Для запуска проекта необходимо корректно сформировать .env
  по примеру .env.example (Иначе приложение не запустится)
- заполните поля DB_USER, DB_PASSWORD, JWT_SECRET

#### Команда для сборки:
./mvnw clean package -DskipTests

docker-compose -f docker/docker-compose.yml --env-file .env up -d --build


### Примеры API запросов

Обращаю внимание что приведенные запросы будут работать только после выполнения авторизации и получения вами токена.

Регистрация:
curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{
"username": "Lebron",
"password": "1234567"
}'

Вход:
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{
"username": "Lebron",
"password": "1234567"
}'

Создать задачу:
curl -X POST http://localhost:8080/api/tasks \
-H "Authorization: Bearer <your_jwt_token>" \
-H "Content-Type: application/json" \
-d '{
"name": "Task",
"description": "new task"
}'

Получить все задачи:
curl -X GET http://localhost:8080/api/tasks \
-H "Authorization: Bearer <your_jwt_token>"

Изменить статус:
curl -X PATCH http://localhost:8080/api/tasks/1/status \
-H "Authorization: Bearer <your_jwt_token>" \
-H "Content-Type: application/json" \
-d '{
"status": "DONE"
}'

Получить задачу по id:
curl -X GET http://localhost:8080/api/tasks/1 \
-H "Authorization: Bearer <your_jwt_token>"

Удалить задачу:
curl -X DELETE http://localhost:8080/api/tasks/2 \
-H "Authorization: Bearer <your_jwt_token>"

Создать запись времени:
curl -X POST http://localhost:8080/api/time-records \
-H "Authorization: Bearer <your_jwt_token>" \
-H "Content-Type: application/json" \
-d '{
"employeeId": 1,
"taskId": 1,
"startTime": "2026-04-20T21:46:51",
"endTime": "2026-04-20T21:46:51",
"description": "Worked on backend API"
}'

Получить записи за период:

curl -X GET "http://localhost:8080/api/time-records?employeeId=1&from=2026-04-20T10:00:00&to=2026-04-20T12:30:00" \
-H "Authorization: Bearer <your_jwt_token>"

### Проверка работоспособности

1. Запустите приложение
2. Откройте Swagger: http://localhost:8080/swagger-ui/index.html
3. Выполните /auth/register
4. Выполните /auth/login и получите токен
5. Используйте токен для вызова защищённых эндпоинтов