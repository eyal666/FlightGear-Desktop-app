package model.interpeter.commands;

import model.interpeter.commands.assests.Variable;
import model.interpeter.Interpreter;

import java.util.ArrayList;

public class WhileCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        Variable var=Interpreter.symbolTable.get(args.get(index+1));
        String operator=args.get(index+2);
        double value=Double.parseDouble(args.get(index+3));
        int endIndex=index+5;
        while(!args.get(endIndex).equals("}")) endIndex++;
        ArrayList<String> subArgs=new ArrayList<>(args.subList(index+5, endIndex));
        while(true){
            if(operator.equals("<") && (double)var.getValue()>=value) break;
            if(operator==">" && (double)var.getValue()<=value) break;
            if(operator=="<=" && (double)var.getValue()>value) break;
            if(operator==">=" && (double)var.getValue()<value) break;
            if(operator=="==" && (double)var.getValue()!=value) break;
            if(operator=="!=" && (double)var.getValue()==value) break;
            int retVal=0;
            for (int i=0 ; i<subArgs.size(); i++) {
                ExpressionCommand c=Interpreter.commandMap.get(subArgs.get(i));
                if(c!=null){
                    retVal=(int)c.calculate(subArgs, i);
                    i+=retVal; //i skips to the next command to be execute
                }
            }
        }
        return 5+subArgs.size();
    }
}
