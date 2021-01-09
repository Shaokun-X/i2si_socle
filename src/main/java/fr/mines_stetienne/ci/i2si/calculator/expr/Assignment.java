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

public class Assignment {
	
	private final Variable var;
	private final Expr expression;	
	
	public Assignment(Variable var, Expr expression) {
		super();
		this.var = var;
		this.expression = expression;
	}
	
	public Variable getVar() {
		return var;
	}
	
	public Expr getExpression() {
		return expression;
	}
	
}
