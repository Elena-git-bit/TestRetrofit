package org.example;

import com.github.javafaker.Faker;
import okhttp3.ResponseBody;
import org.example.dto.Product;
import org.example.service.ProductService;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import org.example.util.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit test for Mini Market Web Api.
 * Swagger Api documentation: http://80.78.248.82:8189/market/v2/api-docs
 * Base URL: 80.78.248.82:8189/market
 */

public class CreateProductTest {

    static Map<String, String> headers = new HashMap<>();

    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    int id;
    int price;
    public static int curId;
    public static int curPrice;
    public static int fixPrice = 333;
    public static String title;
    public int ProductTest(int idTest, int priceTest){
        curId = idTest;
        curPrice = priceTest;
        return curId;
    };

    //Start once instance of service interface
    @BeforeAll
    public static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    //Generate for every test new product title and price
    @BeforeEach
    public void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    /**
     * POST Method Test
     * To do this
     * we first creat a new product with POST,
     * and then clean with tearDown()
     */

    @Test
    @SneakyThrows
    public void createProductInFoodCategoryTest() {

        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        price = response.body().getPrice();
        ProductTest(id, price);

    }

    /**
    * GET Method Test
    * To do this
    * we first creat a new product with POST,
     * check with GET
     * and then clean with tearDown()
    */
    @SneakyThrows
    @Test
    public void getProductByIdInFoodCategoryTest() {

        createProductInFoodCategoryTest();

        Response<ResponseBody> response =
                productService.getProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        ProductTest(id, price);
    }

    /**
     * PUT Method Test
     * To do this
     * we first create a new product with POST,
     * replace price generated by Faker
     * with fixPrice,
     * modify with PUT
     * and then clean with tearDown()
     */
    @Test
    @SneakyThrows
    public void modifyProductInFoodCategoryTest() {

        createProductInFoodCategoryTest();//Call method to create new product with Faker
        //Get product id from web service interface
        Response<ResponseBody> response =
                productService.getProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id = curId;
        price = fixPrice;

        Product curProduct = this.product;//Create new instance of Product interface
        curProduct.setId(id);//Write back product id to current product

        //Modify current product with real product id from web service
        Response<Product> response1 = productService.modifyProduct(curProduct.withId(id))
                .execute();
        id = response1.body().getId();
        assertThat(response1.isSuccessful(), CoreMatchers.is(true));

        //Replace price generated bt Faker with fixPrice
        Response<Product> response2 = productService.modifyProduct(curProduct.withPrice(price))
                .execute();
        price = response2.body().getPrice();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

    }

    /**
     * DELETE Method Test
     * To do this
     * we first create a new product with POST,
     * and then clean with tearDown()
     */
    @Test
    @SneakyThrows
    public void deleteProductInFoodCategoryTest() {

        createProductInFoodCategoryTest();//Call method to create new product with Faker

    }

    @SneakyThrows
    @AfterEach
    public void tearDown() {

        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

}
