package classes;

public class MatrixOperations {
    
    public static void print(double [][] matrix){
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                System.out.print(String.format("%.2f", matrix[i][j]) + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    public static double[][] transpose(double [][] matrix){
        int rows = matrix.length;
        int cols = matrix[0].length;
        double [][] newMatrix = new double[cols][rows];
        
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                newMatrix[i][j] = matrix[j][i];
            }
        }
        return newMatrix;
    }
        
    public static double[][] multiplication(double [][] matrixA, double [][] matrixB)
    {
        if(matrixA[0].length != matrixB.length){
            System.out.println("ERROR: Can't multiply these matrices");
            return null;
        }
        int rows = matrixA.length;
        int cols = matrixB[0].length;
        double [][] newMatrix = new double [rows][cols];
        
        //Multiplication A*B
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                for(int k = 0; k < matrixA[0].length; k++)
                {
                    newMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    //System.out.println("A:" + matrixA[i][k] + "\tB:" + matrixB[k][j]);
                }
        return newMatrix;
    }
    public static double[][] multiplication(double [][] matrix, double scalar){
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = matrix[i][j] * scalar;
        return matrix;
    }
    public static double determinant(double [][] matrix){
        int determinantResult = 0;
        //Must be a square matrix n*n
        if(matrix.length != matrix[0].length){
            System.out.println("ERROR: can't calculate determinant of uneven matrix");
            return 0;
        }
        
        if(matrix.length == 1) 
            return matrix[0][0];
        if(matrix.length == 2) //Recursive until it's 2*2
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        
        int newDimension = matrix.length - 1;
        double [][] C = new double[newDimension][newDimension];
        // This will run in case the matrix is larger than 2x2
        for(int i = 0; i < matrix[0].length; i++)
        {
            int newCurrentRow = 0;
            int newCurrentCol = 0;
            //System.out.print(matrix[0][i] + ":\t");
            for(int j = 1; j < matrix.length; j++)
                for(int k = 0; k < matrix[0].length; k++)
                    if(i != k)
                    {
                        //System.out.print(matrix[j][k] + " ");
                        if(newCurrentCol == newDimension)
                        {
                            newCurrentCol = 0;
                            newCurrentRow++;
                        }
                        C[newCurrentRow][newCurrentCol] = matrix[j][k];
                        //System.out.print("@" + newCurrentRow + ", " + newCurrentCol + "\t");
                        newCurrentCol++;
                    }
            if(i % 2 == 0) //+ - + - + -
                determinantResult += matrix[0][i] * determinant(C);
            else
                determinantResult += -matrix[0][i] * determinant(C);
        }
        return determinantResult;
    }
    //New Matrix N
    public static double [][] cofactorMatrix(double [][] matrix) // adjugateMatrix = transpose of cofactorMatrix
    {
        //Must be square nxn
        int rows = matrix.length;
        int cols = matrix[0].length;
        if(rows != cols)
        {
            System.out.println("ERROR: can't calculate cofactor matrix because it isn't a square matrix.");
            return null;
        }
        double [][] cofactorMatrix = new double [rows][cols];
        
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                double minorValues [][] = new double [rows - 1][cols - 1];
                int currentRow = 0;
                int currentCol = 0;
                //System.out.println(matrix[i][j] + ":");
                for(int k = 0; k < rows; k++)
                {
                    if(k == i) continue;
                    for(int l = 0; l < cols; l++)
                    {
                        if(l == j) continue;
                        //System.out.print(C[k][o] + "\t");
                        minorValues[currentRow][currentCol] = matrix[k][l];
                        currentCol++;
                        if(currentCol == minorValues[0].length)
                        {
                            currentCol = 0;
                            currentRow++;
                        }
                    }
                    //System.out.println();
                }
                cofactorMatrix[i][j] = Math.pow(-1, i+j) * determinant(minorValues);
                /*System.out.print("Minor values: " );
                for(int p = 0; p < minorValues.length; p++)
                {
                    for(int q = 0; q < minorValues[0].length; q++)
                    {
                        System.out.print(minorValues[p][q] + " ");
                    }
                }
                System.out.println();*/
            }
        }
        
        return cofactorMatrix;
    }
    public static double [][] inverse(double [][] matrix)
    {
        if(matrix.length == matrix[0].length) // nxn
            return multiplication(transpose(cofactorMatrix(matrix)), 1/determinant(matrix));
        else if(matrix.length < matrix[0].length) // nxm, where n < m (right inverse)
            return multiplication(transpose(matrix), inverse(multiplication(matrix, transpose(matrix))));
        else // nxm, where n > m (left inverse)
            return multiplication(inverse(multiplication(transpose(matrix), matrix)), transpose(matrix));
    }
}