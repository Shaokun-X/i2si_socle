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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MainTest {

	@Test
	public void testEvaluateExpression1() {
		try {
			double result = Main.evaluateExpression(" 4 * 3");
			assertEquals(12.0, result, 0.0);
		} catch(Exception ex) {
			fail("Exception");
		}
	}

	@Test
	public void testExecLines() {
		List<String> lines = new ArrayList<>();
		ExpressionEvaluator evaluator = new ExpressionEvaluator();
		lines.add("x=4+2");
		lines.add("y=7+5");
		try {
			Main.execLines(lines, evaluator);
		} catch(Exception ex) {
			fail("Exception");
		}
	}

	@Test
	public void testMain1() {
		String[] args = { "4*3" };
		try {
			Main.main(args);
		} catch(Exception ex) {
			fail("Exception");
		}
	}

	@Test
	public void testMain2() {
		String[] args = { "4" , "*" , "3" };
		try {
			Main.main(args);
		} catch(Exception ex) {
			fail("Exception");
		}
	}

	@Test
	public void testMain3() {
		String[] args = { "-k" , "4 " , "*" , "3" };
		try {
			Main.main(args);
			fail("Exception");
		} catch(Exception ex) {
		}
	}

}
