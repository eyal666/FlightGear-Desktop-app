package model.interpeter.commands;

import model.interpeter.commands.assests.DataReaderServer;
import model.interpeter.commands.assests.DataWriterClient;

import java.util.ArrayList;

public class DisconnectCommand extends CommonCommand {


    @Override
    public int execute(ArrayList<String> args, int index) {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {}
        DataWriterClient.out.println("bye");
        DataWriterClient.out.flush();
        DataWriterClient.out.close();
        DataReaderServer.close();
        System.out.println("disconnected from server...");
        return 0;
    }
}
