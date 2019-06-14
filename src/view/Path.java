package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Path extends Canvas {

    ViewController vc;
    Point initPoint, destPoint;
    String[] path;
    int rows, columns;
    int[][] pathMatrix;

    public Path() {
        initPoint = new Point();
        destPoint = new Point();
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
            gc.setFill(Color.BLACK);
            gc.fillText("X", e.getX(), e.getY());
            int row = Math.round((float) (vc.aircraft.mapSize[0] * e.getX() / getHeight()));
            int column = Math.round((float) (vc.aircraft.mapSize[1] * e.getY() / getWidth()));
            destPoint.setLocation(row, column);
            System.out.println(destPoint.toString());
        });
    }

    public void convertPathToLine() {
        try {
            PrintWriter pw=new PrintWriter(new FileWriter("mat.txt"));

        System.out.println("solution is: " + path.toString());
        rows = vc.aircraft.mapSize[0];
        columns = vc.aircraft.mapSize[1];
        pathMatrix=new int [rows][columns];
        GraphicsContext gc = getGraphicsContext2D();
        double H = this.getHeight();
        double W = this.getWidth();
        double h = H / rows;
        double w = W / columns;
        int i = initPoint.y, j = initPoint.x;
        for (String s : path) {
            pathMatrix[i][j]=1;
            switch (s) {
                case "Up":i++;
                case "Down":i--;
                case "Left":j--;
                case "Right":j++;
            }
        }
        for ( i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {
               pw.print(pathMatrix[i][j]+",");
            }
            pw.println();
        }
        for ( i = 0; i < rows; i++) {
            for ( j = 0; j < columns; j++) {
                if (pathMatrix[i][j] == 1) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(j * w, i * h, w, h);
                }
            }
        }
        } catch (IOException e) {}
    }
}
