package agent;

import classes.DataSet;
import classes.LogisticRegression;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;


public class LRAgent extends Agent{
    LogisticRegression logisticRegression;
    DataSet testData;
    
    @Override
    protected void setup(){
        System.out.println("Hello!\nI'm the Logistic Regression Agent");
        //nbaWinnerSetUp();
        schoolAdmissionSetUp();
        
        trainAgent();
    }
    @Override
    protected void takeDown(){
        System.out.println("Terminating Logistic Regression Agent");
    }
    protected void nbaWinnerSetUp(){
        DataSet dataSet = new DataSet(new double[][] {
            {1,1,0},
            {4,2,1},
            {2,4,1}
        });
        logisticRegression = new LogisticRegression(dataSet);
        logisticRegression.showProgress(true);
        logisticRegression.setIterations(5000);
        testData = new DataSet(dataSet.getData());
    }
    protected void schoolAdmissionSetUp(){
        DataSet dataSet = new DataSet(new double [][]{
            {780, 4.0, 3, 1},
            {750, 3.9, 4, 1},
            {690, 3.3, 3, 0},
            {710, 3.7, 5, 1},
            {680, 3.9, 4, 0},
            {730, 3.7, 6, 1},
            {690, 2.3, 1, 0},
            {720, 3.3, 4, 1},
            {740, 3.3, 5, 1}
        });
        logisticRegression = new LogisticRegression(dataSet);
        logisticRegression.showProgress(true);
        logisticRegression.setIterations(40000000);
        testData = new DataSet(dataSet.getData());
        testData = new DataSet(new double[][] {{600, 1, 1, 1}});
    }
    public void trainAgent(){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                logisticRegression.train();
            }
            @Override
            public int onEnd(){
                testAgent(testData);
                return super.onEnd();
            }
        });
    }
    public void testAgent(final DataSet data){
        addBehaviour(new OneShotBehaviour(){
            @Override
            public void action(){
                for(double[] data: testData.getData())
                    logisticRegression.test(data);
            }
            @Override
            public int onEnd(){
                myAgent.doDelete();
                return super.onEnd();
            }
        });
    }
}
