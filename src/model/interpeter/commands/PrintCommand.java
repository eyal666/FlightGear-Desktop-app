package model.interpeter.commands;

import model.interpeter.Interpreter;

import java.util.ArrayList;

public class PrintCommand extends CommonCommand {
    @Override
    public int execute(ArrayList<String> args, int index) {
        if(Interpreter.symbolTable.containsKey(args.get(index+1))){
            System.out.println(Interpreter.symbolTable.get(args.get(index+1)).getValue());
        }
        else {
            System.out.println(args.get(index + 1));
        }
        return 1;
    }
}
