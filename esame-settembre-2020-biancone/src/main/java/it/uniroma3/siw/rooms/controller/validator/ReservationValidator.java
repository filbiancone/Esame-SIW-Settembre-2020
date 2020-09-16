package it.uniroma3.siw.rooms.controller.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.rooms.model.Reservation;

/*
 * Validator for Reservations
 * */
@Component
public class ReservationValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return Reservation.class.equals(c);
	}

	/*
	 * This method validates client Reservation input
	 * @param target The Reservation data requested from the client
	 * */
	@Override
	public void validate(Object target, Errors errors) {
		Reservation r= (Reservation) (target);
		ValidationUtils.rejectIfEmpty(errors, "posti", "required");
		ValidationUtils.rejectIfEmpty(errors, "checkIn", "required");
		ValidationUtils.rejectIfEmpty(errors, "checkOut", "required");
		if(!errors.hasErrors()) {
			if(r.getCheckIn().equals(r.getCheckOut()))
				errors.reject("sameDate");
			if (r.getCheckOut().isBefore(r.getCheckIn()))
				errors.reject("beforeDate");
			if(r.getCheckIn().isBefore(LocalDate.now())) 
				errors.reject("yesterdayDate");
		}
	}
	
	/*
	 * This method validates admin Reservation input
	 * @param reservation A Reservation that wraps the date requested from the admin
	 * to facilitate the validation of a LocalDate
	 * */
	public void adminValidate(Reservation reservation, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "checkIn", "required");
	}
}
