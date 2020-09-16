package it.uniroma3.siw.rooms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.rooms.model.Room;
import it.uniroma3.siw.rooms.repository.RoomRepository;

/**
 * The RoomService handles logic regarding Rooms.
 * This mainly consists in retrieving or storing Rooms in the DB.
 */
@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ReservationService reservationService;

	/*
	 * Find Room in DB by the passed id
	 * @param id The id of the Room
	 * @return o If there is a Room with the passed id, it will return it, or else it returns null 
	 * */
	@Transactional
	public Room getRoom(Long id) {
		Optional<Room> o= this.roomRepository.findById(id);
		return o.orElse(null);
	}

	/*
	 * Returns a List containing all Rooms
	 * @return list A List containing all Rooms in DB
	 * */
	@Transactional
	public List<Room> getAllRooms(){
		List<Room> list= new ArrayList<Room>();
		Iterable<Room> iterable= this.roomRepository.findAll();
		for(Room room: iterable)	list.add(room);
		return list;
	}

	/*
	 * Stores an entity Room in the DB
	 * @param room The entity to be stored in the DB
	 * @return room The same entity
	 * */
	@Transactional
	public Room save(Room room) {
		return this.roomRepository.save(room);
	}
	
	/*
	 * Finds all Rooms with the passed number of bed spaces
	 * @posti The required number of bed spaces
	 * @return A List containing all Rooms with the requested bed spaces
	 * */
	@Transactional
	public List<Room> getPosti(Integer posti){
		return this.roomRepository.findByPosti(posti);
	}

	/*
	 * Find all Rooms that aren't reserved on a certain date without repetitions
	 * @param of The date we need to check
	 * @return The List containing all free Rooms on that day
	 * */
	public List<Room> getFreeOn(LocalDate of) {
		List<Room> l= this.getAllRooms();
		l.removeAll(this.getReservedOn(of));
		return l;
	}
	
	/*
	 * Finds all Rooms that are reserved on a certain date without repetitions
	 * @param of The date we need to check
	 * @return The List containing all reserved Rooms on that day
	 * */
	public List<Room> getReservedOn(LocalDate of) {
		List<Room> l= new ArrayList<>(new HashSet<Room>(this.roomRepository.findByReservationsIn(
				this.reservationService.getFromTo(of, of))));
		return l;
	}
	
	/*
	 * Finds all Rooms that are reserved from a certain date to another
	 * @param from The Date from which we need to check
	 * @param to The Date to which we need to check
	 * @return The List containing all reserved Rooms in this period
	 * */
	@Transactional
	public List<Room> getReservedFromTo(LocalDate from, LocalDate to){
		return this.roomRepository.findByReservationsIn(
				this.reservationService.getFromTo(from, to));
	}

	/*
	 * Finds all Rooms that aren't reserved from a certain date to another
	 * @param from The Date from which we need to check
	 * @param to The Date to which we need to check
	 * @return The List containing all free Rooms in this period
	 * */
	@Transactional
	public List<Room> getFreeFromTo(LocalDate from, LocalDate to){
		List<Room> l= this.roomRepository.findByReservationsNotIn(
				this.reservationService.getFromTo(from, to));
		l.addAll(this.roomRepository.findByReservationsIsNull());
		return l;
	}

	/*
	 * Finds all Rooms with the required bed places from a certain date to another
	 * @param posti The required number of bed places
	 * @param from The arrival date
	 * @param to The departure date
	 * @return The List of available Rooms with the required number of bed spaces in this period
	 * */
	@Transactional
	public List<Room> getReservable(Integer posti, LocalDate from, LocalDate to){
		List<Room> l= this.getPosti(posti);
		l.removeAll(this.getReservedFromTo(from, to));
		return l;
	}

}
