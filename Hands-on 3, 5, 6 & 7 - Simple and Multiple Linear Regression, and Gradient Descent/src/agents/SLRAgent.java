package agents;

import gui.SLRGui;
import classes.DataSet;
import classes.SLR;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

public class SLRAgent extends Agent{
    private SLRGui myGui;
    SLR slr = new SLR();
    @Override
    protected void setup(){
        System.out.println("Hello! I'm the SLR agent.");
        double [][] data1 = {{23, 651}, {26, 762}, {30, 856}, {34, 1063}, {43, 1190}, {48, 1298}, {52, 1421}, {57, 1440}, {58, 1518}}; // esto hard-codeado en el DataSet
        //double [][] data1 = {{1, 2}, {2, 4}, {3,6},{4,8}, {5,10},{6,12},{7, 14}, {8,16},{9,18}, {10,20}}; //0,2
        DataSet dataSet = new DataSet(data1);
        slr = new SLR(dataSet);
        myGui = new SLRGui(this);
        myGui.showGui();
        
        /*addBehaviour(new Behaviour(this){ 
            @Override
            public void action() {
                //System.out.println("SLR behaviour");
                
                //slr.predictFor(new double[] {60});
                //slr.predictFor(new double[] {70});
                //slr.predictFor(new double[] {80});
            }

            @Override
            public boolean done() {
                return false;
            }
            
            @Override
            public int onEnd(){
                myAgent.doDelete();
                return super.onEnd();
            }
        });*/
    }
    
    @Override
    protected void takeDown(){
        System.out.println("Terminating SLR Agent");
    }
    
    public void predict(final double factor){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                slr.predictFor(new double[]{factor});
            }
        });
    }
}
