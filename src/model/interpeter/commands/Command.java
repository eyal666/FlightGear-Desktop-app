package model.interpeter.commands;

import java.util.ArrayList;

public interface Command {

    public int execute(ArrayList<String> args, int index); //execute and return num of arg to skipped, gets as String[] the the parameters
}