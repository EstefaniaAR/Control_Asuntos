package edugem.gob.mx.Control_Acceso.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController 
{
	private Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping({"/","/home"})
	public String getHome()
	{
		return "home";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required =false) String error,@RequestParam(value="logout", required =false) String logout, Model model, Principal principal,RedirectAttributes flash)
	{
		if(principal !=null)
		{
			flash.addFlashAttribute("info","Ya has iniciado sesión como: "+principal.getName());
			return "redirect:/";
		}
		
		if(error!=null)
		{
			log.info("Usuario o Contraseña incorrectos");
			model.addAttribute("error","Usuario o Contraseña incorrectos");
		}
		if(logout!=null)
		{
			model.addAttribute("success","Has entrado exitósamente");
		}
		return "login";
	}

}
