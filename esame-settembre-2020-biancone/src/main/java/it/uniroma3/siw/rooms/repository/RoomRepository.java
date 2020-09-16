package it.uniroma3.siw.rooms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.rooms.model.Reservation;
import it.uniroma3.siw.rooms.model.Room;

/*
 * This interface is a CrudRepository for repository operations on Rooms
 * @see Room
 * */
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
	
	/*
	 * Method that finds all the Rooms that have the passed number of bed spaces
	 * @param posti The requested number of bed spaces
	 * @return A List containing all Rooms that have the requested number of bed spaces
	 * */
	@Query("select r from Room r where (((2*r.lettiMatrimoniali) + r.lettiSingoli) = ?1)")
	public List<Room> findByPosti(Integer posti);
	
	/*
	 * Method that finds all the Rooms that have Reservations that are in the passed List
	 * @param reservations List of Reservations
	 * @return A List containing all Rooms that have Reservations in the passed List
	 * */
	@Query
	public List<Room> findByReservationsIn(List<Reservation> reservations);
	
	/*
	 * Method that finds all the Rooms that have Reservations that are not in the passed List
	 * @param reservations List of Reservations
	 * @return A List containing all Rooms that don't have Reservations in the passed List
	 * */
	public List<Room> findByReservationsNotIn(List<Reservation> reservations);
	
	/*
	 * Method that finds all the Rooms that don't have any Reservations
	 * */
	public List<Room> findByReservationsIsNull();
	
}
