package es.urjc.etsii.schoolist;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import es.urjc.etsii.schoolist.Repositories.UserRepository;
import es.urjc.etsii.schoolist.Entities.Alumno;
import es.urjc.etsii.schoolist.Entities.Usuario;
import es.urjc.etsii.schoolist.Repositories.UserRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	 @Autowired
	 private UserRepository userRepository;
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http)throws Exception{
		 List<Usuario> usuarios = new LinkedList<Usuario>();
		 usuarios= userRepository.findAll();
		 for(int i=0;i<usuarios.size();i++) {
			 String cCifrada=new BCryptPasswordEncoder().encode("1234");
				//	 usuarios.get(i).setPassWord(new BCryptPasswordEncoder().encode
			// System.out.println("Contraseña de " + usuarios.get(i).getNombre()+ " es ahora "
		    //+ cCifrada);
			 
			 usuarios.get(i).setPassWord(cCifrada);
			 userRepository.save( usuarios.get(i));
			 }
		//publico
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/home").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/mlogout").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/loginerror").permitAll();
		http.authorizeRequests().antMatchers("/css/**").permitAll();
		http.authorizeRequests().antMatchers("/img/**").permitAll();
		http.authorizeRequests().antMatchers("/js/**").permitAll();
		http.authorizeRequests().antMatchers("/less/**").permitAll();
		http.authorizeRequests().antMatchers("/mail/**").permitAll();
		http.authorizeRequests().antMatchers("/vendor/**").permitAll();
		
		//privado
		http.authorizeRequests().anyRequest().authenticated();
		
		http.authorizeRequests().antMatchers("/admin").hasAnyRole("Admin");
		http.authorizeRequests().antMatchers("/monitor").hasAnyRole("MONITOR");
		http.authorizeRequests().antMatchers("/padre").hasAnyRole("Padre");
		http.authorizeRequests().antMatchers("/profesor").hasAnyRole("Profesor");
				
		//login
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("uname");
		http.formLogin().passwordParameter("pass");
		//http.formLogin().defaultSuccessUrl("/admin");
		http.formLogin().failureUrl("/loginerror");
		
		//logout
		http.logout().logoutUrl("/logout");
     	http.logout().logoutSuccessUrl("/mlogout");
		
		//cosica a cambiar
		//http.csrf().disable();

		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("uname");
		http.formLogin().passwordParameter("pass");
		//http.formLogin().defaultSuccessUrl("/admin");
		http.formLogin().failureUrl("/loginerror");
		
		//logout
		http.logout().logoutUrl("/logout");
     	http.logout().logoutSuccessUrl("/mlogout");

	}
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//usuarios por defecto
		//auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("USER");
		//auth.inMemoryAuthentication().withUser("admin").password("adminpass").roles("ADMIN");
		
	}*/
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth)
	 throws Exception {
	 // Database authentication provider
		 auth.authenticationProvider(authenticationProvider);
	 }

}
