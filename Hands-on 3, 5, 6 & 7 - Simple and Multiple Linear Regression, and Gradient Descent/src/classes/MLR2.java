package classes;


public class MLR2 implements Regression{
    
    private double [][] coefficients;

    public MLR2(){
        coefficients = new double[][] {{0},{0}};
    }
    public MLR2(DataSet dataSet){
        DataSet dataSetX = dataSet.getDataSetX();
        DataSet dataSetY = dataSet.getDataSetY();
        this.coefficients = MatrixOperations.multiplication(MatrixOperations.inverse(MatrixOperations.multiplication(MatrixOperations.transpose(dataSetX.getDataSet()),dataSetX.getDataSet())),MatrixOperations.multiplication(MatrixOperations.transpose(dataSetX.getDataSet()), dataSetY.getDataSet()));
        printEquation();
    }
    
    @Override
    public double predictFor(double[] factors) {
        double y = this.coefficients[0][0];
        for(int i = 0; i < factors.length; i++)
        {
            y += factors[i] * this.coefficients[i+1][0];
        }
        System.out.println("y = " + String.format("%.2f", y));
        return y;
    }

    @Override
    public double[] getCoefficients() {
        double[] coefficientsArray = new double[]{};
        for(int i = 0; i < this.coefficients.length; i++)
            coefficientsArray[i] = this.coefficients[i][0];
        return coefficientsArray;
    }

    @Override
    public void printEquation() {
        String printEquation = "MLR2\ny = " + String.format("%.2f", this.coefficients[0][0]);
        for(int i = 1; i < this.coefficients.length; i++){
            printEquation += " + " + String.format("%.2f", this.coefficients[i][0]) + "*Factor" + i;
        }
        System.out.println(printEquation);
    }
    
}
