package com.HomeBooking.HomeBooking.BO;

public class HouseBO {

    private String id;
    private String title;
    private String address;
    private Double price;

    public HouseBO() {
    }

    public HouseBO(String id, String title, String address, Double price) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                '}';
    }
}
