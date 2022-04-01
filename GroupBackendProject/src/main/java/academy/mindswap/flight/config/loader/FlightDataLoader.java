package academy.mindswap.flight.config.loader;

import academy.mindswap.flight.persistence.models.Flight;
import academy.mindswap.flight.persistence.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

@Component
public class FlightDataLoader {

    @Autowired
    private FlightRepository flightRepository;


    public void flightLoader(){
       /* userRepository.deleteAll();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder().id(i).name("user"+i).email("user" + i + "@email").password(i+"pass"+i).build();
            createIfNotFound(i, user);
        }*/

        LongConsumer operation = i ->{
            Flight flight = Flight.builder().flightNumber(""+ i).departureAirport("Airport"+i).arrivalAirport("Airport" + i + i).departureDate(i+"/"+i+"/"+i).arrivalDate(i+"/"+i+"/"+i).build();
            createIfNotFound(i+"", flight);
        };

        reloadData(operation,20);

        LongConsumer operation2 = i ->{
            Flight flight = Flight.builder().flightNumber(""+ i).departureAirport("Airport"+i).arrivalAirport("Airport" + i + i).departureDate(i+"/"+i+"/"+i).arrivalDate(i+"/"+i+"/"+i).build();
            createIfNotFound(i+"", flight);
        };

        reloadData(operation2,10);

        //IntStream.range(1, 100).forEach(operation);
    }

    public void reloadData(LongConsumer operation, Integer end) {

        LongStream.range(1, end).forEach(operation);
    }
    public void createIfNotFound(String id, Flight userToSave) {
        Optional<Flight> user = flightRepository.findById(id);
        user.ifPresentOrElse(
                u -> {
                    System.out.println("User already exists");
                },
                () -> {
                    System.out.println("User not found, creating...");

                    flightRepository.save(userToSave);
                }
        );
    }

}

