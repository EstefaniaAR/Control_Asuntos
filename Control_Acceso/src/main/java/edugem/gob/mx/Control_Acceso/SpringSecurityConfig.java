package edugem.gob.mx.Control_Acceso;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edugem.gob.mx.Control_Acceso.auth.handler.SuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private SuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	DataSource dataSource;
	
	/*@Autowired
	DataSource dataSource;
	*/
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception 
	{
		/*JDBC Authentication*/
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select clave_usuario,password,baja from usuarios where clave_usuario=?")
		.authoritiesByUsernameQuery("select u.clave_usuario,a.clave from permisos a inner join usuarios u on (a.usuario_id = u.id) where u.clave_usuario =?");
		
		
		/* In memory Authentication*/
		/*
		PasswordEncoder encoder = passwordEncoder;
		System.out.println(encoder.encode("admin2019"));
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("admin2019").roles("ADMIN","USER","ASUNTO"))
		.withUser(users.username("user").password("user2019").roles("USER"))
		;
		*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests()
		.antMatchers("/","/home","/css/**","/js/**","/img/**")
		.permitAll()
		//.antMatchers("/form/**").hasAnyRole("ADMIN")
		//.antMatchers("/delete/**").hasAnyRole("ADMIN")
		//.antMatchers("/erase/**").hasAnyRole("ADMIN")
		//.antMatchers("/bill/**").hasAnyRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin().successHandler(successHandler).loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling()
		.accessDeniedPage("/error_403");
		
	}

}