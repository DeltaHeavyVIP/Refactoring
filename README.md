## :hammer_and_wrench: РБДиП (Рефакторинг баз данных и приложений) :hammer_and_pick:
### Задание на выбор:
1. Проектирование и разработка новой программной системы
2. Рефакторинг существующего программного проекта.

***Мной был выбран сценарий 2***
</br>:moyai: Дизайн документ, по которому в дальнейшем будет вестись разработка проекта - [отчетик](https://github.com/DeltaHeavyVIP/Itmo_Course_4/blob/master/%D0%A1%D0%B5%D0%BC%D0%B5%D1%81%D1%82%D1%80%207/%D0%A0%D0%91%D0%94%D0%B8%D0%9F/%D0%A0%D0%91%D0%94%D0%B8%D0%9F.pdf)

### Спринт 1:
- Написать новый Swagger UI со всеми (в том числе и новыми) запросами к сервисам - [тык](https://se.ifmo.ru/~s282606/Refactor/)
- Переписать Model слой архитектуры

### Спринт 2:
- Разбиение приложение на 3 модуля (Object, CRUD операции с фильмами и картами, Email оповещение и прочий функционал)
- Переписать Service слой архитектуры во всех сервисах
- В контроллерах приложения стоит возвращать объект класса ResponseEntity и статус response
- В некоторых контроллерах поменять метод запроса, например запрос на получение данных вместо POST использовать GET

### Спринт 3:
- Добавить Feign в проект.
- Добавить новый функционал (на свое усмотрение)
- Code review