package org.metricminer.tasks.metric.cc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.metricminer.antlr4.java.JavaBaseListener;
import org.metricminer.antlr4.java.JavaParser;
import org.metricminer.antlr4.java.JavaParser.ExpressionContext;
import org.metricminer.tasks.metric.common.FullMethodName;

public class CCListener extends JavaBaseListener {

    private Map<String, Integer> ccPerMethod;
    private Stack<String> methodStack;

    public CCListener() {
        ccPerMethod = new HashMap<String, Integer>();
        methodStack = new Stack<String>();
    }
    
	@Override public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
		String methodName = FullMethodName.fullMethodName(ctx.Identifier().getText(), ctx.formalParameters().formalParameterList());
		methodStack.push(methodName);
		increaseCc();
	}
	@Override public void exitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) { 
		methodStack.pop();
	}

	@Override public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
		String methodName = FullMethodName.fullMethodName(ctx.Identifier().getText(), ctx.formalParameters().formalParameterList());
		methodStack.push(methodName);
		increaseCc();
	}
	
	@Override public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) { 
		methodStack.pop();
	}

	@Override public void enterForExpression(JavaParser.ForExpressionContext ctx) { 
		increaseCc();
	}

	@Override public void enterIfExpression(JavaParser.IfExpressionContext ctx) {
		ExpressionContext exp = ctx.parExpression().expression();
		findAndOr(exp);
		
		increaseCc();
	}

	private void findAndOr(ExpressionContext exp) {
		for(int i = 0; i < exp.getChildCount(); i++) {
			if(exp.getChild(i).getClass().equals(ExpressionContext.class)) {
				findAndOr((ExpressionContext)exp.getChild(i));
			}
			
			if(exp.getChild(i).getText().equals("&&")) increaseCc();
			if(exp.getChild(i).getText().equals("&")) increaseCc();
			if(exp.getChild(i).getText().equals("||")) increaseCc();
			if(exp.getChild(i).getText().equals("|")) increaseCc();
		}
	}
	
	@Override public void enterExpression(JavaParser.ExpressionContext ctx) {
		for(int i = 0; i < ctx.getChildCount(); i++) {
			if(ctx.getChild(i).getText().equals("?")) increaseCc();
		}
	}
	
	@Override public void enterWhileExpression(JavaParser.WhileExpressionContext ctx) { 
		increaseCc();
		
	}
	
	@Override public void enterSwitchExpression(JavaParser.SwitchExpressionContext ctx) {
		
		for(int i=0; i< ctx.switchBlockStatementGroup().size();i++) {
			if(!ctx.switchBlockStatementGroup().get(i).getText().startsWith("default:")) {
				increaseCc();
			}
		}
		
	}

	@Override public void enterDoWhileExpression(JavaParser.DoWhileExpressionContext ctx) { 
		increaseCc();
		
	}
	@Override public void enterCatchClause(JavaParser.CatchClauseContext ctx) { 
		increaseCc();
		
	}

    private void increaseCc() {
        String currentMethod = methodStack.peek();
        if (!ccPerMethod.containsKey(currentMethod))
            ccPerMethod.put(currentMethod, 0);

        ccPerMethod.put(currentMethod, ccPerMethod.get(currentMethod) + 1);

    }

    public int getCc() {
        int cc = 0;
        for (Entry<String, Integer> method : ccPerMethod.entrySet()) {
            cc += method.getValue();
        }
        return cc;
    }

    public int getCc(String method) {
        return ccPerMethod.get(method);
    }

    public double getAvgCc() {
        double avg = 0;

        for (Entry<String, Integer> method : ccPerMethod.entrySet()) {
            avg += method.getValue();
        }

        return avg / ccPerMethod.size();
    }

    public Map<String, Integer> getCcPerMethod() {
        return ccPerMethod;
    }

}
