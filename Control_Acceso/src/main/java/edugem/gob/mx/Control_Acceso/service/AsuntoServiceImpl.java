package edugem.gob.mx.Control_Acceso.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edugem.gob.mx.Control_Acceso.domain.Asunto;
import edugem.gob.mx.Control_Acceso.domain.Respuesta;
import edugem.gob.mx.Control_Acceso.repository.AsuntoRepository;
import edugem.gob.mx.Control_Acceso.repository.RespuestaRepository;

@Service
public class AsuntoServiceImpl implements AsuntoService
{
	@Autowired
	AsuntoRepository ar;
	
	@Autowired
	RespuestaRepository rr;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Asunto saveAsunto(Asunto asunto) 
	{

		Asunto asun = ar.save(asunto);
		return asun;
	}

	@Override
	public List<Asunto> searchAsuntos(Asunto criteria) 
	{
		CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Asunto> cq = cb.createQuery(Asunto.class);
	 
	    Root<Asunto> book = cq.from(Asunto.class);
	    List<Predicate> predicates = new ArrayList<>();
	    
	     
	    if (criteria.getId() != null && criteria.getId()> 0) {
	        predicates.add(cb.equal(book.get("id"), criteria.getId()));
	    }
	    if (criteria.getReferencia() != null && !criteria.getReferencia().equals("")) {
	        predicates.add(cb.equal(book.get("referencia"), criteria.getReferencia()));
	    }
	    if (criteria.getDgipo() != null && !criteria.getDgipo().equals("")) {
	        predicates.add(cb.equal(book.get("dgipo"), criteria.getDgipo()));
	    }
	    cq.where(predicates.toArray(new Predicate[predicates.size()]));
	    
	 
	    return em.createQuery(cq).getResultList();
	}

	@Override
	public Asunto getAsunto(Long id) {
		
		return ar.findById(id).orElse(null);
	}

	@Override
	public Respuesta getRespuesta(Long id) {
		return rr.findById(id).orElse(null);
	}

}
