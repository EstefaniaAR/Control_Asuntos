package edugem.gob.mx.Control_Acceso.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edugem.gob.mx.Control_Acceso.domain.Asunto;

@Repository
public interface AsuntoRepository extends CrudRepository<Asunto,Long>,JpaSpecificationExecutor<Asunto>
{

}
