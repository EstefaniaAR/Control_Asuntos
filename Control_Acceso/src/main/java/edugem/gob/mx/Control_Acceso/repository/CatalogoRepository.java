package edugem.gob.mx.Control_Acceso.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edugem.gob.mx.Control_Acceso.domain.Catalogo;

@Repository
public interface CatalogoRepository extends CrudRepository<Catalogo,Long>{
	public List<Catalogo>findByClave(String clave);

}
