package it.uniroma3.siw.rooms.controller;

import java.text.DecimalFormat;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.rooms.controller.validator.ReservationValidator;
import it.uniroma3.siw.rooms.model.Reservation;
import it.uniroma3.siw.rooms.service.RoomService;

/*
 * Controller that involves all of the client interactions
 * */
@Controller
@Scope("session")
public class ClientController {

	/*
	 * Reservation to be stored throughout the session
	 * to calculate the total cost
	 * */
	private Reservation reservation= new Reservation();

	@Autowired
	RoomService roomService;
	@Autowired
	ReservationValidator reservationValidator;

	/*
	 * This method is called when a GET request is sent by the user to URL "/reservation/form".
	 * This method prepares and dispatches the Reservation Form view.
	 *
	 * @param model the Request model
	 * @return the name of the target view, that in this case is "reservationForm"
	 * */
	@RequestMapping(value="/reservation/form", method=RequestMethod.GET)
	public String getReservationForm(Model model) {
		model.addAttribute("reservation", this.reservation);
		return "reservationForm";
	}

	/**
	 * This method is called when a POST request is sent by the user to URL "/reservation/form".
	 * This method sends a List containing all free Rooms in the requested period with the
	 * requested number of bed spaces and saves it in the model for the next view.
	 * If any data wasn't valid, then it resends the current page with the required error messages
	 *
	 * @param model the Request model
	 * @return the name of the target view, that in this case is "rooms" or reservationForm
	 */
	@RequestMapping(value="/reservation/form", method=RequestMethod.POST)
	public String getRoomsForm(@Valid @ModelAttribute("reservation") Reservation reservation, 
			BindingResult bindingResult, Model model) {
		this.reservation= reservation;
		this.reservationValidator.validate(reservation, bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("rooms", this.roomService.getReservable(reservation.getPosti(),
					reservation.getCheckIn(), reservation.getCheckOut()));
			return "rooms";
		}
		return "reservationForm";
	}

	/*
	 * This method is called when a GET request is sent by the user to URL "/room/id,
	 * where id is the id of a Room in the DB".
	 * This method prepares and dispatches the Room view.
	 *
	 * @param id the id of the requested Room
	 * @param model the Request model
	 * @return the name of the target view, that in this case is "room"
	 * */
	@RequestMapping(value="/room/{id}", method=RequestMethod.GET)
	public String getRoom(@PathVariable("id") Long id, Model model) {
		model.addAttribute("freeRoom", this.roomService.getRoom(id));
		model.addAttribute("reservation", this.reservation);
		model.addAttribute("totalCost", new DecimalFormat("#.##").format(
				this.roomService.getRoom(id).getPrezzo() *
				this.reservation.getCheckIn().until(
						this.reservation.getCheckOut(), ChronoUnit.DAYS)));
		return "room";
	}

}
