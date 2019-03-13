package org.topbraid.shacl.model.impl;

import org.topbraid.shacl.model.SHACLAbstractPropertyConstraint;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.spin.util.JenaUtil;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.vocabulary.RDFS;

/**
 * Default implementation of SHACLAbstractPropertyConstraint.
 * 
 * @author Holger Knublauch
 */
public abstract class SHACLAbstractPropertyConstraintImpl extends SHACLTemplateCallImpl implements SHACLAbstractPropertyConstraint {
	
	public SHACLAbstractPropertyConstraintImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}
	
	
	@Override
	public String getComment() {
		return JenaUtil.getStringProperty(this, RDFS.comment);
	}


	@Override
	public Property getPredicate() {
		Resource r = JenaUtil.getResourceProperty(this, SH.predicate);
		if(r != null && r.isURIResource()) {
			return new PropertyImpl(r.asNode(), (EnhGraph)r.getModel());
		}
		else {
			return null;
		}
	}

	
	@Override
	public Resource getValueTypeOrDatatype() {
		Resource valueClass = JenaUtil.getResourceProperty(this, SH.class_);
		if(valueClass != null) {
			return valueClass;
		}
		else {
			Resource directValueType = JenaUtil.getResourceProperty(this, SH.directType);
			if(directValueType != null) {
				return directValueType;
			}
			else {
				return JenaUtil.getResourceProperty(this, SH.datatype);
			}
		}
	}


	@Override
	public String getVarName() {
		Property argProperty = getPredicate();
		if(argProperty != null) {
			return argProperty.getLocalName();
		}
		else {
			return null;
		}
	}
}