package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.*;

public class Path extends Canvas {

    ViewController vc;
    Point initPoint, destPoint;
    String[] path;
    int rows, columns;
    Image x;

    public Path() {
        initPoint = new Point();
        destPoint = new Point();
        try {
            x=new Image(new FileInputStream("./images/x.png"));
        } catch (FileNotFoundException e) {}
    }

    public void setPath(String path, Point initPoint, Point destPoint) {
        this.path = path.split(",");
        this.initPoint = initPoint;
        this.destPoint = destPoint;
        convertPathToLine();
    }

    public void setVc(ViewController vc) {
        this.vc = vc;
    }

    public void setDestination() {
        setOnMouseClicked(e -> {
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            gc.drawImage(x, e.getX()-10, e.getY()-10,20,20);
            int row = Math.round((float) (vc.aircraft.mapSize[0] * e.getY() / getHeight()));
            int column = Math.round((float) (vc.aircraft.mapSize[1] * e.getX() / getWidth()));
            if(row>=vc.aircraft.mapSize[0]) row=vc.aircraft.mapSize[0]-1;
            if(column>=vc.aircraft.mapSize[1]) column=vc.aircraft.mapSize[1]-1;
            destPoint.setLocation(row, column);
        });
    }

    public void convertPathToLine() {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("mat.txt"));
            rows = vc.aircraft.mapSize[0];
            columns = vc.aircraft.mapSize[1];
            int [][] pathMatrix = new int[rows][columns];
            GraphicsContext gc = getGraphicsContext2D();
            double H = this.getHeight();
            double W = this.getWidth();
            double h = H / rows;
            double w = W / columns;
            int i =initPoint.y, j = initPoint.x;
            pathMatrix[i][j] = 1;
            gc.setFill(Color.BLACK);
            gc.fillOval(j*w+5, i*h+7, w/3,h/3);
            for (String s : path) {
                System.out.print(s + ",");
                if(s.equals("Up")) i--;
                if(s.equals("Down")) i++;
                if(s.equals("Left")) j--;
                if(s.equals("Right")) j++;
                pathMatrix[i][j] = 1;
                gc.setFill(Color.BLACK);
                gc.fillOval(j*w+7, i*h+5, w/3,h/3);
            }
        } catch (IOException e) {}
    }
}
