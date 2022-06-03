package classes;


public class DataSet {
    private double [][] dataSet; 
    private int rows;
    private int cols;
    
    public DataSet(){
        this.dataSet = new double[0][0];
        this.rows = 0;
        this.cols = 0;
    }
    
    public DataSet(double [][] dataSet){
        this.dataSet = dataSet;
        this.rows = dataSet.length;
        this.cols = dataSet[0].length;
    }
    
    public void setDataSet(double[][] dataSet){
        this.dataSet = dataSet;
        this.rows = dataSet.length;
        this.cols = dataSet[0].length;
    }
    
    public double[][] getDataSet(){
        return dataSet;
    }
    
    public DataSet getDataSetX(){
        double [][] dataSetX = new double[this.rows][this.cols];
        for(int i = 0; i < dataSetX.length; i++){
            for(int j = 0; j < dataSetX[0].length; j++){
                dataSetX[i][j] = j == 0 ? 1 : this.dataSet[i][j-1];
            }
        }
        return new DataSet(dataSetX);
    }
    
    public DataSet getDataSetY(){
        double [][] dataSetY = new double[this.rows][1];
        for(int i = 0; i < dataSetY.length; i++){
            dataSetY[i][0] = this.dataSet[i][this.cols - 1];
        }
        return new DataSet(dataSetY);
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getCols(){
        return cols;
    }
    
    public void printDataSet(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                System.out.print(this.dataSet[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}