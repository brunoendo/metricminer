package org.metricminer.tasks.metric.staticcounter;

import java.util.HashSet;
import java.util.Set;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

public class StaticCounterMetricVisitor extends VoidVisitorAdapter<Object> {

	public int staticAttributesCounter = 0;
	public Set<String> staticAttributesName = new HashSet<String>();
	public int staticMethodsCounter = 0;
	public Set<String> staticMethodsName = new HashSet<String>();

	public void visit(FieldDeclaration fieldDeclaration, Object arg) {
		if (isStatic(fieldDeclaration.getModifiers())) {
			staticAttributesCounter++;
			staticAttributesName.add(fieldDeclaration.getVariables().get(0).getId().getName());
		}
		
		super.visit(fieldDeclaration, arg);
	}
	
	@Override
	public void visit(MethodDeclaration methodDeclaration, Object arg1) {
		if (isStatic(methodDeclaration.getModifiers())) {
			staticMethodsCounter++;
			staticMethodsName.add(methodDeclaration.getName());
		}
		super.visit(methodDeclaration, arg1);
	}

	private boolean isStatic(int modifiers) {
		return (modifiers & 8) > 0;
	}

}