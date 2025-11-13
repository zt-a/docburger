

## **1. Получение всех блюд**

**Метод:** `GET`
**URL:** `/api/dishes`
**Описание:** Возвращает список всех блюд. Если блюд нет — возвращает 404 с сообщением.
**Пример ответа:**

```json
[
  {
    "id": 1,
    "name": "Pizza",
    "price": 10.5,
    "description": "Cheese pizza",
    "image": "/uploads/images/1699999999999_pizza.jpg",
    "category": {
      "id": 1,
      "name": "Fast Food"
    }
  }
]
```

---

## **2. Получение блюда по ID**

**Метод:** `GET`
**URL:** `/api/dishes/{id}`
**Описание:** Возвращает блюдо по его ID. Если блюдо не найдено — 404 с сообщением.
**Пример ответа:**

```json
{
  "id": 1,
  "name": "Pizza",
  "price": 10.5,
  "description": "Cheese pizza",
  "image": "/uploads/images/1699999999999_pizza.jpg",
  "category": {
    "id": 1,
    "name": "Fast Food"
  }
}
```

---

## **3. Получение блюд по категории**

**Метод:** `GET`
**URL:** `/api/dishes/category/{categoryId}`
**Описание:** Возвращает все блюда заданной категории.
**Пример:** `/api/dishes/category/1`

---

## **4. Создание нового блюда (с изображением)**

**Метод:** `POST`
**URL:** `/api/dishes`
**Content-Type:** `multipart/form-data`
**Поля:**

* `name` — название (обязательное)
* `price` — цена (обязательное)
* `description` — описание (необязательно)
* `categoryId` — ID категории (обязательное)
* `image` — файл изображения (необязательно)

**Описание:** Создаёт блюдо. Если прислали файл — сохраняется на сервер и путь записывается в поле `image`.

**Пример запроса через Postman или фронт:**

```
POST /api/dishes
Content-Type: multipart/form-data

name = "Pizza"
price = 10.5
description = "Cheese pizza"
categoryId = 1
image = (файл pizza.jpg)
```

**Пример ответа:**

```json
{
  "id": 5,
  "name": "Pizza",
  "price": 10.5,
  "description": "Cheese pizza",
  "image": "/uploads/images/1699999999999_pizza.jpg",
  "category": {
    "id": 1,
    "name": "Fast Food"
  }
}
```

---

## **5. Обновление блюда (с возможной заменой изображения)**

**Метод:** `PUT`
**URL:** `/api/dishes/{id}`
**Content-Type:** `multipart/form-data`
**Поля:** те же, что и для создания (`name`, `price`, `description`, `categoryId`, `image`).

**Описание:** Обновляет данные блюда. Если прислали новый файл — старая картинка заменяется новой.

**Пример запроса через Postman:**

```
PUT /api/dishes/5
Content-Type: multipart/form-data

name = "Pizza Large"
price = 12.0
description = "Cheese pizza large"
categoryId = 1
image = (новый файл pizza_large.jpg)
```

**Пример ответа:**

```json
{
  "id": 5,
  "name": "Pizza Large",
  "price": 12.0,
  "description": "Cheese pizza large",
  "image": "/uploads/images/1700000000000_pizza_large.jpg",
  "category": {
    "id": 1,
    "name": "Fast Food"
  }
}
```

---

## **6. Удаление блюда**

**Метод:** `DELETE`
**URL:** `/api/dishes/{id}`
**Описание:** Удаляет блюдо по ID.

**Пример ответа:**

```json
{
  "message": "Dish deleted successfully"
}
```

---

### **Важные моменты**

1. **Доступ к эндпоинтам:**

   * `GET /api/dishes` — доступно всем
   * `GET /api/dishes/{id}` — доступно всем
   * `GET /api/dishes/category/{categoryId}` — доступно всем
   * `POST /api/dishes` — только ADMIN
   * `PUT /api/dishes/{id}` — только ADMIN
   * `DELETE /api/dishes/{id}` — только ADMIN

2. **Изображения:**

   * Сохраняются на сервере в папке `uploads/images/`
   * В базе хранится путь `/uploads/images/...`
   * Клиент может использовать этот путь как URL для отображения картинки.


