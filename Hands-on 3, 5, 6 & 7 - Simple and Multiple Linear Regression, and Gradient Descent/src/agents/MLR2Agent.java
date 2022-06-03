package agents;

import classes.DataSet;
import classes.MLR2;
import gui.MLR2Gui;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;


public class MLR2Agent extends Agent {
    private MLR2Gui myGui;
    MLR2 mlr = new MLR2();
    @Override
    protected void setup(){
        System.out.println("Hello! I'm the MLR2 agent.");
        myGui = new MLR2Gui(this);
        double [][] data2 = {
            {41.9, 29.1, 251.3},
            {43.4, 29.3, 251.3},
            {43.9, 29.5, 248.3},
            {44.5, 29.7, 267.5},
            {47.3, 29.9, 273.0},
            {47.5, 30.3, 276.5},
            {47.9, 30.5, 270.3},
            {50.2, 30.7, 274.9},
            {52.8, 30.8, 285.0},
            {53.2, 30.9, 290.0},
            {56.7, 31.5, 297.0},
            {57.0, 31.7, 302.5},
            {63.5, 31.9, 304.5},
            {65.3, 32.0, 309.3},
            {71.1, 32.1, 321.7},
            {77.0, 32.5, 330.7},
            {77.8, 32.9, 349.0}
        };
        DataSet dataSet2 = new DataSet(data2);
        mlr = new MLR2(dataSet2);
        myGui.showGui();
    }
    
    @Override
    protected void takeDown(){
        System.out.println("Terminating MLR2 Agent");
    }
    public void predict(final double factor1, final double factor2){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                mlr.predictFor(new double[]{factor1, factor2});
            }
        });
    }
}
