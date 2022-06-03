package classes;

public class GradientRegression implements Regression{
    private double b[];
    private final double learningRate;
    private final int maxIterations;
    private final double error;
    private boolean stopGradient;
    
    public GradientRegression(){ //STEP 1
        learningRate = 0.0005; 
        maxIterations = 100000;
        error = 0.00001;
        stopGradient = false;
        b = new double[] {};
    }
    public GradientRegression(DataSet data){ //SLR lr = 0.0005 ; i = 100,000
        learningRate = 0.0005;
        maxIterations = 100000;
        error = 0.00001;
        stopGradient = false;
        gradientDescent(data);
    }
    public GradientRegression(DataSet data, double learningRate, int maxIterations){ //MLR lr = 0.0002 ; i = 100,000,000
        this.learningRate = learningRate;
        this.maxIterations = maxIterations;
        error = 0.00001;
        stopGradient = false;
        gradientDescent(data);
    }
    public final void gradientDescent(DataSet data){
        b = new double[data.getCols()];
        for(int i = 0; i < b.length; i++){
            b[i] = 0;
        }
        int step = 0;
        while(step < maxIterations && !stopGradient){
            stepGradient(data);
            step++;
            //printStep(step, data.getData());
        }
        printStep(step, data.getDataSet());
        printEquation();
    }
    private void stepGradient(DataSet data){ // STEP 2
        if(Math.abs(calculateTheError(data.getDataSet())) <= error){
            stopGradient = true;
            return;
        }
        
        final int n = data.getRows();
        final int numberOfFactors = data.getCols();
        double [] gradient = new double[numberOfFactors];
        for(int i = 0; i < numberOfFactors; i++)
            gradient[i] = 0;
        
        for(int i = 0; i < n; i++){
            double y = data.getDataSet()[i][numberOfFactors-1];
            double yHat = calculateYHat(data.getDataSet()[i]);
            
            gradient[0] += -(2.0/n) * (y - yHat);
            for(int k = 1; k < numberOfFactors; k++)
                gradient[k] += -(2.0/n) * data.getDataSet()[i][k-1] * (y - yHat);
        }
        
        for(int i = 0; i < numberOfFactors; i++)
            b[i] = b[i] - (learningRate * gradient[i]);
    }
    private double calculateTheError(double[][] data){ 
        double totalError = 0;
        for(int i = 0; i < data[0].length; i++){
            totalError += (data[i][data[i].length-1] - calculateYHat(data[i]))* (data[i][data[i].length-1] - calculateYHat(data[i]));
        }
        System.out.println("Error: " + totalError);
        return totalError / data.length;
    }
    private double calculateYHat(double [] factor){
        double yHat = b[0];
        for(int i = 0; i < factor.length - 1; i++)
            yHat += b[i+1] * factor[i];
        return yHat;
    }
    private void printStep(int step, double[][] data){
        String string = "Step " + step + ": ";
        for(int i = 0; i < b.length; i++)
            string += "b" + i + " = " + String.format("%.2f", b[i])+ ", ";
        string += String.format("e = %.5f", calculateTheError(data));
        System.out.println(string);
    }
    @Override
    public void printEquation(){
        String equation = "Gradient descent.\ny = " + String.format("%.2f", b[0]);
        for(int i = 1; i < b.length; i++){
            equation += " + " + String.format("%.2f", b[i]) + "*Factor" + i;
        }
        System.out.println(equation);
    }
    @Override
    public double predictFor(double[] x){
        if(b.length == 0) return 0;
        double y = b[0];
        for(int i = 1; i < b.length; i++){
            y += b[i] * x[i-1];
        }
        System.out.println("y = " + String.format("%.2f", y));
        return y;
    }
    @Override
    public double[] getCoefficients(){
        return b;
    }
}