package com.example.problemakonecranesspring.Models;


//Message Class used when communicating between processes
public class Message {
    private int vehicleId;

    //Used to specify the action we want to make
    private String command;
    private double value;
    private double x;
    private double y;
    private double direction;
    private double speed;


    //Constructors
    public Message(int vehicleId, String command, double value) {
        this.vehicleId = vehicleId;
        this.command = command;
        this.value = value;
    }

    public Message(int vehicleId, String command, double x, double y, double direction, double speed) {
        this.vehicleId = vehicleId;
        this.command = command;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    //Getters and setters
    public int getVehicleId() {
        return vehicleId;
    }

    public String getCommand() {
        return command;
    }

    public double getValue() {
        return value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public double getSpeed() {
        return speed;
    }
}
