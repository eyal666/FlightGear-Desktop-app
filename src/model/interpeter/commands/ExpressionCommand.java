package model.interpeter.commands;

import model.interpeter.expression.Expression;

import java.util.ArrayList;


public class ExpressionCommand implements Expression {

    CommonCommand c;

    public ExpressionCommand(CommonCommand c) {
        this.c = c;
    }

    @Override
    public double calculate(ArrayList<String> args, int index) {
        return c.execute(args, index);
    }
}
