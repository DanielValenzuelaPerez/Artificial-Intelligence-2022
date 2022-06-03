package agents;

import classes.DataSet;
import classes.GradientRegression;
import gui.GDGui;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;


public class GDAgent extends Agent{
    private GDGui myGui;
    GradientRegression gradientRegression = new GradientRegression();
    
    @Override
    protected void setup(){
        System.out.println("Hello! I'm the GradientRegression agent.");
        //SLR
        double [][] data = {{1,2,101}, {2,4,102}, {3,6,103}, {4,8,104}, {5,10,105}, {6,12,106}, {7,14,107}, {8,16,108}, {9,18,109}, {10,20,110}, {11,22,111}, {12,24,112}, {13,26,113}, {14,28,114}, {15,30,115}, {16,32,116}, {17,34,117}};
        //double [][] data = {{23, 651}, {26, 762}, {30, 856}, {34, 1063}, {43, 1190}, {48, 1298}, {52, 1421}, {57, 1440}, {58, 1518}};
        DataSet dataSet = new DataSet(data);
        /*gradientRegression.gradientDescent(dataSet);*/
        /*
        // MLR
        DataSet dataSet = new DataSet(new double [][]{ // -153.51, 1.24, 12.09
            {41.9, 29.1, 251.3}, // lr = 0.0002 ; i = 100,000,000
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
        });*/
        gradientRegression = new GradientRegression(dataSet, 0.0002, 100000000);
        
        myGui = new GDGui(this);
        myGui.showGui();
    }
    @Override
    protected void takeDown(){
        System.out.println("Terminating Gradient Descent Agent");
    }
    public void predict(final double [] factor){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                gradientRegression.predictFor(factor);
            }
        });
    }
    public int getNumberOfFactors(){
        return gradientRegression.getCoefficients().length;
    }
}
