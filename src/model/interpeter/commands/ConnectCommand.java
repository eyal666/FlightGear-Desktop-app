package model.interpeter.commands;

import model.interpeter.commands.assests.DataWriterClient;

import java.util.ArrayList;

public class ConnectCommand extends CommonCommand {


    @Override
    public int execute(ArrayList<String> args, int index) {
        DataWriterClient client = new DataWriterClient(args.get(index + 1), Integer.parseInt(args.get(index + 2)));
        client.runClient();
        return 2;
    }
}
