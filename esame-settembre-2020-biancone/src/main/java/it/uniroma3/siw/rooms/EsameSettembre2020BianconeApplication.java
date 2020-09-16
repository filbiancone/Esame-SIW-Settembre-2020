 package it.uniroma3.siw.rooms;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import it.uniroma3.siw.rooms.model.Reservation;
import it.uniroma3.siw.rooms.model.Room;
import it.uniroma3.siw.rooms.repository.ReservationRepository;
import it.uniroma3.siw.rooms.repository.RoomRepository;

@SpringBootApplication
public class EsameSettembre2020BianconeApplication {

	/*
	 * Reference to application.properties
	 * */
	@SuppressWarnings("unused")
	@Autowired
	private Environment environment;
	
	public static void main(String[] args) {
		SpringApplication.run(EsameSettembre2020BianconeApplication.class, args);
	}

	/*
	 * Method that saves various Rooms and Reservations in the DB
	 * when Spring Boot app starts up
	 * */
	@Bean
    public CommandLineRunner init (RoomRepository roomRepository,
    		ReservationRepository reservationRepository){
        return args -> {
            Room ro1= new Room(49.49, 3, 2);
            Room ro2= new Room(40.9, 3, 1);
            Room ro3= new Room(39, 2, 2);
            Room ro4= new Room(29.9, 1, 2);
            
            Reservation r1= new Reservation(LocalDate.of(2020, 10, 1),
            		LocalDate.of(2020, 10, 8));
            Reservation r2= new Reservation(LocalDate.of(2020, 9, 27),
            		LocalDate.of(2020, 10, 5));
            Reservation r3= new Reservation(LocalDate.of(2020, 10, 9),
            		LocalDate.of(2020, 10, 16));
            Reservation r4= new Reservation(LocalDate.of(2020, 10, 21),
            		LocalDate.of(2020, 11, 1));
            Reservation r5= new Reservation(LocalDate.of(2020, 10, 1),
            		LocalDate.of(2020, 10, 5));
            
            ro1.addReservation(r1);
            r1.setRoom(ro1);
            ro1.addReservation(r3);
            r3.setRoom(ro1);
            
            ro2.addReservation(r2);
            r2.setRoom(ro2);
            
            ro3.addReservation(r5);
            r5.setRoom(ro3);
            ro3.addReservation(r4);
            r4.setRoom(ro3);
            
            roomRepository.save(ro1);
            roomRepository.save(ro2);
            roomRepository.save(ro3);
            roomRepository.save(ro4);
            
            reservationRepository.save(r1);
            reservationRepository.save(r2);
            reservationRepository.save(r3);
            reservationRepository.save(r4);
            reservationRepository.save(r5);
        };
    }
	
}
