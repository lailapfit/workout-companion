package com.techelevator.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.model.AdministratorDAO;
import com.techelevator.model.Classes;
import com.techelevator.model.ClassesDAO;
import com.techelevator.model.MemberDAO;
import com.techelevator.model.User;
import com.techelevator.model.equipment.Equipment;
import com.techelevator.model.equipment.EquipmentDAO;
import com.techelevator.model.session.Session;
import com.techelevator.model.session.SessionDAO;

@Controller
@SessionAttributes({"synergyUser", "workoutTime", "workoutDate"})
public class UserController {

	private MemberDAO userDAO;
	private AdministratorDAO adminDAO;
	private ClassesDAO classesDAO;
	private EquipmentDAO equipmentDAO;
	private SessionDAO sessionDAO;

	@Autowired
	public UserController(MemberDAO userDAO, AdministratorDAO adminDAO, ClassesDAO classesDAO,
			EquipmentDAO equipmentDAO, SessionDAO sessionDAO) {
		this.userDAO = userDAO;
		this.adminDAO = adminDAO;
		this.classesDAO = classesDAO;
		this.equipmentDAO = equipmentDAO;
		this.sessionDAO = sessionDAO;
	}

	@RequestMapping(path = "/users/new", method = RequestMethod.GET)
	public String displayNewUserForm(ModelMap modelHolder) {
		if (!modelHolder.containsAttribute("user")) {
			modelHolder.addAttribute("user", new User());
		}
		return "newUser";
	}

	@RequestMapping(path = "/users", method = RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash) {
		if (result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/users/new";
		}

		userDAO.saveMember(user.getUserName(), user.getPassword(), user.getName(), user.getEmail(),
				user.getWorkoutGoals(), user.getWorkoutProfile(), user.getAvatar());
		return "redirect:/login";
	}
	
	//catching when user attempts to go to their dashboard prior to login in
	@RequestMapping(path="/users", method=RequestMethod.GET)
	public String invalidUserdashboard() {
		return "redirect:/login";	
	}

	@RequestMapping(path = "/users/{username}", method = RequestMethod.GET)
	public String userDashboard(@PathVariable String username, ModelMap map) {
		Object user = userDAO.getMemberByUserName(username);
		List<Session> sessionsData = sessionDAO.getMemberSessionData(username);
		List<Session> allSessionsWithEquipment = sessionDAO.getAllSessionsPerMemberWithEquipment(username);
		List<Session> allSessions = sessionDAO.getAllSessionsPerMember(username);
		List<Classes> classesScheduled = classesDAO.getClassesScheduled(username);

		map.addAttribute("user", user);
		map.addAttribute("synergyUser", user);
		map.addAttribute("gymSession", sessionsData);
		map.addAttribute("equipments", allSessionsWithEquipment);
		map.addAttribute("allSessions", allSessions);
		map.addAttribute("classesScheduled", classesScheduled);

		return "userDashboard";

	}

	@RequestMapping(path = "/editProfile", method = RequestMethod.GET)
	public String displayEditProfile(ModelMap map) {
		User user = (User) map.get("synergyUser");

		map.addAttribute("synergyUser", user);
		return "/editProfile";
	}

	@RequestMapping(path = "/updateProfile", method = RequestMethod.POST)
	public String userDashBoardUpdatedProfile(@RequestParam String workoutGoals, ModelMap map) {
		User user = (User) map.get("synergyUser");
		String userName = user.getUserName();

		userDAO.updateWorkoutGoals(workoutGoals, userName);

		map.addAttribute("user", user);
		map.addAttribute("synergyUser", user);

		return "redirect:/users/" + userName;
	}

	@RequestMapping(path = "/calendar", method = RequestMethod.GET)
	public String calendar(ModelMap map) {
		User user = (User) map.get("synergyUser");
		String userName = user.getUserName();
		map.addAttribute("synergyUser", user);
//		Map<Date,Classes> newMap = new HashMap<Date, Classes>();
		List<Classes> workoutClass = classesDAO.getClassesByClassName();
////		Integer size = workoutClass.size();
//		for(Classes c : workoutClass ) {
//			newMap.put(c.getClassDate(), c);
//		}
//		
//		map.addAttribute("calendar", newMap);
		
		map.addAttribute("calendar", workoutClass);
		
		return "calendar";
	}
	
//	Map<Date, Classes> map = new HashMap<Date, Classes>();
//	List<Classes> allClasses = dao.getClassesByClassName();
//	Integer size = allClasses.size();
//	for(Classes  allClasses) {
//	map.put(c.getClassDate(), c);
//	}
//	for(Classes c : allClasses)

	@RequestMapping(path = "/calendarUpdate", method = RequestMethod.GET)
	public String addToClassSchdule(HttpServletRequest request, ModelMap map) {
		User user = (User) map.get("synergyUser");
		String userName = user.getUserName();
		int classId = Integer.parseInt(request.getParameter("classId"));
		classesDAO.updateClassSchedule(classId, userName);
		map.addAttribute("synergyUser", user);

		return "redirect:/users/" + userName;

	}

	@RequestMapping(path = "/addUser", method = RequestMethod.GET)
	public String displayAddUser(ModelMap map) {
		User user = (User) map.get("synergyUser");
		String userName = user.getUserName();

		map.addAttribute("synergyUser", user);

		return "/addUser";
	}

	@RequestMapping(path = "/addNewUser", method = RequestMethod.POST)
	public String addNewUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes flash,
			ModelMap map) {
		if (result.hasErrors()) {
			flash.addFlashAttribute("user", user);
			flash.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "user", result);
			return "redirect:/addUser";
		}

		adminDAO.addEmployee(user.getUserName(), user.getPassword(), user.getName(), user.getEmail(),
				user.getWorkoutGoals(), user.getWorkoutProfile(), user.getAvatar(), user.getRoleId());

		User admin = (User) map.get("synergyUser");
		String adminName = admin.getUserName();

		return "redirect:/users/" + adminName;
	}

	@RequestMapping(path = "/gymSessionLog", method = RequestMethod.GET)
	public String displayGymSessionLog(ModelMap map) {
		Time start = sessionDAO.getCurrentTime();
		User user = (User) map.get("synergyUser");
		List<Equipment> gymEquipment = equipmentDAO.getAllEquipments();
		map.addAttribute("equipment", gymEquipment);
		map.addAttribute("synergyUser", user);
		map.addAttribute("workoutTime", start);
		
		return "/gymSessionLog";
	}

	@RequestMapping(path = "/gymSessionLog", method = RequestMethod.POST)
	public String addSymSessionLog(@RequestParam Integer sets, @RequestParam Integer reps, @RequestParam Integer weight,
			@RequestParam Integer equipmentId, ModelMap map) {
		User user = (User) map.get("synergyUser");
		String username = user.getUserName();
		Date date = sessionDAO.getCurrentDate();
		sessionDAO.saveSession(username, equipmentId, reps, sets, weight, date);
		map.addAttribute("synergyUser", user);
		map.addAttribute("workoutDate", date);

		return "redirect:/gymSessionLog";
	}
	
	@RequestMapping(path="/gymSessionEnd", method=RequestMethod.GET) 
	public String endSessionLog(ModelMap map) {
		User user = (User) map.get("synergyUser");
		String username = user.getUserName();
		Date date = (Date) map.get("workoutDate");
		Time start = (Time) map.get("workoutTime");
		Time end = sessionDAO.getCurrentTime();
		sessionDAO.updateTime(username, date, start, end);
		return "redirect:/users/" + username;
	}
}
