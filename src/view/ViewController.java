package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import viewModel.ViewModel;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ViewController implements Initializable, Observer {

    ViewModel vm;
    MapDisplayer mapDisplayer; //todo: add notation to map and implement solution display
    @FXML
    Canvas map;
    @FXML
    Circle joyStick;
    @FXML
    Circle frame;
    @FXML
    Slider throttle;
    @FXML
    Slider rudder;
    @FXML
    Button connect;
    @FXML
    Button loadData;
    @FXML
    Button loadScript;
    @FXML
    Button runScript;
    @FXML
    TextArea scriptAsText;
    @FXML
    Button calcPath;
    @FXML
    RadioButton autoPilot;
    @FXML
    RadioButton manual;
    Joystick myJoyStick;
    boolean isScriptLoaded=false;
    boolean isConnected=false;


    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup tg = new ToggleGroup();
        manual.setToggleGroup(tg);
        autoPilot.setToggleGroup(tg);
        manual.fire();
        this.myJoyStick=new Joystick(this);
        this.mapDisplayer=new MapDisplayer(this);
    }
    public void setViewModel(ViewModel vm) {
        this.vm = vm;
        vm.joyStickX.bind(joyStick.centerXProperty());
        vm.joyStickY.bind(joyStick.centerYProperty());
        vm.throttle.bind(throttle.valueProperty());
        vm.rudder.bind(rudder.valueProperty());
    }
    //buttons func's
    public void connectToFlightgear() {
        System.out.println("connected");
        Stage popup = new Stage();
        VBox box = new VBox(20);
        Label ipLabel = new Label("IP:");
        Label portLabel = new Label("PORT:");
        TextField ipUserInput = new TextField();
        TextField portUserInput = new TextField();
        Button submit = new Button("Submit");
        box.getChildren().addAll(ipLabel, ipUserInput, portLabel, portUserInput, submit);
        popup.setScene(new Scene(box, 350, 250));
        popup.setTitle("Connect to FlightGear");
        popup.show();
        submit.setOnAction(e ->
        {
            String ip = ipUserInput.getText(); // saving ip and port data!
            String port = portUserInput.getText();
            vm.connectToSimVM(ip, port);
            popup.close();
        });
        isConnected=true;
    }
    public void calcPath() {
        System.out.println("connected");
        Stage popup = new Stage();
        VBox box = new VBox(20);
        Label ipLabel = new Label("IP:");
        Label portLabel = new Label("PORT:");
        TextField ipUserInput = new TextField();
        TextField portUserInput = new TextField();
        Button submit = new Button("Submit");
        box.getChildren().addAll(ipLabel, ipUserInput, portLabel, portUserInput, submit);
        popup.setScene(new Scene(box, 350, 250));
        popup.setTitle("Connect to path calc server");
        popup.show();
        submit.setOnAction(e ->
        {
            String ip = ipUserInput.getText(); // saving ip and port data!
            String port = portUserInput.getText();
            vm.connectToCalcServerVm(ip, port);
            popup.close();
            showPathOnMap();
        });

    }
    public void loadData() {

    } //use to load CSV file

    //**********************************autopilot func's****************************************//

    public void autoPilotMode() {
        System.out.println("autopilot mode is on");
        manual.disarm();
        rudder.setDisable(true);
        throttle.setDisable(true);
        joyStick.setDisable(true);
        loadScript.setDisable(false);
        runScript.setDisable(false);
    }
    public void runScript(){
        if(isConnected&&isScriptLoaded){
           vm.runScriptVm(scriptAsText.getText());
        }
        else{
            if(!isConnected) {
                popuper("Please connect first");
            }
            if(!isScriptLoaded){
                popuper("Please load script first");
            }
        }
    }
    public void popuper(String name){
        Stage popup = new Stage();
        VBox box = new VBox(10);
        Label msg = new Label(name);
        Button ok = new Button("ok");
        box.getChildren().addAll(msg, ok);
        popup.setScene(new Scene(box, 250, 100));
        popup.setTitle("Massage");
        popup.show();
        ok.setOnAction(e->{
            if(name.contains("script")) loadScript();
            if(name.contains("connect")) connectToFlightgear();
            popup.close();
        });
    } //manage the functionality of run script button in case of no script or no server connection
    public void loadScript() {

        FileChooser fc=new FileChooser();
        fc.setTitle("Choose script");
        fc.setInitialDirectory(new File("./scripts"));
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("txt", "*.txt"));
        File chosen= fc.showOpenDialog(null);
        if(chosen!=null){
            System.out.println(chosen.getName());
        }
        try {
            Scanner s=new Scanner(new FileReader(chosen)).useDelimiter("\n");
            while (s.hasNext()){
                scriptAsText.appendText(s.next()+"\n");
            }
        } catch (FileNotFoundException e) {}
        isScriptLoaded=true;
    }

    //**********************************manual func's******************************************//

    public void manualMode() {
        autoPilot.disarm();
        rudder.setDisable(false);
        throttle.setDisable(false);
        loadScript.setDisable(true);
        joyStick.setDisable(false);
        runScript.setDisable(true);
        System.out.println("manual mode is on");
    }
    public void moveElevatorAileron(){
        myJoyStick.moveJoyStick();
    }  //limit joystick movment to the frame circle
    public void valFromJoystick(){
        vm.controlElevatorAileronVm();
    } //  envoked from joystick class to invoke vm joystick func after limit joystick movment
    public void moveThrottle() {
        vm.controlThrottleVm();
    }
    public void moveRudder() {
        vm.controlRudderVm();
    }

    //***********************************MAP DISPLAY************************************************//

    public void showPathOnMap(){
        mapDisplayer.convertPathToLine();
    }//use to envoke masDisplayer method that draw the solution on the map

    //**********************************************************************************************//
    @Override
    public void update(Observable o, Object arg) {}
}

