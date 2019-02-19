package es.urjc.etsii.schoolist;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.etsii.schoolist.Entities.*;

@Controller
public class DBController 
{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private AlumnoRepository alumnoRepo;
	
	@Autowired
	private AsignaturaRepository asignaturaRepo;
	
	@Autowired
	private AutobusRepository busRepo;
	
	@Autowired
	private CursoRepository cursoRepo;
	
	@Autowired
	private FaltaRepository faltaRepo;
	
	@Autowired
	private GrupoRepository grupoRepo;
	
	@Autowired
	private MensajeRepository mensajeRepo;
	
	@Autowired
	private PadreRepository padreRepo;
	
	@Autowired
	private ParadaRepository paradaRepo;
	
	@Autowired
	private ProfesorRepository profesorRepo;
	
	@Autowired
	private MonitorRepository monitorRepo;
	
	
	@PostConstruct
	public void init() {
		//repository.save(new User("shadow69", "taka", 1));
		//repository.save(new User("Juan", "Hola caracola", 0));
	}
	
	@PostMapping("createUser")
	 public String createUser(Model model, User newUser, @RequestParam String userType) {
		
		userRepo.save(newUser);
		
		switch(userType) {
		case "profesor":
			Profesor profe = new Profesor(newUser);
			profesorRepo.save(profe);
			break;
		case "monitor":
			Monitor moni = new Monitor(newUser);
			monitorRepo.save(moni);
			break;
		case "padre":
			Padre papi = new Padre(newUser);
			padreRepo.save(papi);
			break;
		case "admin":
			Admin admin = new Admin(newUser);
			adminRepo.save(admin);
			break;
		}
		
		
		return "redirect:" + "/admin";
	 }
	
	@PostMapping("createAlumno")
	public String createAlumno(Model model, Alumno newAlumno) {
		
		alumnoRepo.save(newAlumno);
		
		return "redirect:" + "/admin";
	 }
	
	@PostMapping("sendMessage")
	public String createMessage(Model model, Mensaje mensaje, @RequestParam("receptor") String destino_id) {
		//User origen, User destino, String cabecera, String texto
		//mensaje.setOrigin_id(%id_usuario%);
		/*Optional<User> user = userRepo.findById(destino_id);
		
		mensaje.setDestino(user.get());
		*/
		mensajeRepo.save(mensaje);
		
		return "home";
	}
	
	@PostMapping("getMailBox")
	public String getMessages(Model model, Mensaje mensaje) {
		
		//model.addAttribute("name", "padre");
		List<Mensaje> mensajesList = mensajeRepo.findAll();
		
		List<String> asuntos = new LinkedList<String>();
		List<String> textos = new LinkedList<String>();
		
		for(int i=0; i<mensajesList.size(); i++) {
			asuntos.add(mensajesList.get(i).getCabecera());
			textos.add(mensajesList.get(i).getTexto());
		}
		
		model.addAttribute("mensajes", asuntos);
		model.addAttribute("textos", textos);
		return "mailBox_template";
	}
	/*
	@PostMapping("getAlumnosClase")
	public String getAlumnosClase(Model model) {
		
		//model.addAttribute("name", "padre");
		List<Alumno> alumnosList = alumnoRepo.findAll();
		
		List<String> nombres = new LinkedList<String>();

		
		for(int i=0; i<alumnosList.size(); i++) {
			nombres.add(alumnosList.get(i).getNombreCompleto());
	
		}
		
		model.addAttribute("alumnos", nombres);

		return "mailBox_template";
	}*/
}
