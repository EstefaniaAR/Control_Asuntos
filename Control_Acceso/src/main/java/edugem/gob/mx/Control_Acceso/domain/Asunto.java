package edugem.gob.mx.Control_Acceso.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="asuntos")
public class Asunto implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASUNTOS_SEQ")
    @SequenceGenerator(sequenceName = "asuntos_seq", allocationSize = 1, name = "ASUNTOS_SEQ")
	@Column(name="id")
	private Long id;
	
	@Column(name="fecha_registro")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date fechaRegistro;
	
	private String resumen;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "nivel_id")
	private Catalogo nivel;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "subsistema_id")
	private Catalogo subsistema;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "atencion_id")
	private Catalogo Atencion;
	
	private String dgipo;
	
	private String referencia;
	
	@Column(name="fecha_oficio")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOficio;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "remitente_id")
	private Catalogo remitente;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "cargo_id")
	private Catalogo cargo;
	
	@Lob
	@Column(length=100000)
	@Basic(fetch = FetchType.LAZY)
	private byte[] anexo;
	
	//Seguimiento
	private String instruccion;
	
	@Column(name="tipo_asunto")
	private String tipoAsunto;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "estatus_id")
	private Catalogo estatus;
	
	@OneToMany(mappedBy="asunto",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Respuesta>respuestas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public Catalogo getNivel() {
		return nivel;
	}

	public void setNivel(Catalogo nivel) {
		this.nivel = nivel;
	}

	public Catalogo getSubsistema() {
		return subsistema;
	}

	public void setSubsistema(Catalogo subsistema) {
		this.subsistema = subsistema;
	}


	public Catalogo getAtencion() {
		return Atencion;
	}

	public void setAtencion(Catalogo atencion) {
		Atencion = atencion;
	}

	public String getDgipo() {
		return dgipo;
	}

	public void setDgipo(String dgipo) {
		this.dgipo = dgipo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Date getFechaOficio() {
		return fechaOficio;
	}

	public void setFechaOficio(Date fechaOficio) {
		this.fechaOficio = fechaOficio;
	}

	public byte[] getAnexo() {
		return anexo;
	}

	public void setAnexo(byte[] anexo) {
		this.anexo = anexo;
	}

	public Catalogo getRemitente() {
		return remitente;
	}

	public void setRemitente(Catalogo remitente) {
		this.remitente = remitente;
	}

	public Catalogo getCargo() {
		return cargo;
	}

	public void setCargo(Catalogo cargo) {
		this.cargo = cargo;
	}

	public String getInstruccion() {
		return instruccion;
	}

	public void setInstruccion(String instruccion) {
		this.instruccion = instruccion;
	}

	public String getTipoAsunto() {
		return tipoAsunto;
	}

	public void setTipoAsunto(String tipoAsunto) {
		this.tipoAsunto = tipoAsunto;
	}

	public Catalogo getEstatus() {
		return estatus;
	}

	public void setEstatus(Catalogo estatus) {
		this.estatus = estatus;
	}



	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	@Override
	public String toString() {
		return "Asunto [id=" + id + ", fechaRegistro=" + fechaRegistro + ", resumen=" + resumen + ", nivel=" + nivel
				+ ", subsistema=" + subsistema + ", Atencion=" + Atencion + ", dgipo=" + dgipo + ", referencia="
				+ referencia + ", fechaOficio=" + fechaOficio + ", remitente=" + remitente + ", cargo=" + cargo
				+ ", anexo="  + ", instruccion=" + instruccion + ", tipoAsunto=" + tipoAsunto
				+ ", estatus=" + estatus + ", respuestas=" + respuestas + "]";
	}



}
