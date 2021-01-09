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

import static org.junit.Assert.*;

import org.junit.Test;

import fr.mines_stetienne.ci.i2si.calculator.ExpressionEvaluator;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Add;
import fr.mines_stetienne.ci.i2si.calculator.expr.E_Multiply;
import fr.mines_stetienne.ci.i2si.calculator.expr.Expr;
import fr.mines_stetienne.ci.i2si.calculator.expr.Value;
import fr.mines_stetienne.ci.i2si.calculator.expr.Variable;

public class ExpressionEvaluatorTest {

	@Test
	public void testAdd1() {
    	Value v1 = new Value(10.5);
    	Value v2 = new Value(3.4);
    	Expr expr = new E_Add(v1, v2);
    	ExpressionEvaluator evaluator = new ExpressionEvaluator();
    	expr.accept(evaluator);
    	assertEquals(13.9, evaluator.getResult(), 1e-6);
	}

	@Test
	public void testAdd2() {
    	Value v1 = new Value(10.1);
    	Variable x = new Variable("x");
    	Expr expr = new E_Add(v1, x);
    	ExpressionEvaluator evaluator = new ExpressionEvaluator();
    	evaluator.setVariableValue(x, 3.4);
    	expr.accept(evaluator);
    	assertEquals(13.5, evaluator.getResult(), 1e-6);
	}

	@Test
	public void testMultiply1() {
    	Value v1 = new Value(10.1);
    	Value v2 = new Value(3.4);
    	Expr expr = new E_Multiply(v1, v2);
    	ExpressionEvaluator evaluator = new ExpressionEvaluator();
    	expr.accept(evaluator);
    	assertEquals(34.34, evaluator.getResult(), 1e-6);
	}
	
}
