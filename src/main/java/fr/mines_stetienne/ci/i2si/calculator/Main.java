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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mines_stetienne.ci.i2si.calculator.expr.Assignment;
import fr.mines_stetienne.ci.i2si.calculator.expr.Expr;
import fr.mines_stetienne.ci.i2si.calculator.expr.Variable;
import fr.mines_stetienne.ci.i2si.calculator.lang.ArithmeticParser;
import fr.mines_stetienne.ci.i2si.calculator.lang.ParseException;

public class Main {

	/**
	 * The logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	/** h */
	public static final String ARG_HELP = "h";
	/** help */
	public static final String ARG_HELP_LONG = "help";
	/** Show help */
	public static final String ARG_HELP_MAN = "Show help";

	/** f */
	public static final String ARG_FILE = "f";
	/** file */
	public static final String ARG_FILE_LONG = "file";
	/** Input file */
	public static final String ARG_FILE_MAN = "File or URL that contains a sequence of operations, one per line";

	/** i */
	public static final String ARG_INTERACTIVE = "i";
	/** interactive */
	public static final String ARG_INTERACTIVE_LONG = "interactive";
	/** interactive mode */
	public static final String ARG_INTERACTIVE_MAN = "Run in interactive mode";

	/** p */
	public static final String ARG_PRETTIFY = "p";
	/** prettify */
	public static final String ARG_PRETTIFY_LONG = "prettify";
	/** interactive mode */
	public static final String ARG_PRETTIFY_MAN = "Prettify the given expression";

	/** p */
	public static final String ARG_DIGITS = "n";
	/** prettify */
	public static final String ARG_DIGITS_LONG = "number";
	/** interactive mode */
	public static final String ARG_DIGITS_MAN = "Number of digits after the decimal point in output result";

	public static final Options OPTIONS = new Options().addOption(ARG_HELP, ARG_HELP_LONG, false, ARG_HELP_MAN)
			.addOption(ARG_FILE, ARG_FILE_LONG, true, ARG_FILE_MAN)
			.addOption(ARG_INTERACTIVE, ARG_INTERACTIVE_LONG, false, ARG_INTERACTIVE_MAN)
			.addOption(ARG_PRETTIFY, ARG_PRETTIFY_LONG, true, ARG_PRETTIFY_MAN)
			.addOption(ARG_DIGITS, ARG_DIGITS_LONG, true, ARG_DIGITS_MAN);

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			printHelpAndExit();
		}

		DefaultParser commandLineParser = new DefaultParser();
		CommandLine cl = commandLineParser.parse(OPTIONS, args, false);

		if (cl.hasOption(ARG_HELP)) {
			printHelpAndExit();
		}

		if (cl.hasOption(ARG_INTERACTIVE)) {
			execInteractive();
		}

		if (cl.hasOption(ARG_FILE)) {
			execFile(cl.getOptionValue(ARG_FILE));
		}

		if (cl.hasOption(ARG_PRETTIFY)) {
			prettifyExpressionAndExit(cl.getOptionValue(ARG_PRETTIFY));
		}

		args = cl.getArgs();
		String inlineExpression = String.join(" ", args);

		if (!inlineExpression.isEmpty()) {
			Double result = evaluateExpression(inlineExpression);
			if (cl.hasOption(ARG_DIGITS)) {
				String n = cl.getOptionValue(ARG_DIGITS);
				printResult(result, Integer.parseInt(n));
			} else {
				printResult(result);
			}
		}
	}

	public static double evaluateExpression(String inlineExpression) throws ParseException {
		ArithmeticParser parser = new ArithmeticParser(inlineExpression);
		Expr expression = parser.parseExpression();
		ExpressionEvaluator evaluator = new ExpressionEvaluator();
		expression.accept(evaluator);
		return evaluator.getResult();
	}

	public static void printResult(Double result) {
		System.out.println("The result is " + result);
	}

	public static void printResult(Double result, Integer digits) {
		System.out.printf("The result is %." + digits + "f", result);
	}

	public static void execFile(String path) throws ParseException, IOException {
		List<String> lines = Files.readAllLines(Paths.get(path));
		ExpressionEvaluator evaluator = new ExpressionEvaluator();
		execLines(lines, evaluator);
	}

	public static void execLines(List<String> lines, ExpressionEvaluator evaluator) throws ParseException {
		for (String line : lines) {
			ArithmeticParser parser = new ArithmeticParser(line);
			Assignment assignment = parser.parseAssignment();
			Expr expression = assignment.getExpression();
			expression.accept(evaluator);
			if (evaluator.getNonAssignedVar() != null) {
				System.out.println("Variable `" + evaluator.getNonAssignedVar().getName() + " ` is not assigned");
				break;
			}
			double result = evaluator.getResult();
			Variable var = assignment.getVar();	
			if (var == null) {
				System.out.println(result);
			} else {
				evaluator.setVariableValue(var, result);
				System.out.println(var.getName() + " = " + result);
				LOG.debug("Variable " + var.getName() + " is set to " + result);
			}
		}
	}
	
	public static void execInteractive() throws ParseException {
		ExpressionEvaluator evaluator = new ExpressionEvaluator();
		Scanner scanner = new Scanner(System.in);
		List<String> buffer = new ArrayList<>();
		String line;
		System.out.println("You have entered interactive mode. Type `exit` or `quit` to stop.");
		while (true) {
			System.out.print(">>> ");
			line = scanner.nextLine().toLowerCase().strip();
			if (line.equals("exit") || line.equals("quit")) {
				break;
			}
			buffer.add(line);
			execLines(buffer, evaluator);
			buffer.clear();
		}
		scanner.close();
		System.exit(1);
	}

	public static void printHelpAndExit() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("Calculator", OPTIONS);
		System.exit(1);
	}
	
	public static void prettifyExpressionAndExit(String expr) {
		System.out.println(expr.replaceAll("\\s+", "").replace("", " ").trim());
		System.exit(1);
	}
}
