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
package fr.mines_stetienne.ci.i2si.calculator.expr;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.mines_stetienne.ci.i2si.calculator.ExpressionEvaluator;

public class E_AddTest {

	@Test
	public void test() {
    	Value v1 = new Value(10.5);
    	Value v2 = new Value(3.4);
    	E_Add expr = new E_Add(v1, v2);
    	ExpressionEvaluator evaluator = new ExpressionEvaluator();
    	expr.accept(evaluator);
    	assertEquals(13.9, evaluator.getResult(), 0.0);
	}

}
