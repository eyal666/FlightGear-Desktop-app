package model.interpeter.commands;

import model.interpeter.Interpreter;
import model.interpeter.commands.assests.DataReaderServer;

import java.util.ArrayList;

public class OpenDataServerCommand extends CommonCommand {

    @Override
    public int execute(ArrayList<String> args, int index) {
        int port=Integer.parseInt(args.get(index+1));
        int rate=1000/Integer.parseInt(args.get(index+2));
        new Thread(() -> {
                DataReaderServer server = new DataReaderServer(port, rate);
                server.runServer();
            }).start();
        while (!Interpreter.flag);
        return 2;
    }
}
