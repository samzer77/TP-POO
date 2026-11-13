package mobility;

import java.util.ArrayList;
import java.util.List;

public class Station {

    private String name;
    private int capacity;
    private List<Bike> bikes;

    public Station(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.bikes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isFull() {
        return bikes.size() >= capacity;
    }

    public void addBike(Bike bike) {

        if (isFull()) {
            throw new IllegalStateException("Station is full");
        }

        if (bikes.contains(bike)) {
            throw new IllegalArgumentException("Bike already present");
        }

        bikes.add(bike);
    }

    public void removeBike(Bike bike) {

        if (!bikes.contains(bike)) {
            throw new IllegalArgumentException("Bike not found");
        }

        bikes.remove(bike);
    }

    public boolean containsBike(Bike bike) {
        return bikes.contains(bike);
    }
}