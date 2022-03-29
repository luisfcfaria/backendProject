package academy.mindswap.lms.persistence.repositories;

import academy.mindswap.lms.persistence.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, String> {
}
