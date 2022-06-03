package agent;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;


public class PSOAgent extends Agent{
    int iteration;
    int iterations;
    
    int d; // dimensions
    
    double [] x; 
    double [] v;

    double w;
    double [] r;
    double [] c;

    double pFitness;
    double [] pBest;
    static double gFitness;
    static double [] gBest;
    
    @Override
    protected void setup(){
        System.out.println("Hello! I'm the '" + getAID().getLocalName() + "' from the PSO");
        
        d = 2;
        iteration = 0;
        iterations = 500;
        
        x = new double[d];
        v = new double[d];
        for(int i = 0; i < d; i++){
            x[i] = Math.random()*5;
            v[i] = Math.random();
            x[i] += v[i];
        }

        w = 0.8; // 0.8
        r = new double[] {Math.random(), Math.random()}; // Math.random(), Math.random()
        c = new double[] {0.1, 0.1}; // 0.1, 0.1

        pFitness = f();
        pBest = new double[d];
        for(int i = 0; i < d; i++)
            pBest[i] = x[i];
        if(gFitness == 0 || pFitness < gFitness){
            gFitness = pFitness;
            gBest = new double[d];
            for(int i = 0; i < d; i++)
                gBest[i] = pBest[i];
        }
        printProgress();
        
        iteration();
    }
    @Override
    protected void takeDown(){
        System.out.println("Terminatting '" + getAID().getLocalName() + "' from the PSO");
    }
    
    public void iteration(){
        addBehaviour(new SimpleBehaviour(){
            @Override
            public void action(){
                for(int i = 0; i < d; i++){
                    v[i] = w*v[i] + c[0]*r[0]*(pBest[i] - x[i]) + c[1]*r[1]*(gBest[i] - x[i]);
                    x[i] += v[i];
                }
                // Check fitness
                if(f() < pFitness){
                    pFitness = f();
                    for(int i = 0; i < d; i++)
                        pBest[i] = x[i];
                    if(pFitness < gFitness){
                        gFitness = pFitness;
                        for(int i = 0; i < d; i++)
                            gBest[i] = pBest[i];
                    }
                }
                //iteration++;
                printProgress();
            }
            @Override
            public int onEnd(){
                myAgent.doDelete();
                return super.onEnd();
            }
            @Override
            public boolean done() {
                return iteration == iterations;
            }
        });
    }
    private double f(){
        //return -153.51 + 1.24*x[0] + 12.08*x[1];
        
        // (7.58) <1.61, 2.72> https://www.wolframalpha.com/input?i=minimize+%28x-3.14%29%5E2+%2B+%28y-2.72%29%5E2+%2B+%283x%2B1.41%29+%2B+sin%284x-1.73%29
        //return Math.pow(x[0] - 3.14, 2) + Math.pow(x[1] - 2.72, 2) + (3*x[0] + 1.41) + Math.sin(4*x[1] - 1.73); 
        
        // (-1.81) <3.19, 3.13> https://machinelearningmastery.com/a-gentle-introduction-to-particle-swarm-optimization/
        //return Math.pow(x[0] - 3.14, 2) + Math.pow(x[1] - 2.72, 2) + Math.sin(3*x[0] + 1.41) + Math.sin(4*x[1] - 1.73); 
        
        // (0) <0, 0>
        return Math.pow(x[0], 2) + Math.pow(x[1], 2);
        
        // (-64.00) <4.00, 4.00> https://www.wolframalpha.com/input?i=Minimize+x%5E3%2By%5E3-12xy
        //return Math.pow(x[0], 3) + Math.pow(x[1], 3) - 12*x[0]*x[1]; 
        
        // (0) <1,2> 10(x-1)^2 + 20(y-2)^2 https://www.wolframalpha.com/input?i=minimize+10%28x-1%29%5E2+%2B+20%28y-2%29%5E2
        //return 10*Math.pow(x[0]-1, 2) + 20*Math.pow(x[1]-2, 2);
        
        // (0) <1,2,3> 10(x-1)^2 + 20(y-2)^2 + 30(z-3)^2 https://www.wolframalpha.com/input?i=minimize+10%28x-1%29%5E2+%2B+20%28y-2%29%5E2+%2B+30%28z-3%29%5E2
        //return 10*Math.pow(x[0]-1, 2) + 20*Math.pow(x[1]-2, 2) + 30*Math.pow(x[2] - 3, 2); // 3 variables!
        
        // Data set from Hands-on 5
        /*
        double [][] data = new double [][] {
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
        double avgX1 = 0;
        double avgX2 = 0;
        double avgY = 0;
        for(int i = 0; i < data.length; i++){
            avgX1 += data[i][0];
            avgX2 += data[i][1];
            avgY += data[i][0];
        }
        avgX1 /= data.length;
        avgX2 /= data.length;
        avgY /= data.length;
        return Math.pow(avgY - (x[0] + x[1]*avgX1 + x[2]*avgX2), 2);*/
        // 288.4 - (x + y*55.4 + z*30.9)
    }
    private String printArray(double [] array){
        String msg = "<";
        for(int i = 0; i < array.length; i++){
            msg += String.format("%.2f", array[i]);
            if(i + 1 != array.length)
                msg += ", ";
        }
        msg += ">\t";
        return msg;
    }
    private void printProgress(){
        // x, v, pBest(pFit), gBest(gFit)
        String msg = getAID().getLocalName() + ":\t";
        msg += "(" + String.format("%.2f", f()) + ")";
        msg += printArray(x);
        msg += printArray(v);
        msg += "(" + String.format("%.2f", pFitness) + ")";
        msg += printArray(pBest);
        msg += "(" + String.format("%.2f",gFitness) + ")";
        msg += printArray(gBest);
        System.out.println(msg);
    }
}
