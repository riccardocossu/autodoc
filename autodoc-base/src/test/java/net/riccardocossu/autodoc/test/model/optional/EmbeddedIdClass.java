package net.riccardocossu.autodoc.test.model.optional;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class EmbeddedIdClass implements Serializable {

	private String codice;
	private String ottica;
	private String tipo;

	@Column(name = "CODICE", insertable = false, updatable = false)
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name = "OTTICA", insertable = false, updatable = false)
	public String getOttica() {
		return ottica;
	}

	public void setOttica(String ottica) {
		this.ottica = ottica;
	}

	@Column(name = "TIPO")
	public String getTipo() {
		return tipo;
	}// getTipo

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
