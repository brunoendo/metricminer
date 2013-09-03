package org.metricminer.antlr4.java;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.metricminer.antlr4.java.JavaParser.CompilationUnitContext;

public class AntLRVisitor {

	public void visit(JavaBaseListener visitor, InputStream is) throws IOException {
		CharStream input = new ANTLRInputStream(is);
		
		JavaLexer lex = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        JavaParser parser = new JavaParser(tokens);
        CompilationUnitContext r = parser.compilationUnit();

        new ParseTreeWalker().walk(visitor, r);
	}
}
