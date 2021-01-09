/*
 * Copyright 2019 École des Mines de Saint-Étienne.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.mines_stetienne.ci.i2si.calculator;

import java.io.PrintWriter;

import fr.mines_stetienne.ci.i2si.calculator.expr.E_Add;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Divide;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Multiply;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Substract;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_UnaryMinus;
import fr.mines_stetienne.ci.i2si.calculator.expr.ExprVisitor;
import fr.mines_stetienne.ci.i2si.calculator.expr.Value;
import fr.mines_stetienne.ci.i2si.calculator.expr.Variable;

public class ExpressionSerializer implements ExprVisitor {
	
	private final PrintWriter pw;
	
	public ExpressionSerializer(PrintWriter pw) {
		this.pw = pw;
	}

	@Override
	public void visit(Variable var) {
		pw.print(var.getName());
	}

	@Override
	public void visit(E_Add expr) {
		expr.getExpr1().accept(this);
		pw.print(" + ");
		expr.getExpr2().accept(this);
	}

	@Override
	public void visit(E_Substract expr) {
		expr.getExpr1().accept(this);
		pw.print(" - ");
		expr.getExpr2().accept(this);
	}

	@Override
	public void visit(E_Multiply expr) {
		expr.getExpr1().accept(this);
		pw.print(" * ");
		expr.getExpr2().accept(this);
	}

	@Override
	public void visit(E_Divide expr) {
		expr.getExpr1().accept(this);
		pw.print(" / ");
		expr.getExpr2().accept(this);
	}

	@Override
	public void visit(E_UnaryMinus expr) {
		pw.print(" - ");
		expr.getExpr().accept(this);
	}

	@Override
	public void visit(Value value) {
		pw.print(value.getValue());
	}

}
