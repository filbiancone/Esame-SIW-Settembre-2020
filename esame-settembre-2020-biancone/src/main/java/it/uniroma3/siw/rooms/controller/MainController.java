package it.uniroma3.siw.rooms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * Generic controller that handles interactions with the home page
 * */
@Controller
public class MainController {

	public MainController() {
		
	}
	
	/*
	 * This method prepares the view /index when it is requested
	 * @param model The request model
	 * @return The view /index
	 * */
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }
}
