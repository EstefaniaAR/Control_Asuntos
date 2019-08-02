package edugem.gob.mx.Control_Acceso.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edugem.gob.mx.Control_Acceso.domain.Asunto;
import edugem.gob.mx.Control_Acceso.domain.Catalogo;
import edugem.gob.mx.Control_Acceso.domain.Respuesta;
import edugem.gob.mx.Control_Acceso.service.AsuntoService;
import edugem.gob.mx.Control_Acceso.service.CatalogoService;

@Controller
@RequestMapping(value="/asunto")
@SessionAttributes("asunto")
@Secured("ROLE_ASUNTO")
public class AsuntoController 
{
	@Autowired
	CatalogoService cs;
	
	@Autowired
	AsuntoService as;
	
	@GetMapping("/form")
	public String getForm(Model model)
	{
		List<Catalogo>niveles = cs.getCatalogo("nivel"); 
		List<Catalogo>remitentes = cs.getCatalogo("remitente"); 
		List<Catalogo>cargos = cs.getCatalogo("cargo"); 
		List<Catalogo>atenciones = cs.getCatalogo("tipo_atencion"); 
		
		Asunto asunto = new Asunto();
		asunto.setFechaRegistro(new Date());
		
		model.addAttribute("asunto",asunto);
		model.addAttribute("title","REGISTRA UN ASUNTO");
		model.addAttribute("niveles",niveles);
		model.addAttribute("remitentes",remitentes);
		model.addAttribute("cargos",cargos);
		model.addAttribute("atenciones",atenciones);
		
		return "asuntos/form";
	}
	
	@PostMapping(value="/form",params ="insert_asunto")
	public String insertAsunto(@Valid Asunto asunto, RedirectAttributes flash ,SessionStatus status, BindingResult result,Model model,@RequestParam("file") MultipartFile file)
	{
		if(!result.hasErrors() 
				&& !file.isEmpty()
				&& (asunto.getReferencia()!=null && !asunto.getReferencia().equals(""))
				&& asunto.getAtencion()!=null
				&& asunto.getCargo()!=null
				&& (asunto.getDgipo()!=null && !asunto.getDgipo().equals(""))
				&& asunto.getFechaOficio()!=null 
				&& asunto.getNivel()!=null
				&& asunto.getRemitente()!=null
				&& (asunto.getResumen()!=null && !asunto.getResumen().equals(""))
				&& asunto.getSubsistema()!=null)
		{
			try 
			{
				asunto.setAnexo(file.getBytes());
			} 
			catch (IOException e) 
			{
				flash.addFlashAttribute("error",e.getMessage());
				e.printStackTrace();
			}
			
			Asunto sv= as.saveAsunto(asunto);
			if(sv !=null)
			{
				flash.addFlashAttribute("success","Asunto registrado con éxito: Folio: "+sv.getId()+" Referencia: "+sv.getReferencia()+" Fecha de registro: "+sv.getFechaRegistro());
				status.setComplete();
			}
		}
		else
		{
			String error="";
			if(result.hasErrors())
				error +="\n Hubo un error al cargar un asunto:"+result.getAllErrors()+".";
			if(file.isEmpty() )
				error += "\n El archivo PDF es requerido.";
			if(!file.getContentType().equals("application/pdf"))
				error +="\n El archivo debe ser en formato PDF.";
			if(asunto.getReferencia()==null && asunto.getReferencia().equals(""))
				error += "\n La referencia es requerida.";
			if(asunto.getCargo()==null )
				error+="\n El cargo es requerido.";
			if(asunto.getDgipo()==null && asunto.getDgipo().equals(""))
				error += "\n DGIPO es requerido.";
			if(asunto.getFechaOficio()==null)
				error+="\n Fecha de Oficio es requerida.";
			if(asunto.getNivel()==null)
				error+="\n Nivel es requerido.";
			if(asunto.getRemitente()==null)
				error+="\n Remitente es requerido.";
			if(asunto.getResumen()==null && asunto.getResumen().equals(""))
				error+="\n El asunto es requerido.";
			if(asunto.getSubsistema()==null)
				error+="\n El Subsistema es requerido.";
			if(asunto.getTipoAsunto()==null)
				error+= "\n El tipo de atención es requerido.";
			model.addAttribute("error", error);
			
			List<Catalogo>niveles = cs.getCatalogo("nivel"); 
			List<Catalogo>remitentes = cs.getCatalogo("remitente"); 
			List<Catalogo>cargos = cs.getCatalogo("cargo"); 
			List<Catalogo>atenciones = cs.getCatalogo("tipo_atencion"); 
			
			asunto.setNivel(null);
			model.addAttribute("asunto",asunto);
			model.addAttribute("title","REGISTRA UN ASUNTO");
			model.addAttribute("niveles",niveles);
			model.addAttribute("remitentes",remitentes);
			model.addAttribute("cargos",cargos);
			model.addAttribute("atenciones",atenciones);
			
			return "asuntos/form";
		}
		
		return "redirect:form";
	}
	
	@PostMapping(value="/form",params="insert_tipo_asunto")
	public String insertTipoAsunto(@Valid Asunto asunto
			, RedirectAttributes flash 
			,Model model
			,@RequestParam("file") MultipartFile file
			,@RequestParam("descripcion")String descripcion
			,@RequestParam("icon")String icon)
	{
		
		if(descripcion!=null && !descripcion.equals(""))
		{
			Catalogo cat = new Catalogo();
			cat.setClave("tipo_atencion");
			cat.setDescripcion(descripcion.trim().toUpperCase());
			cat.setIcon(icon.trim());
			cat = cs.insertarCatalogo(cat);
			model.addAttribute("success", "Tipo de atencion insertada con éxito: ID: "+cat.getClave()+" Desc: "+cat.getDescripcion()+" ICON: "+cat.getIcon());
		}
		else
			model.addAttribute("error", "Descripción requerida: ");
		
		List<Catalogo>niveles = cs.getCatalogo("nivel"); 
		List<Catalogo>remitentes = cs.getCatalogo("remitente"); 
		List<Catalogo>cargos = cs.getCatalogo("cargo"); 
		List<Catalogo>atenciones = cs.getCatalogo("tipo_atencion"); 
		
		asunto.setNivel(null);
		
		model.addAttribute("asunto",asunto);
		model.addAttribute("title","REGISTRA UN ASUNTO");
		model.addAttribute("niveles",niveles);
		model.addAttribute("remitentes",remitentes);
		model.addAttribute("cargos",cargos);
		model.addAttribute("atenciones",atenciones);
		
		
		return "asuntos/form";
	}
	
	@PostMapping(value="/form",params="insert_remitente")
	public String insertRemitente(@Valid Asunto asunto
			, RedirectAttributes flash 
			,Model model
			,@RequestParam("file") MultipartFile file
			,@RequestParam("descripcion_rem")String descripcion
			,@RequestParam("icon_rem")String icon)
	{
		
		if(descripcion!=null && !descripcion.equals(""))
		{
			Catalogo cat = new Catalogo();
			cat.setClave("remitente");
			cat.setDescripcion(descripcion.trim().toUpperCase());
			cat.setIcon(icon.trim());
			cat = cs.insertarCatalogo(cat);
			model.addAttribute("success", "Remitente insertado con éxito: ID: "+cat.getClave()+" Desc: "+cat.getDescripcion()+" ICON: "+cat.getIcon());
		}
		else
		{
			model.addAttribute("error", "Descripción requerida");
		}		
		
		List<Catalogo>niveles = cs.getCatalogo("nivel"); 
		List<Catalogo>remitentes = cs.getCatalogo("remitente"); 
		List<Catalogo>cargos = cs.getCatalogo("cargo"); 
		List<Catalogo>atenciones = cs.getCatalogo("tipo_atencion"); 
		
		asunto.setNivel(null);
		
		model.addAttribute("asunto",asunto);
		model.addAttribute("title","REGISTRA UN ASUNTO");
		model.addAttribute("niveles",niveles);
		model.addAttribute("remitentes",remitentes);
		model.addAttribute("cargos",cargos);
		model.addAttribute("atenciones",atenciones);
		
		
		
		return "asuntos/form";
	}
	
	@PostMapping(value="/form",params="insert_cargo")
	public String insertCargo(@Valid Asunto asunto
			, RedirectAttributes flash 
			,Model model
			,@RequestParam("file") MultipartFile file
			,@RequestParam("descripcion_cargo")String descripcion
			,@RequestParam("icon_cargo")String icon)
	{
		
		if(descripcion!=null && !descripcion.equals(""))
		{
			Catalogo cat = new Catalogo();
			cat.setClave("cargo");
			cat.setDescripcion(descripcion.trim().toUpperCase());
			cat.setIcon(icon.trim());
			cat = cs.insertarCatalogo(cat);
			model.addAttribute("success", "Cargo insertado con éxito: ID: "+cat.getClave()+" Desc: "+cat.getDescripcion()+" ICON: "+cat.getIcon());
		}
		else
			model.addAttribute("error", "Descripción requerida ");
		
		List<Catalogo>niveles = cs.getCatalogo("nivel"); 
		List<Catalogo>remitentes = cs.getCatalogo("remitente"); 
		List<Catalogo>cargos = cs.getCatalogo("cargo"); 
		List<Catalogo>atenciones = cs.getCatalogo("tipo_atencion");
		
		asunto.setNivel(null);
		
		model.addAttribute("asunto",asunto);
		model.addAttribute("title","REGISTRA UN ASUNTO");
		model.addAttribute("niveles",niveles);
		model.addAttribute("remitentes",remitentes);
		model.addAttribute("cargos",cargos);
		model.addAttribute("atenciones",atenciones);
		
		
		
		return "asuntos/form";
	}
	
	@GetMapping(value="/subsistemas/{nivel}",  produces = {"application/json"})
	public @ResponseBody List<Catalogo>getSubsistemas(@PathVariable(value = "nivel", required = true) Long nivel)
	{
		String clave="";
		Catalogo niv = cs.getCatalogoPorId(nivel);
		if(niv.getDescripcion().equals("BASICA"))
			clave="subsistema_ba";
		else
			clave="subsistema_ms";
		List<Catalogo> subsistemas = cs.getCatalogo(clave);
		return subsistemas;
	}
	
	@GetMapping("/search")
	public String getSearch(Model model)
	{
		model.addAttribute("title","BUSCA UN ASUNTO");
		model.addAttribute("search",new Asunto());
		return "asuntos/search";
	}
	
	@GetMapping("/seguimiento")
	public String getSeguimiento(Model model)
	{
		model.addAttribute("title","ADMINISTRA UN ASUNTO");
		model.addAttribute("search",new Asunto());
		return "asuntos/seguimiento";
	}
	
	@GetMapping("/find")
	public String search(@ModelAttribute("search") Asunto asunto, Model model)
	{		
		List <Asunto>asuntos = as.searchAsuntos(asunto);
		if(asuntos.isEmpty())
			model.addAttribute("warning","No se ha encontrado asuntos con los criterios de búsqueda");
		model.addAttribute("title","BUSCA UN ASUNTO");
		model.addAttribute("asuntos",asuntos);
		
		return "asuntos/search";
	}
	
	@GetMapping("/get/{id}")
	public String getAsunto(@PathVariable("id") Long id, Model model)
	{		
		Asunto asunto = as.getAsunto(id);
		List<Catalogo>estatus = cs.getCatalogo("estatus"); 
		List<Catalogo>listBnAtencion= cs.getCatalogo("atencion");
		model.addAttribute("result",asunto);
		model.addAttribute("title","ADMINISTRA UN ASUNTO");
		model.addAttribute("listBnAtencion",listBnAtencion);
		model.addAttribute("listEstatus",estatus);
		model.addAttribute("line",new Respuesta());
		
		return "asuntos/seguimiento";
	}
	
	@GetMapping("/render/{id}")
	public @ResponseBody String renderImage(@PathVariable ("id")Long id,Model model)
	{
		Asunto asunto = as.getAsunto(id);
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
		
		try
		{
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=\""+asunto.getReferencia()+"\";");
			response.getOutputStream().write(asunto.getAnexo());
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			model.addAttribute("error","No se pudo descargar el archivo: "+e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("error","No se pudo descargar el archivo: "+e.getMessage());
		}
				
		return null;
	}

	@PostMapping ("/seguimiento/form")
	public String insertSeguimento(@ModelAttribute(name="result") Asunto asunto
			,Model model
			,RedirectAttributes flash 
			,@RequestParam("files[]") MultipartFile[] files
			,@RequestParam("refs[]") String[] referencias
			,@RequestParam("fecs[]") String[] fechas
			,@RequestParam("ats[]") Long[] atenciones
			,@RequestParam("segs[]") String[] seguimientos
			)
	{

		Asunto aux = as.getAsunto(asunto.getId());
		//System.out.println(aux);
		if(aux!=null)
		{
			String error="";
			if(asunto.getInstruccion()!=null && !asunto.getInstruccion().equals("") 
					&& asunto.getEstatus()!=null 
					&& asunto.getTipoAsunto()!=null && !asunto.getTipoAsunto().equals("")
					&& (referencias.length>0 ||(asunto.getRespuestas()!=null && asunto.getRespuestas().isEmpty())))
			{
				aux.setInstruccion(asunto.getInstruccion());
				aux.setEstatus(asunto.getEstatus());
				aux.setTipoAsunto(asunto.getTipoAsunto());
				
				for(int x=0; x< referencias.length; x++)
				{
					Respuesta resp = new Respuesta ();
					try 
					{
						resp.setAsunto(aux);
						if(!files[x].isEmpty() && referencias[x]!=null && !referencias[x].equals("") 
								&& fechas[x]!=null && !fechas[x].equals("") 
								&& atenciones[x]>0
								&& seguimientos[x]!=null && !seguimientos[x].equals(""))
						{
							resp.setArchivo(files[x].getBytes());
							resp.setReferencia(referencias[x]);
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							resp.setFechaRespuesta(format.parse(fechas[x]));
							resp.setAtendida(cs.getCatalogoPorId(atenciones[x]));
							resp.setSeguimiento(seguimientos[x]);
							aux.getRespuestas().add(resp);
						}
						else
						{
							error +="El seguimiento "+x+" no puede darse de alta. Causas: ";
							if(files[x].isEmpty())
								error+="\n Archivo requerido";
							if(referencias[x]==null && referencias[x].equals(""))
								error+="\n Referencia requerida";
							if(fechas[x]==null && fechas[x].equals(""))
								error+="\n Fecha requerida";
							if(atenciones[x]==null && atenciones[x]<=0)
								error+="\n Atencion requerida";
							if(seguimientos[x]==null && seguimientos[x].equals(""))
								error+="\n Seguimiento requerido";
						}
					} catch (IOException e) {
						error+=e.getMessage();
						e.printStackTrace();
					} catch (ParseException e) {
						error+=e.getMessage();
						e.printStackTrace();
					}
					if(!error.isEmpty())
						model.addAttribute("error",error);
				}
			}
			else
			{
				
				if(asunto.getInstruccion()==null || asunto.getInstruccion().equals("") )
					error+="\n La instrucción es requerida.";
				if(asunto.getEstatus()==null)
					error+="\n Estatus es Requerido.";
				if(asunto.getTipoAsunto()==null || asunto.getTipoAsunto().equals(""))
					error+= "\n Tipo de Asunto requerido.";
				if(referencias.length<=0 && (asunto.getRespuestas()==null || asunto.getRespuestas().isEmpty()))
					error+="Debe haber al menos un registro de seguimiento válido";
				if(!error.isEmpty())
					model.addAttribute("error",error);
			}
			
			if(error.isEmpty())
			{
				asunto = as.saveAsunto(aux);
				model.addAttribute("success","El seguimiento ha sido guardado");
			}
			
			List<Catalogo>estatus = cs.getCatalogo("estatus"); 
			List<Catalogo>listBnAtencion= cs.getCatalogo("atencion");
			
			asunto = as.getAsunto(asunto.getId());
			model.addAttribute("result",asunto);
			model.addAttribute("title","ADMINISTRA UN ASUNTO");
			model.addAttribute("listBnAtencion",listBnAtencion);
			model.addAttribute("listEstatus",estatus);
		}
		return "asuntos/seguimiento";
	}
	
	@GetMapping("/seguimiento/render/{id}")
	public @ResponseBody String renderPDF(@PathVariable ("id")Long id,Model model)
	{
		Respuesta resp = as.getRespuesta(id);
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
		
		try
		{
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename=\""+resp.getReferencia()+"\";");
			response.getOutputStream().write(resp.getArchivo());
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			model.addAttribute("error","No se pudo descargar el archivo: "+e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			model.addAttribute("error","No se pudo descargar el archivo: "+e.getMessage());
		}
				
		return null;
	}

}
