package classes;


public class SLR implements Regression {
    private double b1; // slope
    private double b0; // intercept
    
    public SLR(){
        b0 = 0;
        b1 = 0;
    }
    
    public SLR(DataSet dataSet){
        double[] coeficients = CramersRule.solve2by2(dataSet.getDataSet()); 
        this.b0 = coeficients[0];
        this.b1 = coeficients[1];
        printEquation();
    }

    @Override
    public double predictFor(double[] factor) {
        double y =  b0 + b1 * factor[0];
        System.out.println("y = " + String.format("%.2f", y));
        return y;
    }

    @Override
    public double[] getCoefficients() {
        return new double [] {b0, b1};
    }

    @Override
    public void printEquation() {
        System.out.println("SLR\ny = " + String.format("%.2f", this.b0) + " + " + String.format("%.2f", this.b1) + "*x");
    }
    
}