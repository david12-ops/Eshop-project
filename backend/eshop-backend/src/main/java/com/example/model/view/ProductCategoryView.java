package com.example.model.view;

import java.math.BigDecimal;
import java.time.Instant;

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

    private Instant productCreatedAt;
    private Instant productUpdatedAt;

    private String categoryName;
    private String categoryDescription;

    private boolean categoryIsActive;

    private Instant categoryCreatedAt;
    private Instant categoryUpdatedAt;

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

    public Instant getProductCreatedAt() {
        return productCreatedAt;
    }

    public void setProductCreatedAt(Instant productCreatedAt) {
        this.productCreatedAt = productCreatedAt;
    }

    public Instant getProductUpdatedAt() {
        return productUpdatedAt;
    }

    public void setProductUpdatedAt(Instant productUpdatedAt) {
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

    public Instant getCategoryCreatedAt() {
        return categoryCreatedAt;
    }

    public void setCategoryCreatedAt(Instant categoryCreatedAt) {
        this.categoryCreatedAt = categoryCreatedAt;
    }

    public Instant getCategoryUpdatedAt() {
        return categoryUpdatedAt;
    }

    public void setCategoryUpdatedAt(Instant categoryUpdatedAt) {
        this.categoryUpdatedAt = categoryUpdatedAt;
    }
}