package model.interpeter.commands;

import model.interpeter.commands.assests.Variable;
import model.interpeter.Interpreter;

import java.util.ArrayList;

public class VarCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        Interpreter.symbolTable.put(args.get(index+1), new Variable(0.0,args.get(index+1)));
        return 1;
    }
}
