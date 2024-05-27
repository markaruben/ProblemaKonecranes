package com.example.problemakonecranesspring;

import com.example.problemakonecranesspring.Controllers.VehicleController;
import com.example.problemakonecranesspring.Models.Message;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//MessageHandler class implementing Runnable interface
public class MessageHandler implements Runnable {

    //BlockingQueue used to store the messages
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private final VehicleController vehicleController;

    //Constructor
    public MessageHandler(VehicleController vehicleController) {
        this.vehicleController = vehicleController;
    }

    //Method used to add a messaged to queue
    public void sendMessage(Message message) {
        messageQueue.offer(message);
    }

    //Overridden run method used to run the thread
    @Override
    public void run() {
        //Infinite loop
        while (true) {
            try {
                //Take a message from the queue and process it
                Message message = messageQueue.take();
                handleMessage(message);
            } catch (InterruptedException e) {
                //Handle exceptions here
                Thread.currentThread().interrupt();
                break;//exit the loop
            }
        }
    }

    //Method to process messages
    private void handleMessage(Message message) {
        switch (message.getCommand()) {
            case "UPDATE_DIRECTION":
                vehicleController.changeVehicleDirectionInternal(message.getVehicleId(), message.getValue());
                break;
            case "ADD_VEHICLE":
                vehicleController.addVehicleInternal(message.getVehicleId(), message.getX(), message.getY(), message.getDirection(), message.getSpeed());
                break;
            case "STOP_ALL":
                vehicleController.stopAllVehiclesInternal();
                break;
            case "START_ALL":
                vehicleController.startAllVehiclesInternal();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + message.getCommand());
        }
    }
}
