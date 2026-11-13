package mobility;

import java.time.LocalDateTime;

public class MobilityService {

    public Rental rentBike(
            User user,
            Bike bike,
            Station station,
            LocalDateTime startTime) {

        // Vérifier la disponibilité
        if (!bike.isAvailable()) {
            throw new IllegalStateException("Bike is not available");
        }

        // Vérifier la présence dans la station
        if (!station.containsBike(bike)) {
            throw new IllegalArgumentException(
                "Bike does not belong to this station"
            );
        }

        // Retirer le vélo de la station
        station.removeBike(bike);

        // Rendre le vélo indisponible
        bike.setAvailable(false);

        // Créer une nouvelle location
        Rental rental = new Rental(user, bike, startTime);

        return rental;
    }

public void returnBike(
        Rental rental,
        Station station,
        LocalDateTime returnTime) {

    // 1. Vérifier que la location est active
    if (!rental.isActive()) {
        throw new IllegalStateException("Rental is not active");
    }

    // 2. Vérifier que la station n'est pas pleine
    if (station.isFull()) {
        throw new IllegalStateException("Station is full");
    }

    // 3. Vérifier que l'heure est valide
    if (returnTime.isBefore(rental.getStartTime())) {
        throw new IllegalArgumentException("Invalid return time");
    }

    // 4. Terminer la location
    rental.finish(returnTime);

    // 5. Rendre le vélo disponible
    rental.getBike().setAvailable(true);

    // 6. Ajouter le vélo à la station d'arrivée
    station.addBike(rental.getBike());
}
}