package co.com.udem.inmobiliaria.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import co.com.udem.inmobiliaria.dto.FiltroDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;

public class FiltroPropiedades {
	
	@PersistenceContext
	EntityManager em;
	private Object App_;
	
public List<Propiedad> filtrarPropiedades(FiltroDTO objectFilter) throws ParseException { //ObjectFilter u
		
		List<Propiedad> result = null;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Propiedad> cr = cb.createQuery(Propiedad.class);
		Root<Propiedad> root = cr.from(Propiedad.class);
		root.fetch("registro", JoinType.INNER);


		List<Predicate> predicates = new ArrayList<>();
		double area = objectFilter.getArea();
		int nrohabitaciones = objectFilter.getNumeroHabitaciones();
		//double precioInicial = objectFilter.getPrecioInicial();
		//double precioFinal = objectFilter.getPrecioFinal();
		
	
		if(area != 0)
		{
			predicates.add(cb.equal(root.get("area"), area));
		}
		
		if(nrohabitaciones != 0)
		{
			predicates.add(cb.equal(root.get("numerohabitaciones"), nrohabitaciones));
		}
		
//	
//		if(fechaInicialStr != null && fechaFinalStr != null)
//		{
//			predicates.add(cb.greaterThanOrEqualTo(root.<String>get("fechaAcuerdo"), fechaInicialStr+" 00:00:00"));
//			predicates.add(cb.lessThanOrEqualTo(root.<String>get("fechaAcuerdo"), fechaFinalStr+" 23:59:59"));
//		}
//		
//		if(fechaInicialStr == null && fechaFinalStr != null)
//		{
//			predicates.add(cb.lessThanOrEqualTo(root.<String>get("fechaAcuerdo"), fechaFinalStr+" 23:59:59"));
//		}
//		
//		if(fechaInicialStr != null && fechaFinalStr == null)
//		{
//			predicates.add(cb.greaterThanOrEqualTo(root.<String>get("fechaAcuerdo"), fechaInicialStr+" 00:00:00"));
//		}
		
		if (predicates.isEmpty()) {
			cr.select(root);
		}
		else
		{
			cr.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
		}
				
		result = em.createQuery(cr).getResultList();

		return result;
	}


}
