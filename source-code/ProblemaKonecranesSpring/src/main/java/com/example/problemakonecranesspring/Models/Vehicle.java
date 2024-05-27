package com.example.problemakonecranesspring.Models;

import java.awt.*;
import java.util.Random;

//Vehicle class implementing Runnable interface
public class Vehicle implements Runnable {

    private int id;
    private double x, y;
    private double direction;
    private double speed;
    private boolean running;
    private Color color;

    //Constructor
    public Vehicle(int id, double x, double y, double direction, double speed, boolean running) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
        this.running = running;
        this.color = getRandomColor();
    }

    //Method used to generate a color for the vehicle
    private Color getRandomColor() {
        Random rand = new Random();
        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    //Method to calculate the new coordiantes of the vehicle while moving
    public void move(double delta) {
        double directionInRadians = Math.toRadians(direction);

        x += speed * Math.cos(directionInRadians) * delta;
        y += speed * Math.sin(directionInRadians) * delta;
    }


    //Overridden run method to run the thread( it calculates the elapsed time to call the move method )
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        //Infinite loop
        while (running) {
            long currentTime = System.nanoTime();
            //Calculating elapsed time
            double delta = (currentTime - lastTime) / 1_000_000_000.0;
            lastTime = currentTime;
            //Calling move method
            move(delta);
            try {
                //Make the thread sleep to simulate real life
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //Handle exceptions here
                e.printStackTrace();
            }
        }
    }

    //Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    //toString method
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", speed=" + speed +
                ", running=" + running +
                ", color=" + color +
                '}';
    }
}
