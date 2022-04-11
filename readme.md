Проект был разработан по следующим требованиям: https://docs.google.com/document/d/18BlbqpOVw9XGC7DWC37LtmPcd85lXWqPGBfq1bRW1Os/edit
Приложение имитирует работу кассового аппарата, который формирует чек, исходя из имеющихся данных(покупки, работающий кассир, скидочная карта).

После развёртывания, доступ к приложению можно получить по адресу localhost:8080/. По этому адресу располагается главная
страница, содержащая иструкции по работе(очень информативную(нет)).

Доступны следующие операции:
1) Сохранине(save)
2) Обновление(put & patch)
3) Удаление(delete)
4) Вывод списка записей(log)
5) Поиск записи по id(print)

Категория записи определяется параметром table.
В рамках предметной области, на данный момент существуют следующие категории:
1) bills - чек
2) products - продукт
3) cashiers - кассир
4) cards - дисконтная карта
5) card_types - тип дисконтной карты
6) product_discounts - доступные акции для продуктов

Форма url имеет вид:
localhost:8080/[категория]/[действие]?[параметр]=[значение]&....

Работа с GET зарросами.
GET запросами являются команды печати чека(print) и вывода списка заисей(log).
Для вывода списка всех записей можно задать номер страницы и количество записей на ней. Данные отсортированы по id.
Для вывода по id достаточно самого id в парметрах запроса. Также для категрии чеков доступен параметр "pdf", принимающий
true либо false(по умолчанию), который вернёт чек в виде pdf файла.

Работа с POST запросами.
POST запросы - удаление(delete), обновление(patch & put) и сохранение(save) - аналогично требуют различных параметров,
в зависимости от категории и действия. Однако параметры передаются в теле(body) запроса в виде json строки.

Шаблоны JSON строк для сохранения разных категорий записей:
1) bill - { "cashier_uid":2, "card_uid":2, "products":{ "2":5, "3":2 } }
2) cashier - { "name": "xdxd", "surname": "xdxd" }
3) card - { "birthday": "2003-04-18", "discountId" : 2 }
4) card_type - { "title": "xd", "discountVal" : 5 }
5) product_discount - { "title": "xdxdxd", "discount" : 7, "required_quantity" : 7 }

Для действия обновления не обязательно передавать все параметры - только те, что изменяются,
а также id записи. 
Для save, аналогично поиску по id, можно задать параметр "pdf".

Для удаления достаточно только id записи.