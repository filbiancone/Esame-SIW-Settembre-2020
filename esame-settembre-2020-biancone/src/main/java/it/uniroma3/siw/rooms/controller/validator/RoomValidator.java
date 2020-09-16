package it.uniroma3.siw.rooms.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.rooms.model.Room;

/*
 * Validator for Rooms
 * */
@Component
public class RoomValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Room.class.equals(clazz);
	}

	/*
	 * This method validates admin Room input
	 * @param target The Room data inserted from the admin
	 * */
	@Override
	public void validate(Object target, Errors errors) {
		Room r= (Room) target;
		ValidationUtils.rejectIfEmpty(errors, "lettiSingoli", "required");
		ValidationUtils.rejectIfEmpty(errors, "lettiMatrimoniali", "required");
		if(!errors.hasErrors()) {
			if ((r.getLettiMatrimoniali()==0) && (r.getLettiSingoli()==0))
				errors.reject("noBeds");
		}
	}

}
