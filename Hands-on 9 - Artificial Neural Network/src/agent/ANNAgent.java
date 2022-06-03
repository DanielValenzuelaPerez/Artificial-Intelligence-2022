package agent;

import classes.ANN;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ANNAgent extends Agent{
    ANN ann;
    double[][] inputs;
    double[][] targets;
    double[][] testInputs;
    double[][] testTargets;
    
    @Override
    protected void setup(){
        System.out.println("Hello!\nI'm the Artificial Neural Network Agent");
        //simpleExampleSetUp();
        //xorSetUp();
        breastCancerSetUp();
        
        trainAgent();
    }
    @Override
    protected void takeDown(){
        System.out.println("Terminating Artificial Neural Network Agent");
    }
    protected void simpleExampleSetUp(){
        inputs = new double [][] {
            {1, 4, 5}
        };
        targets = new double [][] {
            {0.1, 0.05}
        };
        int hiddenNodes = 2;
        
        ann = new ANN(inputs, hiddenNodes, targets);
        ann.showProgress(true);
        ann.setIterations(5000);
        
        testInputs = inputs;
        testTargets = targets;
    }
    protected void xorSetUp(){
        inputs = new double [][]{
            {0,0},
            {0,1},
            {1,0},
            {1,1}
        };
        targets = new double [][]{
            {0},
            {1},
            {1},
            {0}
        };
        int hiddenNodes = 4;
        
        ann = new ANN(inputs, hiddenNodes, targets);
        ann.showProgress(true);
        ann.setIterations(100000);
        
        testInputs = inputs;
        testTargets = targets;
    }
    protected void breastCancerSetUp(){
        inputs = new double[469][5]; 
        targets = new double[469][2]; 
        testInputs = new double[99][5]; 
        testTargets = new double[99][2]; 
        
        String path = "dataFixed.csv"; // Must be inside project folder (same direction as src) https://stackoverflow.com/questions/17287478/get-file-from-project-folder-using-java
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine(); // skip header
            int i = 0;
            // Training data
            while((line = br.readLine()) != null && i < inputs.length){
                String[] values = line.split(",");
                // Targets DIAGNOSIS (M, B)
                if("B".equals(values[1])){ // Values at 1 is the diagnosis, B=0,1 and M=1,0
                    targets[i][0] = 1;
                    targets[i][1] = 0;
                } 
                else if("M".equals(values[1])){
                    targets[i][0] = 0;
                    targets[i][1] = 1;
                }
                // Inputs
                for(int j = 0; j < inputs[0].length; j++)
                    inputs[i][j] = Double.parseDouble(values[j+2]);
                i++;
            }
            //Testing data
            i = 0;
            while((line = br.readLine()) != null && i < testInputs.length){
                String[] values = line.split(",");
                // Targets DIAGNOSIS (M, B)
                if("B".equals(values[1])){ // Values at 1 is the diagnosis, B=0,1 and M=1,0
                    testTargets[i][0] = 1;
                    testTargets[i][1] = 0;
                } 
                else if("M".equals(values[1])){
                    testTargets[i][0] = 0;
                    testTargets[i][1] = 1;
                }
                // Inputs
                for(int j = 0; j < testInputs[0].length; j++)
                    testInputs[i][j] = Double.parseDouble(values[j+2]);
                i++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        int hiddenNodes = 10;
        ann = new ANN(inputs, hiddenNodes, targets);
        ann.showProgress(true);
        ann.setIterations(500000);
    }
    public void trainAgent(){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                ann.train();
            }
            @Override
            public int onEnd(){
                testAgent();
                return super.onEnd();
            }
        });
    }
    public void testAgent(){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                ann.testAccuracy(testInputs, testTargets);
                ann.test(new double[] {.12, .28, .31, .15, .25});
            }
            @Override
            public int onEnd(){
                myAgent.doDelete();
                return super.onEnd();
            }
        });
    }
}
