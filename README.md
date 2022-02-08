# TestRetrofit 

В проекте TestRetrofit находятся автотесты для CRUD-запросов, относящихся к сервису продуктов с использованием
retrofit. Сервис продуктов расположен: [market/swagger](http://80.78.248.82:8189/market/swagger-ui.html#/).

```java
Api Documentation 1.0 

[ Base URL: 80.78.248.82:8189/market ]

http://80.78.248.82:8189/market/v2/api-docs
Api Documentation
Terms of service
Apache 2.0
```

В автотестах используются два класса:

- ```GetCategoryTest``` - Выводит все продукты категирии Food
- `CreateProductTest` - Выполняет все тестовые методы согласно Api документации.

В классе GetCategoryTest реализован метод:

```java
    @SneakyThrows
    @Test
    public void getCategoryByIdPositiveTest() {

        Response<GetCategoryResponse> response =
                categoryService.getCategory(1).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
            assertThat(response.body().getId(), equalTo(1));
            assertThat(response.body().getTitle(), equalTo("Food"));
            response.body().getProducts().forEach(product ->
                    assertThat(product.getCategoryTitle(), equalTo("Food")));
    }
```
В классе CreateProductTest реализованы следующие методы:

- `createProductInFoodCategoryTest()` - выполняет тест метода POST, который создает новый продукт, используя утилиту Faker для генерации наименоваия и цены. Этот метод использует интерфейс:

  ```java
  @POST("products")
  Call<Product> createProduct(@Body Product createProductRequest);
  ```

- `getProductByIdInFoodCategoryTest()` - выполняет тест метода GET. Для этого последовательно вызывается метод createProductInFoodCategoryTest() для создания нового продукта, затем выполняется запрос GET к сервису через интерфейс:

  ```java
  @GET("products/{id}")
  Call<ResponseBody> getProduct(@Path("id") int id);
  ```

- `modifyProductInFoodCategoryTest()` - выполняет тест метода PUT для модификации продукта. Для этого последовательно вызывается метод createProductInFoodCategoryTest() для создания нового продукта с произвольным наименованием и ценой, затем выполняется запрос GET к сервису для получения актуального id продукта и следующий запрос для обновления поля цены с фиксированной величиной fixPrice = 333. Этот метод использует интерфейс:

  ```java
  @PUT("products")
  Call<Product> modifyProduct(@Body Product modifyProductRequest);
  ```

- `deleteProductInFoodCategoryTest()` -  выполняет тест метода DELETE для удаления продукта. Для этого вызывается метод createProductInFoodCategoryTest() для создания нового продукта, удаление продукта происходит с помощью метода tearDown() после завершения теста. Этот метод использует интерфейс:

  ```java
  @DELETE("products/{id}")
  Call<ResponseBody> deleteProduct(@Path("id") int id);
  ```
  

## Логирование тестов

Логирование тестов происходит с помощбю перхватчика  `okhttp3.logging.HttpLoggingInterceptor` Результат тестов выводится в терминал в отформатированном виде  с помощью`PrettyLogger`Пример результата логирования:

```java
INFO: Date: Tue, 08 Feb 2022 17:40:45 GMT
февр. 08, 2022 8:40:45 PM okhttp3.internal.platform.Platform log
INFO: Keep-Alive: timeout=60
февр. 08, 2022 8:40:45 PM okhttp3.internal.platform.Platform log
INFO: Connection: keep-alive
февр. 08, 2022 8:40:45 PM okhttp3.internal.platform.Platform log
INFO: 
февр. 08, 2022 8:40:45 PM okhttp3.internal.platform.Platform log
INFO: {
  "id" : 25561,
  "title" : "Bread",
  "price" : 1067,
  "categoryTitle" : "Food"
}
февр. 08, 2022 8:40:45 PM okhttp3.internal.platform.Platform log
INFO: <-- END HTTP (64-byte body)
```

[1]: 