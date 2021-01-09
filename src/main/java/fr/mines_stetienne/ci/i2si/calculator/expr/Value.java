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

public class Value implements Expr, Comparable<Value> {
	
    private final double value;

    public Value(String value) {
        this.value = Double.parseDouble(value);
    }
    
    public Value(double value) {
    	this.value = value;
    }
    
    public double getValue() {
		return value;
	}
    
    @Override
    public void accept(ExprVisitor visitor) {
    	visitor.visit(this);
    }

	@Override
	public int compareTo(Value other) {
		return Double.compare(value, other.value);
	}

}
