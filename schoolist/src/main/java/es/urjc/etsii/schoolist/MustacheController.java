package es.urjc.etsii.schoolist;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.etsii.schoolist.Entities.AlumnoRepository;
import es.urjc.etsii.schoolist.Entities.User;
import es.urjc.etsii.schoolist.Entities.UserRepository;

@Controller
public class MustacheController 
{
	
	@Autowired
	private UserRepository repository;
	
	@PostConstruct
	public void init() {
		repository.save(new User("shadow69", "taka", 1));
		repository.save(new User("Juan", "Hola caracola", 0));
	}
	
	@RequestMapping(value={"", "/", "home"})
	 public String base(Model model) {
		model.addAttribute("name", "home");
		//model.addAttribute("name", repository.findById("Juan").toString());
		return "home_template";
	 }
	
	@RequestMapping("/login")
	 public String greeting(Model model) {
		model.addAttribute("name", "login");
		return "login_template";
	 }
	
	@RequestMapping("/admin")
	 public String admin(Model model) {
		model.addAttribute("name", "admin");
		return "admin_template";
	 }
	
	@RequestMapping("/profesor")
	 public String profesor(Model model) {
		model.addAttribute("name", "profesor");
		return "profesor_template";
	 }
	
	@RequestMapping("/padre")
	 public String padre(Model model) {
		model.addAttribute("name", "padre");
		return "padre_template";
	 }
	
	@RequestMapping("/monitor")
	 public String monitor(Model model) {
		model.addAttribute("name", "monitor");
		return "monitor_template";
	 }
	
	@RequestMapping("/mail")
	 public String mail(Model model) {
		model.addAttribute("name", "mail");
		return "mail_template";
	}
	
}
