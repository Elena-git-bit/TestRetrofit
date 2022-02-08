package org.example;

import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import retrofit2.Response;
import org.example.dto.GetCategoryResponse;
import org.example.service.CategoryService;
import org.example.util.RetrofitUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for Mini Market Web Api.
 * Swagger Api documentation: http://80.78.248.82:8189/market/v2/api-docs
 * Base URL: 80.78.248.82:8189/market
 * GET Product Category Test
 */
public class GetCategoryTest
{
    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService =
                RetrofitUtils.getRetrofit().create(CategoryService.class);

    }

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

}
