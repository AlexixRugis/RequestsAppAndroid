ЧКР (Че Как Работает)

Пользователь нажимает кнопку завершения заявки
|
V
|
Кнопка вызывает событие во ViewModel'и
|
V
|
ViewModel запускает асихронную процедуру завершения заявки
|
V
|
Процедура обрабатывается в UseCas'e
|
V
|
UseCase обращается в репозиторий для изменения данных
|
V
|
Репозиторий отсылает данные о действии на сервер
.
V  Данные о статусе операции в обратном порядке доходят до ViewModel'и
.
ViewModel изменяет состояние экрана
|
V
|
Экран завершения заявки реагирует на изменение состояния и выбрасывает пользователя на главный экран приложения

