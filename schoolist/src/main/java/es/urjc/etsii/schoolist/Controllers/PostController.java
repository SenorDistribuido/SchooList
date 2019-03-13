package es.urjc.etsii.schoolist.Controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.urjc.etsii.schoolist.Entities.Autobus;
import es.urjc.etsii.schoolist.Entities.Parada;
import es.urjc.etsii.schoolist.Entities.Post;
import es.urjc.etsii.schoolist.Entities.Usuario;
import es.urjc.etsii.schoolist.Repositories.PostRepository;

@Controller
public class PostController {
	@Autowired
	private PostRepository postRepo;
	
	@PostMapping(value = "createPost")
	public String createPost(Post newPost) {
		
		postRepo.save(newPost);
		
		return "redirect:" + "/admin";
	}

	@PostMapping(value = "updatePost/{id}")
	public String updatePost(@PathVariable Long id, Post updatedPost) {

		Optional<Post> post = postRepo.findById(id);
		
		if(post.get() != null) {
			updatedPost.setId(id);
			postRepo.save(updatedPost);
		}
		return "redirect:" + "/admin";
	}
	
	@PostMapping(value = "deletePost/{id}")
	public String deleteAutobus(@PathVariable Long id) {

		postRepo.deleteById(id);
		return "redirect:" + "/admin";
	}
	
	/*
	@PostMapping("deletePost")
	public String deletePost(Model model, @RequestParam("id")long id) {

		postRepo.deleteById(id);	
		
		return "redirect:" + "/admin";
	}
	
	
	@PostMapping("editPost")
	public String editPost(Model model, @RequestParam("id")long id) {

		Optional<Post> post = postRepo.findById(id);
		post.ifPresent(postExistente -> {
			model.addAttribute("post", postExistente);
		   });
		
		if(model.containsAttribute("post"))
			return "editarPost_template";
		return "redirect:" + "/admin";
	}*/

	
}
