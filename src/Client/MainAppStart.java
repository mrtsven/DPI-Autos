package Client;

import Shared.*;

import javax.jms.JMSException;
import java.util.Scanner;

public class MainAppStart implements IListener, Runnable {

    private static Connector connector;
    Scanner scanner = new Scanner(System.in);
    private boolean locked = false;
    private String userInput;

    @Override
    public void run() {
        startConnection();
        System.out.println("Client has booted.");

        userInput = input();

        try {
            connector.sendMessage(new CarRequest(userInput), "carSearch", "request");
        } catch (JMSException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new MainAppStart();
        Thread thread = new Thread(runnable);
        thread.start();
//        while (true);
    }

    @Override
    public void onMessage(Car message) {
        CarReply carReply = (CarReply) message;
        System.out.println("Answer from vendor: ");
        System.out.println(carReply.toString());
        scanner.nextLine();
        locked = false;
    }

    private void startConnection(){
        connector = new Connector("carResult", this);
    }

    private String input(){
        System.out.println("Enter the car you're looking for:");
        return scanner.nextLine();
    }
}
