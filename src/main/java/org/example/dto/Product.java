package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
//--> Used for GET: import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

//
//--> Used for GET
@Data
//<-- Used for Get

//--> Used for POST
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
//<-- Used for POST

public class Product {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("categoryTitle")
    private String categoryTitle;
}

