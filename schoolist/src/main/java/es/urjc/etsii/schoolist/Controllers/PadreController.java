package es.urjc.etsii.schoolist.Controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.urjc.etsii.schoolist.Entities.Alumno;
import es.urjc.etsii.schoolist.Entities.Asignatura;
import es.urjc.etsii.schoolist.Entities.Autobus;
import es.urjc.etsii.schoolist.Entities.Grupo;
import es.urjc.etsii.schoolist.Entities.Padre;
import es.urjc.etsii.schoolist.Repositories.AsignaturaRepository;
import es.urjc.etsii.schoolist.Repositories.AutobusRepository;
import es.urjc.etsii.schoolist.Repositories.PadreRepository;

@Controller
public class PadreController {
	
	@Autowired
	private PadreRepository padreRepo;
	
	@Autowired
	private AsignaturaRepository asignaturaRepo;
	
	@Autowired
	private AutobusRepository busRepo;
	

	@RequestMapping("/padre")
	 public String padre(Model model) {
		model.addAttribute("name", "padre");
		
		/* A coger del usuario logeado cuando esté implementado */
		Optional<Padre> conejilloIndias = padreRepo.findById("silzavi");
		
		conejilloIndias.ifPresent(conejilloIndiasExistente -> {
			Alumno alumno = conejilloIndiasExistente.getHijo();
			
		    List<Asignatura> asignaturasAlumno = new LinkedList<Asignatura>();
		    Grupo grupo = alumno.getGrupo();
		    
		    asignaturasAlumno = asignaturaRepo.findByGrupo(grupo);
		    
			Autobus bus = busRepo.findByParadas(alumno.getParada());
			
			//List<Falta> faltas = faltaRepo.findByAlumno(alumno);
			
			model.addAttribute("alumno", alumno);
			model.addAttribute("asignaturas", asignaturasAlumno);
			model.addAttribute("grupo", grupo);
			//model.addAttribute("faltas", faltas);
			model.addAttribute("autobus", bus);
			model.addAttribute("padre", conejilloIndiasExistente);
		});
		
		return "padre_template";
	 }
	
}
