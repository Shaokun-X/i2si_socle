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

import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import fr.mines_stetienne.ci.i2si.calculator.expr.E_Add;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Divide;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Multiply;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Substract;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_UnaryMinus;
import fr.mines_stetienne.ci.i2si.calculator.expr.Expr;
import fr.mines_stetienne.ci.i2si.calculator.expr.ExprVisitor;
import fr.mines_stetienne.ci.i2si.calculator.expr.Value;
import fr.mines_stetienne.ci.i2si.calculator.expr.Variable;

public class ExpressionEvaluator implements ExprVisitor {

	// private final Map<Variable, Double> values = new HashMap<>();
	private final Map<String, Double> values = new HashMap<>();

	private double result;

	private Variable nonAssignedVar = null;

	public Variable getNonAssignedVar() {
		return nonAssignedVar;
	}

	public double getResult() {
		return result;
	}

	public void setVariableValue(Variable var, double value) {
		values.put(var.getName(), value);
	}

	@Override
	public void visit(Variable var) {
		if (values.containsKey(var.getName())) {
			result = values.get(var.getName());
		} else {
			nonAssignedVar = var;
		}
	}

	@Override
	public void visit(E_Add expr) {
		expr.getExpr1().accept(this);
		double v1 = result;
		expr.getExpr2().accept(this);
		double v2 = result;
		result = v2 + v1;
	}

	@Override
	public void visit(E_Substract expr) {
		expr.getExpr1().accept(this);
		double v1 = result;
		expr.getExpr2().accept(this);
		double v2 = result;
		result = v1 - v2;
	}

	@Override
	public void visit(E_Multiply expr) {
		expr.getExpr1().accept(this);
		double v1 = result;
		expr.getExpr2().accept(this);
		double v2 = result;
		result = v1 * v2;
	}

	@Override
	public void visit(E_Divide expr) {
		expr.getExpr1().accept(this);
		double v1 = result;
		expr.getExpr2().accept(this);
		double v2 = result;
		result = v1 / v2;
	}

	@Override
	public void visit(E_UnaryMinus expr) {
		expr.getExpr().accept(this);
		result = -result;
	}

	@Override
	public void visit(Value value) {
		result = value.getValue();
	}

}
