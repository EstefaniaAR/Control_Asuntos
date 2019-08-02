package edugem.gob.mx.Control_Acceso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edugem.gob.mx.Control_Acceso.domain.Catalogo;
import edugem.gob.mx.Control_Acceso.repository.CatalogoRepository;

@Service
public class CatalogoServiceImpl implements CatalogoService
{
	@Autowired
	private CatalogoRepository cr;

	@Override
	public List<Catalogo> getCatalogo(String clave)
	{
		return cr.findByClave(clave);
	}

	@Override
	public Catalogo getCatalogoPorId(Long id)
	{
		return  cr.findById(id).orElse(null);
	}

	@Override
	public Catalogo insertarCatalogo(Catalogo cat) 
	{
		return cr.save(cat);
	}

}
