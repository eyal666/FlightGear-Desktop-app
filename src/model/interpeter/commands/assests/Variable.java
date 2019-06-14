package model.interpeter.commands.assests;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;

public class Variable extends Observable {

    public double value;
    public String varName;

    public Variable(double value, String varName) {
        this.value = value;
        this.varName = varName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
