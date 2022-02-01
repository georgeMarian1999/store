package com.project.model.dto;

public class ProductOrderDTO {
    String name;
    Float price;
    Integer quantity;


    public ProductOrderDTO(String name, Float price, Integer quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductOrderDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
