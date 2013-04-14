package org.metricminer.tasks.metric.unitorsystemtest;

import static org.metricminer.tasks.metric.common.ClassAssumptions.isATest;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class UnitOrSystemTestVisitor extends VoidVisitorAdapter<Object> {

	private Map<String, TestInfo> tests = new HashMap<String, TestInfo>();
	private String productionClass;
	private Stack<String> currentMethod;
	private Map<String, Boolean> objectAndPackage;
	private boolean checkTypes;
	private boolean isUnitTest;
	private Map<String, String> importList;
	private VariableDeclaratorId lastDeclaredVariable;
	
	
	public UnitOrSystemTestVisitor(String testClass) {
		this.productionClass = testClass.replace("Tests", "").replace("Test", "");
		currentMethod = new Stack<String>();
		importList = new HashMap<String, String>();
	}
	
	public void visit(ImportDeclaration n, Object arg) {
		importList.put(getClassInImport(n.toString()), n.toString());
	}
	private String getClassInImport(String importLine) {
		String[] breakLine = importLine.trim().split("\\.");
		return breakLine[breakLine.length - 1].replace(";", "");
	}

	public void visit(ObjectCreationExpr expr, Object arg) {
		
		if(isProductionClass(expr.getType())) {
			checkTypes = true;
			isUnitTest = true;
			super.visit(expr, arg);
			tests.get(currentMethod.peek()).setIntegration(!isUnitTest);
			checkTypes = false;
		} else {
			putObjectInTheList(expr);
			super.visit(expr, arg);
		}
	}

	private void putObjectInTheList(ObjectCreationExpr expr) {
		Boolean value = objectAndPackage.get(lastDeclaredVariable.toString());
		boolean isPackage = isSamePackage(expr.getType().toString());
		
		if(value == null || value == true) {
			objectAndPackage.put(lastDeclaredVariable.toString(), isPackage);
		}
		
	}
	
	public void visit(VariableDeclarator expr, Object arg) {
		lastDeclaredVariable = expr.getId();
		super.visit(expr,arg);
	}
	
	private boolean isProductionClass(ClassOrInterfaceType type) {
		return type.toString().equals(productionClass);
	}

	public void visit(NameExpr name, Object arg) {
		
		if(checkTypes) {
			String var = name.toString();
			isUnitTest = objectAndPackage.get(var);
		}
		
		super.visit(name, arg);
	}
	
	private boolean isSamePackage(String var) {
		return !importList.containsKey(var);
	}

	public void visit(MethodDeclaration expr, Object arg) {
		if(isATest(expr)) {
			currentMethod.push(expr.getName());
			objectAndPackage = new HashMap<String, Boolean>();
			tests.put(expr.getName(), new TestInfo(expr.getName()));
		}
		
		super.visit(expr, arg);
		
		if(isATest(expr)) currentMethod.pop();
	}
	
	public Map<String, TestInfo> getTests() {
		return tests;
	}
}
