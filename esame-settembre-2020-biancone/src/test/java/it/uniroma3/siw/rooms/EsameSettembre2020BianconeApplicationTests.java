package it.uniroma3.siw.rooms;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.uniroma3.siw.rooms.service.*;
import junit.framework.Assert;
import it.uniroma3.siw.rooms.repository.*;
import it.uniroma3.siw.rooms.model.*;

@SuppressWarnings("deprecation")
@SpringBootTest
@RunWith(SpringRunner.class)
class EsameSettembre2020BianconeApplicationTests {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Before
	public void deleteAll() {
		System.out.println("Delete all data in the DB...");
		this.roomRepository.deleteAll();
		this.reservationRepository.deleteAll();
		System.out.println("Done");
	}

	@Test
	public void test() {

		/*
		 * Test Reservations by themselves
		 * */

		Reservation r1= new Reservation(LocalDate.of(2020, 11, 7), LocalDate.of(2020, 11, 13));
		Reservation r2= new Reservation(LocalDate.of(2020, 10, 21), LocalDate.of(2020, 11, 2));
		Reservation r3= new Reservation(LocalDate.of(2020, 11, 2), LocalDate.of(2020, 11, 5));

		this.reservationService.save(r1);
		this.reservationService.save(r2);
		this.reservationService.save(r3);

		List<Reservation> reservationList= this.reservationService.getFromTo(LocalDate.of(2020, 11, 3), LocalDate.of(2020, 11, 6));
		Assert.assertTrue(reservationList.contains(r3));
		Assert.assertFalse(reservationList.contains(r1));
		Assert.assertFalse(reservationList.contains(r2));

		reservationList= this.reservationService.getFromTo(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertTrue(reservationList.contains(r2));
		Assert.assertTrue(reservationList.contains(r3));
		Assert.assertFalse(reservationList.contains(r1));

		/*
		 * Test Rooms with Reservations
		 * */

		Room room1= new Room(349.50, 2, 2);
		Room room2= new Room(290, 1, 2);

		room1.addReservation(r1);
		room2.addReservation(r2);
		room2.addReservation(r3);

		r1.setRoom(room1);
		r2.setRoom(room2);
		r3.setRoom(room2);

		this.roomService.save(room1);
		this.roomService.save(room2);

		this.reservationService.save(r1);
		this.reservationService.save(r2);
		this.reservationService.save(r3);

		Assert.assertEquals(room1, r1.getRoom());
		Assert.assertEquals(room2, r2.getRoom());
		Assert.assertEquals(room2, r3.getRoom());
		Assert.assertTrue(room1.getReservation().contains(r1));
		Assert.assertTrue(room2.getReservation().contains(r2));
		Assert.assertTrue(room2.getReservation().contains(r3));

		Assert.assertTrue(this.roomRepository.findByReservationsIn
				(reservationList).contains(room2));
		Assert.assertFalse(this.roomRepository.findByReservationsIn
				(reservationList).contains(room1));

		/*
		 * Test to find reserved and free Rooms
		 * */

		List<Room> roomList= this.roomService.getReservedFromTo(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertFalse(roomList.contains(room1));
		Assert.assertTrue( roomList.contains(room2));

		roomList= this.roomService.getFreeFromTo(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertTrue(roomList.contains(room1));
		Assert.assertFalse(roomList.contains(room2));

		/*
		 * Test with a Room without Reservations
		 * */

		Room room3= new Room(340.50, 3, 1);

		this.roomService.save(room3);

		roomList= this.roomService.getPosti(4);
		Assert.assertTrue(roomList.contains(room1));
		Assert.assertFalse(roomList.contains(room2));
		Assert.assertTrue(roomList.contains(room3));

		roomList= this.roomService.getReservedFromTo(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertFalse(roomList.contains(room3));
		roomList= this.roomService.getFreeFromTo(LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertTrue(roomList.contains(room3));

		roomList= this.roomService.getReservable(4, LocalDate.of(2020, 10, 10), LocalDate.of(2020, 11, 5));
		Assert.assertTrue(roomList.contains(room1));
		Assert.assertFalse(roomList.contains(room2));
		Assert.assertTrue(roomList.contains(room3));
		
		roomList= this.roomService.getReservedOn(LocalDate.of(2020, 11, 2));
		Assert.assertTrue(roomList.contains(room2));
	}
}
