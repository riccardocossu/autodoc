/**
 * 
 */
package net.riccardocossu.autodoc.jpa;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EntityResult;
import javax.persistence.Enumerated;
import javax.persistence.ExcludeDefaultListeners;
import javax.persistence.ExcludeSuperclassListeners;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.MapKeyJoinColumns;
import javax.persistence.MapKeyTemporal;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

import net.riccardocossu.autodoc.base.AnnotationModel;
import net.riccardocossu.autodoc.base.AnnotationsPlugin;
import net.riccardocossu.autodoc.base.BaseAbstractPlugin;

/**
 * @author riccardo
 * 
 */
public class JPAPlugin extends BaseAbstractPlugin implements AnnotationsPlugin {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final List<? extends Class> MANAGED = Arrays.asList(
			Access.class, AssociationOverride.class,
			AssociationOverrides.class, AttributeOverride.class,
			AttributeOverrides.class, Basic.class, Cacheable.class,
			CollectionTable.class, Column.class, ColumnResult.class,
			DiscriminatorColumn.class, DiscriminatorValue.class,
			ElementCollection.class, Embeddable.class, Embedded.class,
			EmbeddedId.class, Entity.class, ExcludeDefaultListeners.class,
			ExcludeSuperclassListeners.class, FieldResult.class,
			GeneratedValue.class, Id.class, IdClass.class, Inheritance.class,
			JoinColumn.class, JoinColumns.class, JoinTable.class, Lob.class,
			ManyToMany.class, ManyToOne.class, MapKey.class, MapKeyClass.class,
			MapKeyColumn.class, MapKeyEnumerated.class, MapKeyJoinColumn.class,
			MapKeyJoinColumns.class, MapKeyTemporal.class,
			MappedSuperclass.class, MapsId.class, NamedNativeQueries.class,
			NamedNativeQuery.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.riccardocossu.autodoc.base.AnnotationsPlugin#getManagedAnnotations()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<? extends Class> getManagedAnnotations() {
		// TODO Auto-generated method stub
		return MANAGED;
	}

	public AnnotationModel parse(Access target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(AssociationOverride target) {
		return getAnnotationValues(target, "name", "joinColumn", "joinTable");
	}

	public AnnotationModel parse(AssociationOverrides target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(AttributeOverrides target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(AttributeOverride target) {
		return getAnnotationValues(target, "column", "name");
	}

	public AnnotationModel parse(Basic target) {
		return getAnnotationValues(target, "fetchType", "optional");
	}

	public AnnotationModel parse(Cacheable target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(CollectionTable target) {
		return getAnnotationValues(target, "catalog", "joinColumns", "name",
				"schema", "uniqueConstraints");
	}

	public AnnotationModel parse(Column target) {
		return getAnnotationValues(target, "columnDefinition", "insertable",
				"length", "name", "nullable", "precision", "scale", "table",
				"unique", "updatable");
	}

	public AnnotationModel parse(ColumnResult target) {
		return getAnnotationValues(target, "name");
	}

	public AnnotationModel parse(DiscriminatorColumn target) {
		return getAnnotationValues(target, "columnDefinition",
				"discriminatorType", "name", "length");
	}

	public AnnotationModel parse(DiscriminatorValue target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(ElementCollection target) {
		return getAnnotationValues(target, "fetch", "targetClass");
	}

	public AnnotationModel parse(Embeddable target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(Embedded target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(EmbeddedId target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(Entity target) {
		return getAnnotationValues(target, "name");
	}

	public AnnotationModel parse(EntityListeners target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(EntityResult target) {
		return getAnnotationValues(target, "entityClass");
	}

	public AnnotationModel parse(Enumerated target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(ExcludeDefaultListeners target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(ExcludeSuperclassListeners target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(FieldResult target) {
		return getAnnotationValues(target, "column", "name");
	}

	public AnnotationModel parse(GeneratedValue target) {
		return getAnnotationValues(target, "genrator", "strategy");
	}

	public AnnotationModel parse(Id target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(IdClass target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(Inheritance target) {
		return getAnnotationValues(target, "strategy");
	}

	public AnnotationModel parse(JoinColumn target) {
		return getAnnotationValues(target, "columnDefinition", "insertable",
				"name", "nullable", "referencedColumnName", "table", "unique",
				"updatable");
	}

	public AnnotationModel parse(JoinColumns target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(JoinTable target) {
		return getAnnotationValues(target, "catalog", "inverseJoinColumns",
				"joinColumns", "name", "schema", "uniqueConstraints", "unique",
				"updatable");
	}

	public AnnotationModel parse(Lob target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(ManyToMany target) {
		return getAnnotationValues(target, "cascade", "fetch", "mappedBy",
				"targetEntity");
	}

	public AnnotationModel parse(ManyToOne target) {
		return getAnnotationValues(target, "cascade", "fetch", "optional",
				"targetEntity");
	}

	public AnnotationModel parse(MapKey target) {
		return getAnnotationValues(target, "name");
	}

	public AnnotationModel parse(MapKeyClass target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(MapKeyColumn target) {
		return getAnnotationValues(target, "columnDefinition", "insertable",
				"length", "name", "nullable", "precision", "scale", "table",
				"unique", "updatable");
	}

	public AnnotationModel parse(MapKeyEnumerated target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(MapKeyJoinColumn target) {
		return getAnnotationValues(target, "columnDefinition", "insertable",
				"name", "nullable", "referencedColumnName", "table", "unique",
				"updatable");
	}

	public AnnotationModel parse(MapKeyJoinColumns target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(MapKeyTemporal target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(MappedSuperclass target) {
		return getAnnotationValues(target, new String[0]);
	}

	public AnnotationModel parse(MapsId target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(NamedNativeQueries target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(NamedNativeQuery target) {
		return getAnnotationValues(target, "name", "query", "hints",
				"resultClass", "resulSetMapping");
	}

	public AnnotationModel parse(NamedQueries target) {
		return getAnnotationValues(target, "value");
	}

	public AnnotationModel parse(NamedQuery target) {
		return getAnnotationValues(target, "name", "query", "hints", "lockMode");
	}

	protected String toString(JoinColumn target) {
		return super.toString(target);
	}

	protected String toString(JoinColumn[] target) {
		return super.toString(target);
	}

	protected String toString(JoinTable target) {
		return super.toString(target);
	}

	protected String toString(Column target) {
		return super.toString(target);
	}

	protected String toString(Column[] target) {
		return super.toString(target);
	}

	protected String toString(QueryHint target) {
		return super.toString(target);
	}

	protected String toString(QueryHint[] target) {
		return super.toString(target);
	}

}
