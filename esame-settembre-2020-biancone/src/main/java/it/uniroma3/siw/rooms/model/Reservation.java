package it.uniroma3.siw.rooms.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * This class represents the rental of an object Room from one date to another
 * */
@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/*
	 * Number of people per Room
	 * */
	@Column
	private Integer posti;

	/*
	 * Check-in date of the reservation
	 * */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable=false)
	private LocalDate checkIn;

	/*
	 * Check-out day of the reservation
	 * */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable=false)
	private LocalDate checkOut;

	/*
	 * The rented Room
	 * */
	@ManyToOne
	private Room room;

	/*
	 * Constructors
	 * */
	public Reservation() {

	}

	public Reservation(LocalDate checkIn, LocalDate checkOut) {
		this();
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	/*
	 * Getters and setters
	 * */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
		this.posti= (2*room.getLettiMatrimoniali())+ room.getLettiSingoli();
	}

	public Integer getPosti() {
		return posti;
	}

	public void setPosti(Integer posti) {
		this.posti = posti;
	}

	/*
	 * tostring, hashCode and equals methods
	 * */
	@Override
	public String toString() {
		return "Rental [id=" + id + ", posti= " + posti + ", check-in=" + checkIn 
				+ ", check-out=" + checkOut + ", room=" + room+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkIn == null) ? 0 : checkIn.hashCode());
		result = prime * result + ((checkOut == null) ? 0 : checkOut.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((posti == null) ? 0 : posti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Reservation other = (Reservation) obj;
		if (this.id==null && other.id != null)
			return false;
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}	

}
