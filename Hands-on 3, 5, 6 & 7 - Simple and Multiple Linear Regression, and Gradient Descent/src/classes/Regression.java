package classes;

public interface Regression {
    double predictFor(double [] factors);
    double [] getCoefficients();
    void printEquation();
}