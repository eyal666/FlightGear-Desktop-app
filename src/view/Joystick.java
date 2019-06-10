package view;

import javafx.scene.shape.Circle;

public class Joystick {
    ViewController vc;
   // Circle frame;
   // Circle joyStick;
    double radLimit;


    public Joystick(ViewController vc) {
//        this.frame = frame;
//        this.joyStick = joyStick;
        this.vc=vc;
        radLimit = vc.frame.getRadius() + vc.joyStick.getRadius();
    }

    public void moveJoyStick() {
        vc.joyStick.setOnMouseDragged(e -> {
            if (Math.sqrt(Math.pow(e.getX(), 2) + Math.pow(e.getY(), 2)) <= radLimit) {
                vc.joyStick.setCenterX(e.getX());
                vc.joyStick.setCenterY(e.getY());
                vc.valFromJoystick();
            }
        });
        vc.joyStick.setOnMouseReleased(e -> {
            vc.joyStick.setCenterX(0);
            vc.joyStick.setCenterY(0);
            vc.valFromJoystick();
        });
    }
}
