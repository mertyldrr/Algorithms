import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] grid;
    private int openSiteCount;
    private final int virtualSource;
    private final int virtualSink;
    private final int gridSize;
    private final WeightedQuickUnionUF wqu;   // weighted quick union object reference
    private final WeightedQuickUnionUF backwash;  // for backwash bug

    public Percolation(int n)
    {
        if (n <= 0) throw new IllegalArgumentException();
        int numberOfNodes = n*n + 2;    // two extra site for virtualSource and virtualSink
        grid = new boolean[n+1][n+1];   // row, column indices are starting from 1, so we need n+1.
        gridSize = n;   // n+1 - 1 = n 
        wqu = new WeightedQuickUnionUF(numberOfNodes);
        backwash = new WeightedQuickUnionUF(numberOfNodes);     
        openSiteCount = 0;
        virtualSource = 0;  // we are using indices 1 to n, 0 is empty. that is our virualSource
        virtualSink = (n*n) + 1; // we are using indices 1 to n, (n*n)+1 is empty. that is our virualSource
    }   

    public void open(int row, int col)  // connect nodes with neighbour open sites
    {
        validateIndices(row, col);
        openSite(row, col);
        connectWithVirtualSource(row, col);
        connectWithNeighbours(row, col);
        connectWithVirtualSink(row, col);
    }

    private void openSite(int row, int col) // open site, if it is not open already
    {
        if (!isOpen(row, col))
        {
            grid[row][col] = true;
            openSiteCount++; 
        } 
    }

    private void union(int row, int col)  // union nodes seperately in each union-find object
    {
        wqu.union(row, col);
        backwash.union(row, col);
    }

    private int xyTo1D(int row, int col)  // map from a 2-dimensional (row, column) pair to a 1-dimensional union find object index
    {
        return ((row-1) * gridSize + col); // row and col cannot be smaller than 1, this will produce numbers 1 to n
    }

    public boolean isOpen(int row, int col) // is site open 
    {
        validateIndices(row, col);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) // is site full
    {
        validateIndices(row, col);
        return wqu.connected(xyTo1D(row, col), virtualSource);
    }

    public int numberOfOpenSites()
    {
        return openSiteCount;
    }

    public boolean percolates() // does the system percolate ?
    {
        // use backwash for this, because we used backwash object for connecting with virtualSink
        return backwash.connected(virtualSink, virtualSource);
    }

    private void validateIndices(int row, int col)
    {
        if (row < 1 || col < 1 || row > grid.length || col > grid.length)
        {
            throw new IllegalArgumentException();
        }    
    }

    private void connectWithVirtualSource(int row, int col)
    {
        if (row == 1)
        {
            union(virtualSource, xyTo1D(row, col));
        }      
    }

    private void connectWithVirtualSink(int row, int col)
    {
        if (row == gridSize)
        {
            backwash.union(virtualSink, xyTo1D(row, col));
        }
    }

    private void connectWithTopNode(int row, int col)
    {
        if (row > 1 && isOpen(row - 1, col))
        {
            union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
    }

    private void connectWithLeftNode(int row, int col)
    {
        if (col > 1 && isOpen(row, col - 1))
        {
            union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }        
    }

    private void connectWithRightNode(int row, int col)
    {
        if (col < gridSize && isOpen(row, col + 1))
        {
            union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }    
    }

    private void connectWithBottomNode(int row, int col)
    {
        if (row < gridSize && isOpen(row + 1, col))
        {
            union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }    
    }

    private void connectWithNeighbours(int row, int col)  // connect with open neighbour nodes
    {
        connectWithTopNode(row, col);
        connectWithLeftNode(row, col);
        connectWithRightNode(row, col);
        connectWithBottomNode(row, col);
    }
}

