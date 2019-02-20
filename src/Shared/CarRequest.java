package Shared;

public class CarRequest extends Car{
    private String car;

    public CarRequest(String car){
        type = CarRequestType.REQUEST;
        this.car = car;
    }

    public String getCar(){
        return car;
    }
}
