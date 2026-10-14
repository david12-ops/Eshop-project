package com.example.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderItemDTO {
    private Integer productId;// zde
    private Integer quantity;
    private BigDecimal lineTotal;
    private String notes;
    private BigDecimal unitPrice;
    private Boolean deleted = false;
    private Instant deletedAt;
    private OrderDTO orderDTO;

    public OrderItemDTO() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", lineTotal=" + lineTotal +
                ", notes='" + notes + '\'' +
                ", unitPrice=" + unitPrice +
                ", deleted=" + deleted +
                ", deletedAt=" + deletedAt +
                '}';
    }
}