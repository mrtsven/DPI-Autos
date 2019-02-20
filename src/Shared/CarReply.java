package Shared;

import java.io.Serializable;

public class CarReply extends Car {

    // Car properties for Reply
    private String carName;
    private String registration;
    private String color;
    private Integer doors;
    private Integer price;


    public CarReply(String carName, String registration, String color, Integer doors, Integer price) {
        type = CarRequestType.REPLY;

        this.carName = carName;
        this.registration = registration;
        this.color = color;
        this.doors = doors;
        this.price = price;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarRequestType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "A " + getCarName() + ", the cost of it is: " + getPrice();
    }
}
