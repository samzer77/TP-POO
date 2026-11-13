
import mobility.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    static DateTimeFormatter format =
            DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {

        // SCENARIO PRINCIPAL
        System.out.println("===== SCENARIO PRINCIPAL =====");
        scenarioPrincipal();

        // TEST 7.1
        System.out.println("\n===== TEST 7.1 : STATION PLEINE =====");
        testStationPleine();

        // TEST 7.2
        System.out.println("\n===== TEST 7.2 : VELO DEJA LOUE =====");
        testVeloDejaLoue();

        // TEST 7.3
        System.out.println("\n===== TEST 7.3 : DATE DE RETOUR INVALIDE =====");
        testDateRetourInvalide();

        // TEST 7.4
        System.out.println("\n===== TEST 7.4 : RETOUR DANS UNE STATION PLEINE =====");
        testRetourStationPleine();
    }


    // SCENARIO PRINCIPAL
    public static void scenarioPrincipal() {

        Station campus = new Station("Campus Nord", 10);
        Station bibliotheque = new Station("Bibliothèque", 10);

        User alice = new User("U01", "Alice");
        Bike bike = new Bike("B-104");

        campus.addBike(bike);

        MobilityService service = new MobilityService();

        LocalDateTime start = LocalDateTime.now();

        System.out.println("B-104 initially at Campus Nord");

        Rental rental = service.rentBike(
                alice, bike, campus, start
        );

        System.out.println("Alice rents B-104 at " + start.format(format));
        System.out.println("B-104 available: " + bike.isAvailable());

        LocalDateTime end = LocalDateTime.now();

        service.returnBike(rental, bibliotheque, end);

        System.out.println(
                "Alice returns B-104 at Bibliothèque at " + end.format(format)
        );

        System.out.println("Rental active: " + rental.isActive());

        System.out.println(
                "B-104 now at Bibliothèque: "
                + bibliotheque.containsBike(bike)
        );
    }


    // TEST 7.1 : Ajouter un vélo dans une station pleine
    public static void testStationPleine() {

        Station station = new Station("Test", 1);

        Bike b1 = new Bike("B-001");
        Bike b2 = new Bike("B-002");

        station.addBike(b1);

        try {
            station.addBike(b2);

        } catch (IllegalStateException e) {
            System.out.println("Ajout refusé : " + e.getMessage());
        }

        System.out.println("Station pleine : " + station.isFull());
        System.out.println("B-002 présent : " + station.containsBike(b2));
    }


    // TEST 7.2 : Essayer d'emprunter un vélo déjà loué
    public static void testVeloDejaLoue() {

        Station campus = new Station("Campus Nord", 10);

        User alice = new User("U01", "Alice");
        Bike bike = new Bike("B-104");

        campus.addBike(bike);

        MobilityService service = new MobilityService();

        LocalDateTime start = LocalDateTime.now();

        // Premier emprunt
        Rental rental = service.rentBike(
                alice, bike, campus, start
        );

        // Deuxième tentative avec le même vélo
        try {
            service.rentBike(
                    alice, bike, campus, LocalDateTime.now()
            );

        } catch (IllegalStateException e) {
            System.out.println("Deuxième emprunt refusé : " + e.getMessage());
        }

        System.out.println("Location toujours active : " + rental.isActive());
        System.out.println("Vélo disponible : " + bike.isAvailable());
    }


    // TEST 7.3 : Essayer de retourner le vélo avant l'heure de départ
    public static void testDateRetourInvalide() {

        User alice = new User("U01", "Alice");
        Bike bike = new Bike("B-104");

        LocalDateTime start = LocalDateTime.now();

        Rental rental = new Rental(alice, bike, start);

        // Heure de retour antérieure au départ
        LocalDateTime invalidReturn = start.minusHours(1);

        try {
            rental.finish(invalidReturn);

        } catch (IllegalArgumentException e) {
            System.out.println("Retour refusé : " + e.getMessage());
        }

        System.out.println("Location toujours active : " + rental.isActive());
    }


    // TEST 7.4 : Retourner un vélo dans une station pleine
    public static void testRetourStationPleine() {

        Station depart = new Station("Départ", 5);
        Station arrivee = new Station("Arrivée", 1);

        User alice = new User("U01", "Alice");

        Bike bike1 = new Bike("B-104");
        Bike bike2 = new Bike("B-205");

        depart.addBike(bike1);

        // On remplit la station d'arrivée
        arrivee.addBike(bike2);

        MobilityService service = new MobilityService();

        LocalDateTime start = LocalDateTime.now();

        // Alice emprunte B-104
        Rental rental = service.rentBike(
                alice, bike1, depart, start
        );

        // Tentative de retour dans la station pleine
        try {
            service.returnBike(
                    rental, arrivee, start.plusHours(2)
            );

        } catch (IllegalStateException e) {
            System.out.println("Retour refusé : " + e.getMessage());
        }

        // Vérification de l'état après l'échec
        System.out.println("Location toujours active : " + rental.isActive());
        System.out.println("Vélo disponible : " + bike1.isAvailable());
        System.out.println("B-104 présent à Arrivée : " + arrivee.containsBike(bike1));
        System.out.println("Station Arrivée pleine : " + arrivee.isFull());
    }
}
