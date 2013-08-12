package net.riccardocossu.autodoc.test.model;

import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

@NamedQueries(value = { @NamedQuery(name = "test1", query = "testQuery1", hints = { @QueryHint(name = "name1", value = "hint1") }, lockMode = LockModeType.NONE) })
public class NestedQueryTestEntity {

}
