package model.interpeter.commands;

import model.interpeter.Interpreter;
import model.interpeter.commands.assests.Variable;

import java.util.ArrayList;

public class BindCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        if(!Interpreter.symbolTable.containsKey(args.get(index+1))) {
            Interpreter.symbolTable.put(args.get(index+1), new Variable(0.0,args.get(index+1)));
        }
        Interpreter.symbolTable.replace(args.get(index-2), Interpreter.symbolTable.get(args.get(index+1)));
        return 1;
    }
}
