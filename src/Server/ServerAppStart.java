package Server;

import Shared.*;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;

public class ServerAppStart implements IListener, Runnable {

    // Stuff
    private static Connector connector;
    private List<String> availableCars;

    public static void main(String[] args) {
        Runnable runnable = new ServerAppStart();
        Thread thread = new Thread(runnable);
        thread.run();

    }

    @Override
    public void run() {
        connection();
        System.out.println("Server has booted and a connection has been set.");
        setAvailableCars();
        awaitRequest();
    }

    @Override
    public void onMessage(Car message) {
        CarRequest carRequest = (CarRequest) message;
        System.out.println("Customer is looking for a: " + carRequest.getCar());

        if(checkForCar(carRequest.getCar())) {
            System.out.println("We have a: " + carRequest.getCar());

            try {
                System.out.println("Sending a reply to customer.");
                CarReply reply = new CarReply("BMW", "ws-xs-tt", "blue", 3, 40000);
                System.out.println("Sending this car: " + reply.getType());

                connector.sendMessage(reply, "carResult", "reply");

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void connection(){
        connector = new Connector("carSearch", this);
    }

    private void awaitRequest(){
        System.out.println("Waiting for a car request.");
    }

    private void setAvailableCars(){
        System.out.println("Producing a bunch of cars...");
        availableCars = new ArrayList<>();
        availableCars.add("BMW");
        availableCars.add("Mercedes");
        availableCars.add("SAAB");
        availableCars.add("Volkswagen");
    }

    private Boolean checkForCar(String carName){
        return availableCars.contains(carName);
    }
}
