package classes;


public class CramersRule {
    public static double[] solve3by3(double[][] dataSet){
        
        int n = dataSet.length;
        double sumX1 = 0;
        double sumX2 = 0;
        double sumX1X2 = 0;
        double sumX1X1 = 0;
        double sumX2X2 = 0;
        double sumY = 0;
        double sumYX1 = 0;
        double sumYX2 = 0;
        
        final int x1 = 0;
        final int x2 = 1;
        final int y = 2;
        for(int i = 0; i < dataSet.length; i++)
        {
            sumX1 += dataSet[i][x1];
            sumX2 += dataSet[i][x2];
            sumX1X2 += dataSet[i][x1] * dataSet[i][x2];
            sumX1X1 += dataSet[i][x1] * dataSet[i][x1];
            sumX2X2 += dataSet[i][x2] * dataSet[i][x2];
            sumY += dataSet[i][y];
            sumYX1 += dataSet[i][y] * dataSet[i][x1];
            sumYX2 += dataSet[i][y] * dataSet[i][x2];
        }
        
        double ds = n*sumX1X1*sumX2X2 - n*sumX1X2*sumX1X2 - sumX1*sumX1*sumX2X2 + 2*sumX1*sumX2*sumX1X2 - sumX1X1*sumX2*sumX2;
        double db0 = sumY*sumX1X1*sumX2X2 - sumY*sumX1X2*sumX1X2 - sumX1*sumYX1*sumX2X2 + sumX1*sumX1X2*sumYX2 + sumX2*sumYX1*sumX1X2 - sumX2*sumX1X1*sumYX2;
        double db1 = n*sumYX1*sumX2X2 - n*sumX1X2*sumYX2 - sumY*sumX1*sumX2X2 + sumY*sumX1X2*sumX2 + sumX2*sumX1*sumYX2 - sumX2*sumYX1*sumX2;
        double db2 = n*sumX1X1*sumYX2 - n*sumYX1*sumX1X2 - sumX1*sumX1*sumYX2 + sumX1*sumYX1*sumX2 + sumY*sumX1*sumX1X2 - sumY*sumX1X1*sumX2;
        
        
        double b0 = db0/ds;
        double b1 = db1/ds;
        double b2 = db2/ds;
        return new double[] {b0, b1, b2};
    }
    
    public static double[] solve2by2(double[][] dataSet){
        
        int n = dataSet.length;
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumXX = 0;
        
        for(int i = 0; i < n; i++){
            sumX += dataSet[i][0];
            sumXX += dataSet[i][0] * dataSet[i][0];
            sumY += dataSet[i][1];
            sumXY += dataSet[i][0] * dataSet[i][1];
        }
        
        double ds = n * sumXX - sumX * sumX;
        double db1 = n * sumXY - sumX * sumY;
        double db0 = sumY * sumXX - sumXY * sumX;
        
        double b0 = db0 / ds; //La intercepción
        double b1 = db1 / ds; //La pendiente
        
        return new double[] {b0, b1};
    }
}