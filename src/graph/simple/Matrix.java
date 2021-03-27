package graph.simple;

/**
 * Represents a matrix and some useful methods given the context
 * @author CreeperStone72
 */
public class Matrix {
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Attributes /////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Number of rows in the matrix
     */
    private int rows;

    /**
     * Number of columns in the matrix
     */
    private int cols;

    /**
     * Values stored in the matrix
     */
    private final int[][] values;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors ///////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Constructor for a square matrix
     * @param size is the number of rows and columns
     */
    public Matrix(int size) { this(size, size); }

    /**
     * Main constructor
     * @param rows is the number of rows
     * @param cols is the number of columns
     */
    public Matrix(int rows, int cols) {
        setRows(rows);
        setCols(cols);
        values = new int[getRows()][getCols()];
        init();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Setters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void setRows(int rows) { this.rows = rows; }

    public void setCols(int cols) { this.cols = cols; }

    /**
     * Initializes to a zero matrix
     */
    private void init() {
        for (int row = 0 ; row < getRows() ; row++)
            for (int col = 0 ; col < getCols() ; col++)
                setValue(row, col, 0);
    }

    public void setValue(int row, int col, int value) { getValues()[row][col] = value; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Getters ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public int getRows() { return rows; }

    public int getCols() { return cols; }

    private int[][] getValues() { return values; }

    public int getValue(int row, int col) { return getValues()[row][col]; }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Methods ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Is the matrix square ?
     * @return true if the number of rows and columns are the same, otherwise false
     */
    public boolean isSquare() { return rows == cols; }

    /**
     * Is the matrix upper triangular ?
     * @return true if all values beneath the diagonal are equal to 0, otherwise false
     */
    public boolean isUpper() {
        for (int row = 0 ; row < getRows() ; row++)
            for (int col = 0; col < row; col++)
                if (getValue(row, col) != 0)
                    return false;

        return true;
    }

    /**
     * Is the matrix lower triangular ?
     * @return true if all values above the diagonal are equal to 0, otherwise false
     */
    public boolean isLower() {
        for (int row = 0 ; row < getRows() ; row++)
            for (int col = row; col < getCols(); col++)
                if (getValue(row, col) != 0)
                    return false;

        return true;
    }

    /**
     * Is the matrix diagonal ?
     * @return true if all values that aren't on the diagonal equal to 0, otherwise false
     */
    public boolean isDiagonal() { return isUpper() && isLower(); }

    /**
     * Is the matrix symmetrical ?
     * @return true if the matrix accepts its diagonal as an axis of symmetry, otherwise false
     */
    public boolean isSymmetrical() {
        for (int row = 0 ; row < getRows() ; row++)
            for (int col = row ; col < getCols() ; col++)
                if (getValue(row, col) != getValue(col, row))
                    return false;

        return true;
    }
    
    //////////////////////////////////////////////////////////////////////
    //// Remarkable matrices /////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    /**
     * Builds an identity matrix
     * @param n is the number of rows and columns
     * @return a square matrix where all values equals to 0, except on the diagonal where it's equal to 1
     */
    public static Matrix identity(int n) {
        Matrix identity = new Matrix(n);

        for (int i = 0 ; i < n ; i++)
            identity.setValue(i, i, 1);

        return identity;
    }

    /**
     * Builds the transpose of a matrix
     * @param m is the base matrix
     * @return a matrix that is equal to the transposition of m
     */
    public static Matrix transpose(Matrix m) {
        Matrix transpose = new Matrix(m.getCols(), m.getRows());

        for (int row = 0 ; row < m.getRows() ; row++)
            for (int col = 0 ; col < m.getCols() ; col++)
                transpose.setValue(col, row, m.getValue(row, col));

        return transpose;
    }
}
