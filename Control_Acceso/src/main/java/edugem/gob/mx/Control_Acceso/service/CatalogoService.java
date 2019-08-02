package edugem.gob.mx.Control_Acceso.service;

import java.util.List;

import edugem.gob.mx.Control_Acceso.domain.Catalogo;

public interface CatalogoService 
{
	public List<Catalogo>getCatalogo(String clave);
	public Catalogo getCatalogoPorId(Long id);
	public Catalogo insertarCatalogo(Catalogo cat);
}
