package it.uniroma3.siw.rooms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/*
 * The class represents a Room in a hotel
 * */
@Entity
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/*
	 * Price of the Room
	 * */
	@Column(nullable=false)
	private double prezzo;

	/*
	 * Number of duplex beds in the Room
	 * */
	@Column(nullable=false)
	private Integer lettiMatrimoniali;

	/*
	 * Number of single beds in the Room
	 * */
	@Column(nullable=false)
	private Integer lettiSingoli;

	/*
	 * List of all rentals to this Room
	 * */
	@OneToMany(mappedBy= "room", cascade= CascadeType.REMOVE)
	private List<Reservation> reservations;

	/*
	 * Constructors
	 * */
	public Room() {
		this.reservations= new ArrayList<Reservation>();
	}

	public Room(double prezzo, int lettiMatrimoniali, int lettiSingoli) {
		this();
		this.prezzo= prezzo;
		this.lettiMatrimoniali= lettiMatrimoniali;
		this.lettiSingoli= lettiSingoli;
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

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getLettiMatrimoniali() {
		return lettiMatrimoniali;
	}

	public void setLettiMatrimoniali(Integer lettiMatrimoniali) {
		this.lettiMatrimoniali = lettiMatrimoniali;
	}

	public Integer getLettiSingoli() {
		return lettiSingoli;
	}

	public void setLettiSingoli(Integer lettiSingoli) {
		this.lettiSingoli = lettiSingoli;
	}

	public List<Reservation> getReservation(){
		return this.reservations;
	}

	public void addReservation(Reservation r) {
		this.reservations.add(r);
	}

	/*
	 * tostring, hashCode and equals methods
	 * */
	@Override
	public String toString() {
		return "Room [id=" + id + ", prezzo=" + prezzo + ", lettiMatrimoniali=" + lettiMatrimoniali
				+ ", lettiSingoli=" + lettiSingoli + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lettiMatrimoniali == null) ? 0 : lettiMatrimoniali.hashCode());
		result = prime * result + ((lettiSingoli == null) ? 0 : lettiSingoli.hashCode());
		long temp;
		temp = Double.doubleToLongBits(prezzo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((reservations == null) ? 0 : reservations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Room other = (Room) obj;
		if (this.id==null && other.id != null)
			return false;
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}


}
