package edugem.gob.mx.Control_Acceso.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Table(name="respuestas")
@Entity
public class Respuesta implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPUESTAS_SEQ")
    @SequenceGenerator(sequenceName = "respuestas_seq", allocationSize = 1, name = "RESPUESTAS_SEQ")
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Asunto asunto;
	
	@Column(name="fecha_respuesta")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date fechaRespuesta;
	
	@ManyToOne()
	@JoinColumn(name = "atendida_id")
	private Catalogo atendida;
	
	private String seguimiento;
	private String referencia;
	
	@Lob
	@Column(length=100000)
	@Basic(fetch = FetchType.LAZY)
	private byte[] archivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public Catalogo getAtendida() {
		return atendida;
	}

	public void setAtendida(Catalogo atendida) {
		this.atendida = atendida;
	}

	public String getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(String seguimiento) {
		this.seguimiento = seguimiento;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public Asunto getAsunto() {
		return asunto;
	}

	public void setAsunto(Asunto asunto) {
		this.asunto = asunto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public String toString() {
		return "Respuesta [id=" + id + ", fechaRespuesta=" + fechaRespuesta + ", atendida=" + atendida
				+ ", seguimiento=" + seguimiento + ", referencia=" + referencia + "]";
	}
	

}
