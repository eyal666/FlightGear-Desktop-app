package view;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class Joystick {
    Circle frame;
    Circle joyStick;
    double radLimit;


    public Joystick() {
//        this.frame = frame;
//        this.joyStick = joyStick;
        radLimit = frame.getRadius() + joyStick.getRadius();
    }

    public void moveJoyStick() {
        joyStick.setOnMouseDragged(e -> {
            if (Math.sqrt(Math.pow(e.getX(), 2) + Math.pow(e.getY(), 2)) <= radLimit) {
                joyStick.setCenterX(e.getX());
                joyStick.setCenterY(e.getY());
            }
        });
        joyStick.setOnMouseReleased(e -> {
            joyStick.setCenterX(0);
            joyStick.setCenterY(0);
        });
    }
}
