package org.metricminer.tasks.metric.unitorsystemtest;

import static org.metricminer.tasks.metric.common.ClassAssumptions.isATest;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class UnitOrSystemTestVisitor extends VoidVisitorAdapter<Object> {

	private static final boolean INTEGRATION = true;
	
	private Map<String, TestInfo> tests = new HashMap<String, TestInfo>();
	private String productionClass;
	private Stack<String> currentMethod;
	private Map<String, Boolean> variableAndPackage;
	private Map<String, Boolean> fieldAndPackage;
	private boolean checkTypes;
	private boolean isUnitTest;
	private Map<String, String> importList;
	private VariableDeclaratorId lastDeclaredVariable;
	private Set<String> productionClassTypeVariables;
	private Set<String> productionClassTypeAttributes;
	
	
	public UnitOrSystemTestVisitor(String testClass) {
		this.productionClass = testClass.replace("Tests", "").replace("Test", "");
		currentMethod = new Stack<String>();
		importList = new HashMap<String, String>();
	}
	
	public void visit(ImportDeclaration n, Object arg) {
		importList.put(getClassInImport(n.toString()), n.toString());
	}
	
	public void visit(FieldDeclaration n, Object arg) {
		
		if(isProductionClass(n.getType())) {
			for(VariableDeclarator v : n.getVariables()) {
				productionClassTypeAttributes.add(v.getId().toString());
			}
		}
		else {
			for(VariableDeclarator v : n.getVariables()) {
				putAttributeInTheList(v.getId().getName(), n.getType().toString());
			}			
		}
		
	}
	
	public void visit(ConstructorDeclaration n, Object arg) {
	}
	
	public void visit(ClassOrInterfaceDeclaration expr, Object arg) {
		productionClassTypeAttributes = new HashSet<String>();
		fieldAndPackage = new HashMap<String, Boolean>();
		super.visit(expr, arg);
	}
	
	public void visit(ObjectCreationExpr expr, Object arg) {
		
		if(lastDeclaredVariable == null) {
			super.visit(expr, arg);
		}
		
		else if(isProductionClass(expr.getType())) {
			
			productionClassTypeVariables.add(lastDeclaredVariable.toString());
			checkTypes = true;
			isUnitTest = true;
			super.visit(expr, arg);
			
			saveTest(!isUnitTest);
			checkTypes = false;
		} else {
			putInstanceInTheList(lastDeclaredVariable.toString(), expr.getType().toString());
			lastDeclaredVariable = null;
			super.visit(expr, arg);
		}
	}

	public void visit(MethodCallExpr method, Object arg) {

		if(method.getScope()!=null && method.getScope().toString().startsWith("new ")) {
			String type = extractClassNameFromScope(method);
			if(importList.containsKey(type)) {
				saveTest(INTEGRATION);
			}
		}
		
		else if(method.getScope()!=null) {
			String var = method.getScope().toString();
			if(!instanceOfProductionClass(var) && 
					!instanceIsFromTheSamePackage(var)) {
				saveTest(INTEGRATION);
			}
		}

		if(containsParameters(method)) {
			for(Expression e : method.getArgs()) {
				if(e instanceof NameExpr) {
					String var = e.toString();
					
					if(!instanceOfProductionClass(var) && 
							!instanceIsFromTheSamePackage(var)) {
						saveTest(INTEGRATION);
					}
				}
			}
		}
		
		super.visit(method, arg);
	}

	private boolean containsParameters(MethodCallExpr method) {
		return method.getArgs()!=null;
	}

	private boolean instanceOfProductionClass(String var) {
		return (productionClassTypeVariables!=null && productionClassTypeVariables.contains(var)) 
				|| (productionClassTypeAttributes!=null && productionClassTypeAttributes.contains(var)) ;
	}

	private String extractClassNameFromScope(MethodCallExpr expr) {
		String clazz = expr.getScope().toString();
		String type = clazz.replace("new ", "");
		type = type.substring(0, type.indexOf("("));
		
		return type;
	}

	public void visit(VariableDeclarator expr, Object arg) {
		lastDeclaredVariable = expr.getId();
		super.visit(expr,arg);
	}
	

	public void visit(NameExpr name, Object arg) {
		
		if(checkTypes) {
			String var = name.toString();
			isUnitTest = instanceIsFromTheSamePackage(var);
		}
		
		super.visit(name, arg);
	}

	public void visit(MethodDeclaration expr, Object arg) {
		
		variableAndPackage = new HashMap<String, Boolean>();
		productionClassTypeVariables = new HashSet<String>();

		if(isATest(expr)) {
			currentMethod.push(expr.getName());
			
			tests.put(expr.getName(), new TestInfo(expr.getName()));

			super.visit(expr, arg);
			
			currentMethod.pop();
		}
		
	}
	
	public Map<String, TestInfo> getTests() {
		return tests;
	}
	
	private String getClassInImport(String importLine) {
		String[] breakLine = importLine.trim().split("\\.");
		return breakLine[breakLine.length - 1].replace(";", "");
	}

	private void saveTest(boolean integration) {
		tests.get(currentMethod.peek()).setIntegration(integration);
	}

	private void putInstanceInTheList(String name, String type) {
		Boolean value = variableAndPackage.get(name);
		boolean isPackage = classBelongsToSamePackage(type);
		
		if(value == null || value == true) {
			variableAndPackage.put(name, isPackage);
		}
	}
	private void putAttributeInTheList(String name, String type) {
		Boolean value = fieldAndPackage.get(name);
		boolean isPackage = classBelongsToSamePackage(type);
		
		if(value == null || value == true) {
			fieldAndPackage.put(name, isPackage);
		}
	}

	private boolean instanceIsFromTheSamePackage(String var) {
		return (variableAndPackage!=null && variableAndPackage.containsKey(var) && variableAndPackage.get(var)) || 
				(fieldAndPackage!=null && fieldAndPackage.containsKey(var) && fieldAndPackage.get(var));
	}

	private boolean isProductionClass(ClassOrInterfaceType type) {
		return type.toString().equals(productionClass);
	}
	private boolean isProductionClass(Type type) {
		return type.toString().equals(productionClass);
	}
	
	private boolean classBelongsToSamePackage(String var) {
		return !importList.containsKey(var);
	}

}
