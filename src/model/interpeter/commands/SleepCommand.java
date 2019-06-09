package model.interpeter.commands;

import java.util.ArrayList;

public class SleepCommand extends CommonCommand {
    @Override
    public int execute(ArrayList<String> args, int index) {
        try {
            Thread.sleep(Integer.parseInt(args.get(index+1)));
        } catch (InterruptedException e) {}
        return 1;
    }
}
