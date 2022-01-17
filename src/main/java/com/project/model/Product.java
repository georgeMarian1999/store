package com.project.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String title;
    String description;
    Float price;
    Integer stock;
    Date addedDate;
    Integer sale;

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @ManyToOne
    @JoinColumn(name = "id_category")
    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {

    }


    public Product(String name, String title, String description, Float price, Integer stock, Date addedDate, Integer sale, Category category) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.addedDate = addedDate;
        this.sale = sale;
        this.category = category;
    }

    public Product(String name, String title, String description, Float price, Integer stock, Date addedDate, Category category) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.addedDate = addedDate;
        this.category = category;
        this.sale = 0;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product(String name, String title, String description, Float price, Integer stock) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
}
