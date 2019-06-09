package model.interpeter.commands;

import model.interpeter.Interpreter;
import model.interpeter.commands.assests.DataWriterClient;
import model.interpeter.commands.assests.Variable;
import model.interpeter.expression.ShuntingYardAlg;

import java.util.ArrayList;

public class AssignCommand extends CommonCommand {



    @Override
    public int execute(ArrayList<String> args, int index) {
        if(args.get(index+1).equals("bind")){
            return 0;
        }
        Variable tempVar=Interpreter.symbolTable.get(args.get(index-1));
        if(tempVar.varName.contains("/")) {
            DataWriterClient.out.println("set " + tempVar.varName + " " + ShuntingYardAlg.calc(args.get(index + 1)));
            DataWriterClient.out.flush();
            return 1;
        }
        tempVar.setValue(ShuntingYardAlg.calc(args.get(index + 1)));
        return 1;
    }
}
