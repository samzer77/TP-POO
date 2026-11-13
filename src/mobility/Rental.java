package mobility;

import java.time.LocalDateTime;

public class Rental {

    private User user;
    private Bike bike;
    private LocalDateTime startTime;
    private LocalDateTime returnTime;

    public Rental(User user, Bike bike, LocalDateTime startTime) {

        this.user = user;
        this.bike = bike;
        this.startTime = startTime;
        this.returnTime = null;
    }

    public User getUser() {
        return user;
    }

    public Bike getBike() {
        return bike;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public boolean isActive() {
        return returnTime == null;
    }

    public void finish(LocalDateTime returnTime) {

        if (!isActive()) {
            throw new IllegalStateException("Rental already finished");
        }

        if (returnTime.isBefore(startTime)) {
            throw new IllegalArgumentException("Invalid return time");
        }

        this.returnTime = returnTime;
    }
}
