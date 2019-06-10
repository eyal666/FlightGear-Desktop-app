package view;

public class Joystick {
    ViewController vc;
    double radLimit;


    public Joystick(ViewController vc) {
        this.vc=vc;
        radLimit = vc.frame.getRadius();
    }

    public void moveJoyStick() {
        vc.joyStick.setOnMouseDragged(e -> {
            double vectorLength=Math.sqrt(Math.pow(e.getX(), 2) + Math.pow(e.getY(), 2));//calc the (x,y) lenght from center of frame
            if (vectorLength <= radLimit) {
                vc.joyStick.setCenterX(e.getX());
                vc.joyStick.setCenterY(e.getY());
                vc.valFromJoystick();
            }
            else {
                vc.joyStick.setCenterX(e.getX() * radLimit / vectorLength);
                vc.joyStick.setCenterY(e.getY() * radLimit / vectorLength);
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
