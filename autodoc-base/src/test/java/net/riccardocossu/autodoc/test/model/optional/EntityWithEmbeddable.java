package net.riccardocossu.autodoc.test.model.optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EntityWithEmbeddable")
public class EntityWithEmbeddable {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "codice", column = @Column(name = "CODICE")),
			@AttributeOverride(name = "ottica", column = @Column(name = "OTTICA")),
			@AttributeOverride(name = "tipo", column = @Column(name = "TIPO")) })
	private EmbeddedIdClass id;

}// AnagraficheView
