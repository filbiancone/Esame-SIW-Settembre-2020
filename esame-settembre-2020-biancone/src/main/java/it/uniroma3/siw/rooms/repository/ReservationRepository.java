package it.uniroma3.siw.rooms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.rooms.model.Reservation;

/*
 * This interface is a CrudRepository for repository operations on Reservations
 * @see Reservation
 * */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	/*
	 * Method to find Reservations with a passed value of guests
	 * @param posti The number of guests
	 * @return The List of Reservations with the passed number of guests
	 * */
	public List<Reservation> findByPosti(Integer posti);

	/*
	 * Method to find all Reservations between two passed dates
	 * @param checkIn check-in Date
	 * @param checkOut check-out Date
	 * @return The List of Reservations in between the passed Dates
	 * */
	@Query("select r from Reservation r where "
			+ "((?1 between r.checkIn AND r.checkOut) OR"
			+ " (?2 between r.checkIn AND r.checkOut) OR"
			+ " (r.checkIn between ?1 AND ?2) OR"
			+ " (r.checkOut between ?1 AND ?2))")
	public List<Reservation> findByCheckInBetweenOrCheckOutBetween(LocalDate checkIn, LocalDate checkOut);
	
}
