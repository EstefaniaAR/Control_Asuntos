package edugem.gob.mx.Control_Acceso.auth.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException 
	{
		SessionFlashMapManager flashManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		flashMap.put("success","Bienvenido "+authentication.getName());
		flashManager.saveOutputFlashMap(flashMap, request, response);
		if(authentication!=null)
		{
			logger.info("success: usuario logeado: '"+authentication.getName()+"'" + new Date().toString());
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	

}
