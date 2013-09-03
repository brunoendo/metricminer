package org.metricminer.tasks.metric.common;

import japa.parser.ast.body.Parameter;

import java.util.List;

import org.metricminer.antlr4.java.JavaParser.FormalParameterListContext;

public class FullMethodName {

	public static String fullMethodName(String name, List<Parameter> parameters) {
		return name + "/" + (parameters == null ? "0" : (parameters.size() + typesIn(parameters)));
	}

	public static String fullMethodName(String name, FormalParameterListContext parameters) {
		return name + "/" + (parameters == null ? "0" : (parameters.formalParameter().size() + typesIn(parameters)));
	}

	private static String typesIn(FormalParameterListContext parameters) {
		StringBuilder types = new StringBuilder();
		types.append("[");
		
		for(int i = 0; i < parameters.formalParameter().size(); i++) {
			String type = parameters.formalParameter().get(i).type().getText();
			types.append(type + ",");
		}
		
		return types.substring(0, types.length() - 1) + "]";
	}

	private static String typesIn(List<Parameter> parameters) {
		StringBuilder types = new StringBuilder();
		types.append("[");
		for(Parameter p : parameters) {
			types.append(p.getType() + ",");
		}
		
		return types.substring(0, types.length() - 1) + "]";
	}
}
