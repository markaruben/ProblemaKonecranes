package com.example.problemakonecranesspring.Controllers;

import com.example.problemakonecranesspring.MessageHandler;
import com.example.problemakonecranesspring.Models.Message;
import com.example.problemakonecranesspring.Models.Vehicle;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//Controller for the Vehicle Model used to send the command messages ( to implement asynchronous communication )

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    //List of all the vehicles that are being instantiated
    private List<Vehicle> vehicles = new ArrayList<>();
    //Instance of MessageHandler
    private final MessageHandler messageHandler;

    //ID Counter to generate the unique id for each vehicle in every session
    private final AtomicInteger idCounter = new AtomicInteger();

    //Constructor
    public VehicleController() {
        this.messageHandler = new MessageHandler(this);
        new Thread(messageHandler).start();
    }

    //Methods used for request made from the gui to the server
    @PostMapping("/add")
    public void addVehicle() {
        int id = idCounter.incrementAndGet();
        messageHandler.sendMessage(new Message(id, "ADD_VEHICLE", 250, 250, 0.0, 5.0));
    }

    @PostMapping("/start")
    public void startAllVehicles() {
        messageHandler.sendMessage(new Message(0, "START_ALL", 0));
    }

    @PostMapping("/stop")
    public void stopAllVehicles() {
        messageHandler.sendMessage(new Message(0, "STOP_ALL", 0));
    }

    @PutMapping("/{id}/direction")
    public void changeVehicleDirection(@PathVariable int id, @RequestParam double direction) {
        messageHandler.sendMessage(new Message(id, "UPDATE_DIRECTION", direction));
    }

    @GetMapping
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    //Internal methods to be called from MessageHandler class
    public void addVehicleInternal(int id, double x, double y, double direction, double speed) {
        Vehicle vehicle = new Vehicle(id, x, y, direction, speed, true);
        vehicles.add(vehicle);
        new Thread(vehicle).start();
    }

    public void changeVehicleDirectionInternal(int vehicleId, double newDirection) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == vehicleId) {
                vehicle.setDirection(newDirection);
                break;
            }
        }
    }

    public void startAllVehiclesInternal() {
        for (Vehicle vehicle : vehicles) {
            vehicle.setRunning(true);
            new Thread(vehicle).start();
        }
    }

    public void stopAllVehiclesInternal() {
        for (Vehicle vehicle : vehicles) {
            vehicle.setRunning(false);
        }
    }
}
