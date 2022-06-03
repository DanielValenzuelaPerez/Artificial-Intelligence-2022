package classes;

public class Matrix {
    private int rows;
    private int cols;
    private double [][] data;
    
    Matrix(double [][] data){
        this.data = data;
        rows = data.length;
        cols = data[0].length;
    }
    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double [rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                data[i][j] = 0;
    }
    public void randomize(){
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                data[i][j] = Math.random();
    }
    public static Matrix multiply(Matrix matrixA, Matrix matrixB){
        if(matrixA.getCols() != matrixB.getRows()){
            System.out.println("ERROR: Can't multiply these matrices");
            return null;
        }
        int rows = matrixA.getRows();
        int cols = matrixB.getCols();
        double [][] newMatrix = new double[rows][cols];
        
        //Multiplication A*B
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                for(int k = 0; k < matrixA.getCols(); k++){
                    newMatrix[i][j] += matrixA.getData()[i][k] * matrixB.getData()[k][j];
                    //System.out.println("A:" + matrixA[i][k] + "\tB:" + matrixB[k][j]);
                }
        return new Matrix(newMatrix);
    }
    public void multiply(double scalar){
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                data[i][j] = data[i][j] * scalar;
    }
    public void print(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++)
                System.out.print(String.format("%.4f", data[i][j]) + " ");
            System.out.println();
        }
    }
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }
    public double[][] getData(){
        return data;
    }
    public void setData(double[][] data){
        this.data = data;
        rows = data.length;
        cols = data[0].length;
    }
    public void setDataPoint(int row, int col, double value){
        if(row >= rows || col >= cols){
            System.out.println("Can't set data point: out of bounds");
            return;
        }
        data[row][col] = value;
    }
    public static Matrix arrayToMatrix(double [] array){
        Matrix m = new Matrix(array.length, 1);
        for(int i = 0; i < array.length; i++)
            m.data[i][0] = array[i];
        return m;
    }
    public double[] matrixToArray(){
        double [] array = new double [cols * rows];
        int k = 0;
        for(int i= 0; i < rows; i++)
            for(int j = 0; j < cols; j++){
                array[k] = data[i][j];
                k++;
            }
        return array;
    }
    public void add(Matrix m){
        if(this.rows != m.getRows() || this.cols != m.getCols()){
            System.out.println("Error: to add matrix both must have the same dimensions");
            return;
        }
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.getData()[i][j] += m.data[i][j];
    }
    public void subtract(Matrix m){
        if(this.rows != m.getRows() || this.cols != m.getCols()){
            System.out.println("Error: to add matrix both must have the same dimensions");
            return;
        }
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                this.getData()[i][j] -= m.data[i][j];
    }
    public static Matrix transpose(Matrix matrix){
        int rows = matrix.getRows();
        int cols = matrix.getCols();
        double [][] newMatrix = new double[cols][rows];
        
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                newMatrix[i][j] = matrix.getData()[j][i];
            }
        }
        return new Matrix(newMatrix);
    }
}
