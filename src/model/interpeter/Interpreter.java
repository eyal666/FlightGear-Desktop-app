package model.interpeter;

import model.interpeter.commands.*;
import model.interpeter.commands.assests.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;

public class Interpreter extends Observable {

    public static HashMap<String,ExpressionCommand> commandMap;
    public static HashMap<String, Variable> symbolTable;
    public static ArrayList<String> lexArr;
    public static String simulatorVarsScript;
    public volatile static boolean flag=false;


    public Interpreter(String simulatorVarsScript) {
        this.commandMap = new HashMap<>();
        this.symbolTable = new HashMap<>();
        this.lexArr = new ArrayList<>();
        this.simulatorVarsScript = simulatorVarsScript;
        //define interpreter commands
        commandMap.put("openDataServer", new ExpressionCommand(new OpenDataServerCommand()));
        commandMap.put("connect", new ExpressionCommand(new ConnectCommand()));
        commandMap.put("disconnect", new ExpressionCommand(new DisconnectCommand()));
        commandMap.put("var", new ExpressionCommand(new VarCommand()));
        commandMap.put("=", new ExpressionCommand(new AssignCommand()));
        commandMap.put("bind", new ExpressionCommand(new BindCommand()));
        commandMap.put("while", new ExpressionCommand(new WhileCommand()));
        commandMap.put("return", new ExpressionCommand(new ReturnCommand()));
        commandMap.put("print", new ExpressionCommand(new PrintCommand()));
        commandMap.put("sleep", new ExpressionCommand(new SleepCommand()));
    }

    public static HashMap<String, Variable> getSymbolTable() {
        return symbolTable;
    }

    public int parser(){
        int retVal=0;
        for (int i = 0; i < lexArr.size(); i++) {
            ExpressionCommand c=commandMap.get(lexArr.get(i));
            if(c!=null){
                retVal=(int)c.calculate(lexArr, i);
                i+=retVal; //i skips to the next command to be execute
            }
        }
        return retVal;
    }

    public void lexer(String []  lines) {
        StringBuilder sb = new StringBuilder();
        for (String s : lines) {
            s = s.replaceAll(" \\+ ", "+");
            s = s.replaceAll(" \\- ", "-");
            s = s.replaceAll(" \\* ", "*");
            s = s.replaceAll(" \\/ ", "/");
            s = s.replaceAll(" \\)", ")");
            s = s.replaceAll("\\( ", "(");
            s = s.replaceAll("\"", "");
            sb.append(s).append(" ");
        }
        Collections.addAll(lexArr, sb.toString().replaceAll("=", " = ").split("\\s"));
        lexArr.removeIf(s->(s.equals("")));
    }

}
