package classes;

public class ANN {
    private double [][] inputData;
    private double [][] targetData;
    
    private int inputNodes;
    private int hiddenNodes;
    private int outputNodes;
    
    private Matrix inputs;
    private Matrix targets;
    private Matrix hiddens;
    private Matrix outputs;
    
    private Matrix weightsInputToHidden;
    private Matrix weightsHiddenToOutput;
    private Matrix biasHidden;
    private Matrix biasOutput;
    
    private double learningRate;
    
    private boolean showProgress;
    private int iterations;
    private int iteration;
    private double error;
    
    public ANN(double [][] inputData, int hiddenNodes, double [][] targetData){
        this.inputData = inputData;
        this.targetData = targetData;
        
        inputNodes = inputData[0].length;
        this.hiddenNodes = hiddenNodes;
        outputNodes = targetData[0].length;
        
        weightsInputToHidden = new Matrix(this.hiddenNodes, inputNodes);
        weightsHiddenToOutput = new Matrix(outputNodes, this.hiddenNodes);
        biasHidden = new Matrix(this.hiddenNodes, 1);
        biasOutput = new Matrix(outputNodes, 1);
        
        weightsInputToHidden.randomize();
        weightsHiddenToOutput.randomize();
        biasHidden.randomize();
        biasOutput.randomize();

        learningRate = 0.1;
        showProgress = false;
        iteration = 0;
    }
    public void train(){
        while(iteration <= this.iterations){
            // Select a random set of data from the input data to feed forward
            int r = (int)(Math.random()*this.inputData.length);
            this.inputs = Matrix.arrayToMatrix(this.inputData[r]);
            this.targets = Matrix.arrayToMatrix(this.targetData[r]);
            
            feedForward();
            calculateTheError();
            backPropagation();
            
            printProgress();
            
            iteration++;
        }
    }
    private void feedForward(){
        // Generating the hiddens' output [W]*[I] + [B]
        this.hiddens = Matrix.multiply(this.weightsInputToHidden, this.inputs);
        this.hiddens.add(this.biasHidden);
        toSigmoid(this.hiddens); // Activattion function
        
        // Generating the outputs' output [W]*[I] + [B]
        this.outputs = Matrix.multiply(this.weightsHiddenToOutput, this.hiddens);
        this.outputs.add(this.biasOutput);
        toSigmoid(this.outputs); // Activation function
    }
    public void calculateTheError(){
        this.error = 0;
        for(int i = 0; i < targets.getRows(); i++)
            this.error += Math.pow(outputs.getData()[i][0] - targets.getData()[i][0], 2); //(O-t)^2
    }
    private void backPropagation(){
        Matrix gradientsHiddenWeights = getGradientsHidden(false);
        Matrix gradientsHiddenBias = getGradientsHidden(true);
        Matrix gradientsOutputWeights = getGradientsOutput(false);
        Matrix gradientsOutputBias = getGradientsOutput(true);
        
        gradientsHiddenWeights.multiply(this.learningRate);
        gradientsHiddenBias.multiply(this.learningRate);
        gradientsOutputWeights.multiply(this.learningRate);
        gradientsOutputBias.multiply(this.learningRate);
        
        this.weightsInputToHidden.subtract(gradientsHiddenWeights);
        this.biasHidden.subtract(gradientsHiddenBias);
        this.weightsHiddenToOutput.subtract(gradientsOutputWeights);
        this.biasOutput.subtract(gradientsOutputBias);
    }
    private Matrix getGradientsHidden(boolean isForBias){
        double [][] gradients = new double[this.weightsInputToHidden.getRows()][this.weightsInputToHidden.getCols()];
        if(isForBias) gradients = new double[this.biasHidden.getRows()][this.biasHidden.getCols()];
        
        for(int i = 0; i < gradients.length; i++){
            for(int j = 0; j < gradients[0].length; j++){
                gradients[i][j] = 0;
            }
        }
        
        for(int i = 0; i < gradients[0].length; i++){
            for(int j = 0; j < gradients.length; j++){
                for(int k = 0; k < weightsHiddenToOutput.getRows(); k++){
                    gradients[j][i] += weightsHiddenToOutput.getData()[k][j] * outputs.getData()[k][0] * (1 - outputs.getData()[k][0]) * 2 * (outputs.getData()[k][0] - targets.getData()[k][0]);
                }
                double dz_d = inputs.getData()[i][0];
                if(isForBias) dz_d = 1;
                gradients[j][i] *= hiddens.getData()[j][0] * (1 - hiddens.getData()[j][0]) * dz_d;
            }
        }
        return new Matrix(gradients);  
    }
    private Matrix getGradientsOutput(boolean isForBias){
        double [][] gradients = new double[weightsHiddenToOutput.getRows()][weightsHiddenToOutput.getCols()];
        if(isForBias) gradients = new double[this.biasOutput.getRows()][this.biasOutput.getCols()];
        
        for(int i = 0; i < gradients[0].length; i++)
            for(int j = 0; j < gradients.length; j++){
                double dz_d = hiddens.getData()[i][0];
                if(isForBias) dz_d = 1;
                gradients[j][i] = 2 * (outputs.getData()[j][0] - targets.getData()[j][0]) * outputs.getData()[j][0] * (1 - outputs.getData()[j][0]) * dz_d;
            }
        return new Matrix(gradients);
    }
    private double sigmoid(double z){
        return 1.0 / (1.0 + Math.exp(-z));
    }
    private void toSigmoid(Matrix matrix){
        for(int i = 0; i < matrix.getRows(); i++)
            matrix.setDataPoint(i, 0, sigmoid(matrix.getData()[i][0])); // matrix[i][0]
    }
    public double[] test(double[] input){
        this.inputs = Matrix.arrayToMatrix(input);
        feedForward();
        
        System.out.println("TEST\nInput:");
        inputs.print();
        System.out.println("Output:");
        outputs.print();
        
        return this.outputs.matrixToArray();
    }
    public double testAccuracy(double[][] inputs, double[][] targets){
        double accuracy = 0;
        for(int i = 0; i < inputs.length; i++){
            this.inputs = Matrix.arrayToMatrix(inputs[i]);
            this.targets = Matrix.arrayToMatrix(targets[i]);
            feedForward();
            boolean accurate = true;
            for(int j = 0; j < outputs.getRows(); j++){
                if(Math.round(outputs.getData()[j][0]) != Math.round(this.targets.getData()[j][0])){
                    accurate = false;
                    break;
                }
            }
            if(accurate)
                accuracy++;
            String msg = "Output: ";
            for(int k = 0; k < this.outputNodes; k++)
                msg += String.format("%.0f", outputs.getData()[k][0]) + " ";
            msg += "Target: ";
            for(int k = 0; k < this.targets.getRows(); k++)
                msg += String.format("%.0f", this.targets.getData()[k][0]) + " ";
            msg += "Accuracy: " + String.format("%.0f", (accuracy/(i+1))*100) + "%";
            System.out.println(msg);
        }
        return accuracy/inputs.length;
    }
    public void showProgress(boolean showProgress){
        this.showProgress = showProgress;
    }
    private void printProgress(){
        if(iteration % 100 != 0 || !showProgress) return;
        
        String msg = "";

        if(iteration == 0)
            msg += "Inputs,\t\t\t\t\tOutputs,\tTargets,\tError,\tIteration\n";
        
        for(int i = 0; i < this.inputNodes; i++)
            msg += String.format("%.4f", inputs.getData()[i][0]) + " ";
        msg += ",\t";
        
        for(int i = 0; i < this.outputNodes; i++)
            msg += String.format("%.4f", outputs.getData()[i][0]) + " ";
        msg += ",\t";
        
        for(int i = 0; i < this.targets.getRows(); i++)
            msg += String.format("%.4f", targets.getData()[i][0]) + " ";
        msg += ",\t";
        
        msg += String.format("%.4f", error) + ",\t" + iteration;
        System.out.println(msg);
    }
    public void setIterations(int iterations){
        this.iterations = iterations;
    }
}
