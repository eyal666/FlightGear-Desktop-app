package model.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyMainTrain {

    public static void main(String [] args){
          try {
            BufferedReader inFromScript=new BufferedReader(new FileReader("./scripts/script4.txt"));
            String line;
            List<String> lines=new ArrayList<>();
            while((line=inFromScript.readLine())!=null){
                lines.add(line);
            }
            inFromScript.close();
            MyInterpreter.interpret(lines.toArray(new String[0]));
        } catch (FileNotFoundException e) {} catch (IOException e) {
            e.printStackTrace();
        }
    }
}
