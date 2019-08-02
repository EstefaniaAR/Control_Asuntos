package edugem.gob.mx.Control_Acceso.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="catalogos")
public class Catalogo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATALOGOS_SEQ")
    @SequenceGenerator(sequenceName = "catalogos_seq", allocationSize = 1, name = "CATALOGOS_SEQ")
	@Column(name="id")
	private Long id;
	private String clave;
	private String descripcion; 
	private String icon;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "Catalogo [id=" + id + ", clave=" + clave + ", descripcion=" + descripcion + "]";
	}
	
	
}
