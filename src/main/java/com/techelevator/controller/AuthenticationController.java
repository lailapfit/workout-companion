package com.techelevator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techelevator.model.MemberDAO;
import com.techelevator.model.User;

@Controller
@SessionAttributes({"synergyUser"})
public class AuthenticationController {

	private MemberDAO userDAO;

	@Autowired
	public AuthenticationController(MemberDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@RequestMapping("/")
	public String displayLoginAtRoot() {

		return "login";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.GET)
	public String displayLoginForm() {
		return "login";
	}
	
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public String login(@RequestParam String userName, 
						@RequestParam String password, 
						@RequestParam(required=false) String destination,
						HttpSession session, ModelMap map) {
		if(userDAO.searchForUsernameAndPassword(userName, password)) {
			session.setAttribute("currentUser", userDAO.getMemberByUserName(userName));
			
			if(destination != null && ! destination.isEmpty()) {
				return "redirect:" + destination;
			} else {
				//return "redirect:/UserDashboard"; 
				User user = new User();
				user.setUserName(userName);
				map.addAttribute("synergyUser", user);
				return "redirect:/users/" + userName;
			}
		} else {
			return "redirect:/login";
		}
	}

	@RequestMapping(path="/logout", method=RequestMethod.GET)
	public String logout(ModelMap model, HttpSession session) {
		model.remove("synergyUser");
		session.invalidate();
		return "redirect:/";
	}
	
}
