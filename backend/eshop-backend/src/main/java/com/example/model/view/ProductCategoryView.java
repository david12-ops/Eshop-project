package com.example.model.view;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ProductCategoryView {

    private Integer productId;
    private String productName;
    private String productCode;
    private String productDescription;

    private BigDecimal recommendedPrice;
    private BigDecimal unitPrice;
    private BigDecimal taxRate;

    private Integer categoryId;

    private boolean productIsActive;

    private OffsetDateTime productCreatedAt;
    private OffsetDateTime productUpdatedAt;

    private String categoryName;
    private String categoryDescription;

    private boolean categoryIsActive;

    private OffsetDateTime categoryCreatedAt;
    private OffsetDateTime categoryUpdatedAt;

    public ProductCategoryView() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isProductIsActive() {
        return productIsActive;
    }

    public void setProductIsActive(boolean productIsActive) {
        this.productIsActive = productIsActive;
    }

    public OffsetDateTime getProductCreatedAt() {
        return productCreatedAt;
    }

    public void setProductCreatedAt(OffsetDateTime productCreatedAt) {
        this.productCreatedAt = productCreatedAt;
    }

    public OffsetDateTime getProductUpdatedAt() {
        return productUpdatedAt;
    }

    public void setProductUpdatedAt(OffsetDateTime productUpdatedAt) {
        this.productUpdatedAt = productUpdatedAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public boolean isCategoryIsActive() {
        return categoryIsActive;
    }

    public void setCategoryIsActive(boolean categoryIsActive) {
        this.categoryIsActive = categoryIsActive;
    }

    public OffsetDateTime getCategoryCreatedAt() {
        return categoryCreatedAt;
    }

    public void setCategoryCreatedAt(OffsetDateTime categoryCreatedAt) {
        this.categoryCreatedAt = categoryCreatedAt;
    }

    public OffsetDateTime getCategoryUpdatedAt() {
        return categoryUpdatedAt;
    }

    public void setCategoryUpdatedAt(OffsetDateTime categoryUpdatedAt) {
        this.categoryUpdatedAt = categoryUpdatedAt;
    }
}