package com.project.model.dto;

import com.project.model.User;
import com.project.model.enums.OrderStatus;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private String phone;
    private String street;
    private String apartment;
    private String city;
    private String country;
    private Long postcode;
    private Float subtotal;
    private Float taxes;
    private Float total;
    private OrderStatus orderStatus;
    private Date date;
    private Float shipping;
    List<ProductOrderDTO> productOrderDTOList;

    public OrderDTO() {}

    public OrderDTO(Integer id, String phone, String street, String apartment, String city, String country, Long postcode, Float subtotal, Float taxes, Float total, OrderStatus orderStatus, Date date, Float shipping, List<ProductOrderDTO> productOrderDTOList) {
        this.id = id;
        this.phone = phone;
        this.street = street;
        this.apartment = apartment;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.orderStatus = orderStatus;
        this.date = date;
        this.shipping = shipping;
        this.productOrderDTOList = productOrderDTOList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPostcode() {
        return postcode;
    }

    public void setPostcode(Long postcode) {
        this.postcode = postcode;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getTaxes() {
        return taxes;
    }

    public void setTaxes(Float taxes) {
        this.taxes = taxes;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getShipping() {
        return shipping;
    }

    public void setShipping(Float shipping) {
        this.shipping = shipping;
    }

    public List<ProductOrderDTO> getProductOrderDTOList() {
        return productOrderDTOList;
    }

    public void setProductOrderDTOList(List<ProductOrderDTO> productOrderDTOList) {
        this.productOrderDTOList = productOrderDTOList;
    }
}
