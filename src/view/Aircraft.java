package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Aircraft extends Canvas implements Observer {
    Image aircraft;
    ViewController vc;
    DoubleProperty longitude, latitude, futureLong, futureLat;
    double x, y, pixelSize;
    int [] mapSize;

    public Aircraft() {
        try {
            aircraft=new Image(new FileInputStream("./images/c130j.png"));
        } catch (IOException e) {}
        longitude=new SimpleDoubleProperty();
        latitude=new SimpleDoubleProperty();
    }
    public void setAircraft(ViewController vc){
        this.vc=vc;
        x=vc.map.initCoordinate.getX();
        y=vc.map.initCoordinate.getY();
        mapSize=new int[]{vc.map.rows, vc.map.columns};
        pixelSize=vc.map.pixelSize;
        GraphicsContext gc=getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(aircraft,0,0, 20,20);
    }
    public void position(){
            double longNew=((longitude.doubleValue()-y)+pixelSize)/pixelSize;
            double latNew=((latitude.doubleValue()-x)+pixelSize)/pixelSize;
            GraphicsContext gc=getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            gc.drawImage(aircraft,latNew,longNew, 20,20);
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
