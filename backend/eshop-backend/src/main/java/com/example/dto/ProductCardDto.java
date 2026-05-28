package com.example.dto;

import java.math.BigDecimal;

public class ProductCardDto {

    private Integer productId;

    private String productName;

    private String shortDescription;

    private BigDecimal price;

    private String categoryName;

    private String imageUrl;

    public ProductCardDto(
            Integer productId,
            String productName,
            String shortDescription,
            BigDecimal price,
            String categoryName,
            String imageUrl) {

        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}