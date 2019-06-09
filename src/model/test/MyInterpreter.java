package model.test;

import model.interpeter.Interpreter;

public class MyInterpreter {

	public static  int interpret(String[] lines){
		String sim="./scripts/simulator_vars.txt";
		String test="./scripts/variables_order_mainTrain4.txt";
		Interpreter i=new Interpreter(sim);
		i.lexer(lines);

		return i.parser();
	}
}
