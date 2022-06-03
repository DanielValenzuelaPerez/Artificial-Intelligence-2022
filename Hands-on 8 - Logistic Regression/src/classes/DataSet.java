package classes;

public class DataSet {
    private double [][] data;
    private int rows;
    private int cols;
    
    public DataSet(){
        this.data = new double[0][0];
        this.rows = 0;
        this.cols = 0;
    }
    
    public DataSet(double [][] data){
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }
    
    public double[][] getData(){
        return data;
    }
    public DataSet getXs(){
        double [][] dataSetX = new double[this.rows][this.cols - 1];
        for(int i = 0; i < dataSetX.length; i++){
            for(int j = 0; j < dataSetX[0].length; j++){
                dataSetX[i][j] = this.data[i][j];
            }
        }
        return new DataSet(dataSetX);
    }
    public DataSet getYs(){
        double [][] dataSetY = new double[this.rows][1];
        for(int i = 0; i < dataSetY.length; i++){
            dataSetY[i][0] = this.data[i][this.cols - 1];
        }
        return new DataSet(dataSetY);
    }
    public void append1s(){
        double [][] newData = new double[this.rows][this.cols + 1];
        
        for(int i = 0; i < newData.length; i++)
            for(int j = 0; j < newData[0].length; j++){
                if(j == 0)
                    newData[i][j] = 1;
                else
                    newData[i][j] = this.getData()[i][j-1];
            }
        this.data = newData;
        this.cols = this.cols + 1;
    }
    public int getRows(){
        return rows;
    }
    
    public int getCols(){
        return cols;
    }
    
    public void print(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                System.out.print(this.data[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}