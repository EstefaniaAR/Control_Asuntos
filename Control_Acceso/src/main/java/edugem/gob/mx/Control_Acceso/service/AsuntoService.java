package edugem.gob.mx.Control_Acceso.service;

import java.util.List;

import edugem.gob.mx.Control_Acceso.domain.Asunto;
import edugem.gob.mx.Control_Acceso.domain.Respuesta;

public interface AsuntoService
{
	public Asunto saveAsunto(Asunto asunto);
	public List<Asunto>searchAsuntos(Asunto criteria);
	public Asunto getAsunto(Long id);
	public Respuesta getRespuesta(Long id);

}
