package com.example.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderItemDTO {
    private Integer orderItemId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal lineTotal;
    private String notes;
    private BigDecimal unitPrice;
    private Boolean deleted = false;
    private Instant deletedAt;
    private OrderDTO orderDTO;

    public OrderItemDTO() {
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
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

    public Boolean isDeleted() {
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
                ", orderItemId=" + orderItemId +
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