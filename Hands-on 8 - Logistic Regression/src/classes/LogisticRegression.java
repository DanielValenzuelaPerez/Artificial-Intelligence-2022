package classes;

public class LogisticRegression {
    DataSet inputs;
    DataSet outputs;
    
    double [] weights;
    
    double error;
    double learningRate;
    int iterations;
    int iteration;
    
    boolean showProgress;
    
    public LogisticRegression(DataSet dataSet){
        inputs = dataSet.getXs();
        outputs = dataSet.getYs();
        inputs.append1s();
        
        weights = new double[inputs.getCols()];
        for(int i = 0; i < weights.length; i++) 
            weights[i] = 0;
        
        learningRate = 0.1;
        iteration = 0;
        showProgress = false;
    }
    
    public void train(){
        while(iteration <= iterations){
            double [] sigmoid = calculateSigmoid();
            updateWeights(sigmoid);
            calculateTheError(sigmoid);
            
            printProgress();
            iteration++;
        }
    }
    private double[] calculateSigmoid(){
        double [] sigmoid = new double[inputs.getRows()];
        
        // Sum w*x
        double [] z = new double[inputs.getRows()];
        for(int i = 0; i < z.length; i++){
            z[i] = 0;
            for(int j = 0; j < inputs.getCols(); j++)
                z[i] += weights[j] * inputs.getData()[i][j];
        }
        
        // to sigmoid
        for(int i = 0; i < z.length; i++)
            sigmoid[i] = sigmoidFunction(z[i]);
        
        return sigmoid;
    }
    private void updateWeights(double[] sigmoid){
        for(int j = 0; j < weights.length; j++)
            for(int i = 0; i < sigmoid.length; i++)
                weights[j] -= learningRate * (sigmoid[i] - outputs.getData()[i][0]) * inputs.getData()[i][j];
    }
    private void calculateTheError(double [] sigmoid){
        this.error = 0;
        for(int i = 0; i < outputs.getRows(); i++)
            this.error += outputs.getData()[i][0] * Math.log10(sigmoid[i]) + (1.0 - outputs.getData()[i][0]) * Math.log10(1.0 - sigmoid[i]);
        this.error = - (this.error / outputs.getRows());
    }
    private double sigmoidFunction(double z){
        return 1 / (1 + Math.exp(-z));
    }
    private void printProgress(){
        if(iteration % 1000 != 0 || !showProgress) 
            return;
        String msg = "";
        if(iteration == 0){
            for(int i = 0; i < weights.length; i++)
                msg += "w" + i + ",\t";
            msg += "Error,\tIteration\n";
        }
        for(int i = 0; i < weights.length; i++)
                msg += String.format("%.2f", weights[i]) + ",\t";
        msg += String.format("%.4f", error) + ",\t" + iteration;
        System.out.println(msg);
    }
    public void showProgress(boolean showProgress){
        this.showProgress = showProgress;
    }
    public void setLearningRate(double learningRate){
        this.learningRate = learningRate;
    }
    public double test(double [] input){
        double z = weights[0];
        String msg = "Input: ";
        for(int i = 1; i < weights.length; i++){
            z += input[i-1] * weights[i];
            msg += input[i-1] + " ";
        }
        
        double result = sigmoidFunction(z);
        msg += "Result: " + String.format("%.2f", result);
        System.out.println(msg);
        return result;
    }
    public void setIterations(int iterations){
        this.iterations = iterations;
    }
}
