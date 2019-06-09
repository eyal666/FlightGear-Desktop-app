package model.interpeter.commands;


import model.interpeter.expression.ShuntingYardAlg;

import java.util.ArrayList;
import java.util.List;

public class ReturnCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        List<String> subArr=new ArrayList<>(args.subList(index+1,args.size()));
        StringBuilder sb=new StringBuilder();
        for (String s : subArr){
            sb.append(s);
        }
        return (int)ShuntingYardAlg.calc(sb.toString());
    }
}
