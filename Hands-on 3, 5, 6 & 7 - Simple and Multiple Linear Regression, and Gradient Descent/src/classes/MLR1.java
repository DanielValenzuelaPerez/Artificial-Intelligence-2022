package classes;

public class MLR1 implements Regression{
    
    private double [] coefficients;
    
    public MLR1(){
        coefficients = new double[] {0,0};
    }
    
    public MLR1(DataSet dataSet){
        this.coefficients = CramersRule.solve3by3(dataSet.getDataSet());
        printEquation();
    }

    @Override
    public double predictFor(double[] factors) {
        double y = coefficients[0];
        
        for(int i = 0; i < 2; i++){
            y += coefficients[i+1] * factors[i];
        }
        System.out.println("y = " + String.format("%.2f", y));
        return y;
    }

    @Override
    public double[] getCoefficients() {
        return this.coefficients;
    }

    @Override
    public void printEquation() {
        System.out.println("MLR1\ny = " + String.format("%.2f", this.coefficients[0]) + " + " + String.format("%.2f", this.coefficients[1]) + "*Factor1 + " + String.format("%.2f", this.coefficients[2]) + "*Factor2");
    }

}
