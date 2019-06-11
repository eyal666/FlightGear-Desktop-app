package model.interpeter.commands;

import model.interpeter.Interpreter;
import model.interpeter.commands.assests.Variable;

import java.util.ArrayList;

public class VarCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        Interpreter.symbolTable.put(args.get(index+1), new Variable(0.0,null));
        return 1;
    }
}
