package it.uniroma3.siw.rooms.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.rooms.controller.validator.ReservationValidator;
import it.uniroma3.siw.rooms.controller.validator.RoomValidator;
import it.uniroma3.siw.rooms.model.Reservation;
import it.uniroma3.siw.rooms.model.Room;
import it.uniroma3.siw.rooms.service.RoomService;

/*
 * Controller that handles all of the admin interactions
 * */
@Controller
public class AdminController {

	/*
	 * LocalDate variable with prefixed format
	 * */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate onDate;
	
	@Autowired
	RoomService roomService;
	@Autowired
	ReservationValidator reservationValidator;
	@Autowired
	RoomValidator roomValidator;
	
	/*
	 * This method is called when a GET request is sent to the URL /admin.
	 * This method prepares and dispatches the Admin view
	 * @param model The request model
	 * @return The name of the target view, which in this case is "admin"
	 * */
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String getAdminPage(Model model) {
		return "admin";
	}
	
	/*
	 * This method is called when a GET request is sent to the url /reservations/form.
	 * This method prepares and dispatches the Reservations Form view
	 * @param model The request model
	 * @return The name of the target view, which in this case is "reservationsForm"
	 * */
	@RequestMapping(value="/reservations/form", method=RequestMethod.GET)
	public String getAdminReservationForm(Model model) {
		model.addAttribute("onDate", new Reservation());
		return "reservationsForm";
	}

	/*
	 * This method is called when a POST request is sent to the url /reservations/form.
	 * This method evaluates the recieved date from the form and prepares a List containing
	 * all the free rooms and all the reserved rooms on the recieved date
	 * @param model The request model
	 * @return The target view, which is "roomsAdmin" if the parameter "onDate" is valid,
	 * "reservationsForm" otherwhise
	 * */
	@RequestMapping(value="/reservations/form", method=RequestMethod.POST)
	public String getRoomsFromTo(@Valid @ModelAttribute("onDate") Reservation reservation,
			BindingResult bindingResult, Model model) {
		this.reservationValidator.adminValidate(reservation, bindingResult);
		if(!bindingResult.hasErrors()) {
			LocalDate localDate= reservation.getCheckIn();
			model.addAttribute("onDate", reservation.getCheckIn());
			model.addAttribute("freeRooms", this.roomService.getFreeOn(localDate));
			model.addAttribute("reservedRooms", this.roomService.getReservedOn(localDate));
			return "roomsAdmin";
		}
		return "reservationsForm";
	}
	
	/*
	 * This method is called when a GET request is sent to the URL /room/form.
	 * This method prepares and dispatches the Room Form view
	 * @param model The request model
	 * @return The target view, which in this case is "roomForm"
	 * */
	@RequestMapping(value="/room/form", method=RequestMethod.GET)
	public String getRoomForm(Model model) {
		model.addAttribute("room", new Room());
		return "roomForm";
	}
	
	/*
	 * This method is called when a POST request is sent to the URL /room/form
	 * This method evaluates the recieved room and prepares and dispatches the
	 * Room Created view
	 * @param model The request model
	 * @return The target view, whis is "roomCreated" if the parameter "room" is
	 * valid, "roomForm" otherwhise
	 * */
	@RequestMapping(value="/room/form", method=RequestMethod.POST)
	public String postRoom(@Valid @ModelAttribute("room") Room room,
			BindingResult bindingResult, Model model) {
		this.roomValidator.validate(room, bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("room", this.roomService.save(room));
			return "roomCreated";
		}
		return "roomForm";
	}
}
