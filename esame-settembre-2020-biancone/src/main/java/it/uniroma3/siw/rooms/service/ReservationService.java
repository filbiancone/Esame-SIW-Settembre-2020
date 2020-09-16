package it.uniroma3.siw.rooms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.rooms.model.Reservation;
import it.uniroma3.siw.rooms.repository.ReservationRepository;

/**
 * The ReservationService handles logic regarding Reservations.
 * This mainly consists in retrieving or storing Reservations in the DB.
 */
@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	/*
	 * Find Reservation in DB by the passed id
	 * @param id The id of the Reservation
	 * @return o If there is a Reservation with the passed id, it will return it, or else it returns null 
	 * */
	@Transactional
	public Reservation getReservation(Long id) {
		Optional<Reservation> o= this.reservationRepository.findById(id);
		return o.orElse(null);
	}
	
	/*
	 * Returns a List containing all Reservations
	 * @return list A List containing all Reservations in DB
	 * */
	@Transactional
	public List<Reservation> getAllReservations(){
		List<Reservation> list= new ArrayList<Reservation>();
		Iterable<Reservation> iterable= this.reservationRepository.findAll();
		for(Reservation reservation: iterable)	list.add(reservation);
		return list;
	}
	
	/*
	 * Stores an entity Reservation in the DB
	 * @param reservation The entity to be stored in the DB
	 * @return reservation The same entity
	 * */
	@Transactional
	public Reservation save(Reservation reservation) {
		return this.reservationRepository.save(reservation);
	}
	
	/*
	 * Finds all Reservations in the passed time period
	 * @param from The beginning of the period
	 * @param to The end of the period
	 * @return The List of Reservations in this period
	 * */
	@Transactional
	public List<Reservation> getFromTo(LocalDate from, LocalDate to){
		return this.reservationRepository.
				findByCheckInBetweenOrCheckOutBetween(from, to);
	}
}
