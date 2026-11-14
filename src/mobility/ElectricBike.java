
package mobility;

public class ElectricBike extends Bike {

    private int batteryLevel;

    // Constructeur
    public ElectricBike(String id, int batteryLevel) {
        super(id);

        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException(
                "Invalid battery level"
            );
        }

        this.batteryLevel = batteryLevel;
    }

    // Getter
    public int getBatteryLevel() {
        return batteryLevel;
    }

}
