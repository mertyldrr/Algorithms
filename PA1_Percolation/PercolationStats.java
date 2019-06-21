import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats
{
    private double[] trialResults;  // stores threshold values
    public PercolationStats(int size, int trials)   // constructor runs runTrials method
    {
        if (size <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException();
        }        
        trialResults = new double[trials];
        runTrials(size, trials);
    }

    private void runTrials(int size, int trials)    // saves trials to result array
    {
        for (int i = 0; i < trials; i++) 
        {
            trialResults[i] = runTrial(size);
        }
    }

    private double runTrial(int size)
    {
        Percolation p = new Percolation(size);
        do {
            int row = getRandom(size); // choose a site uniformly at random among all blocked sites
            int col = getRandom(size);
            if (!p.isOpen(row, col))
            {
                p.open(row, col);      // open the site
            }           
        } while (!p.percolates());     // repeat this until the system percolates

        return (double) p.numberOfOpenSites() / ((double) size*size);   // returns threshold value
    }
    private int getRandom(int size)    // get a random number between [1, size+1)
    {
        return StdRandom.uniform(1, size + 1);
    }

    public double mean()    // calculate mean
    {
        return StdStats.mean(trialResults);
    }

    public double stddev()  // calculate standart deviation
    {
        return StdStats.stddev(trialResults);
    }

    private double confidence()     // calculate confidence interval
    {
        return (1.96 * stddev() / Math.sqrt(trialResults.length));
    }

    public double confidenceLo()    // returns low endpoint of 95% confidence interval
    {
        return mean() - confidence();
    }

    public double confidenceHi()    // returns high endpoint of 95% confidence interval
    {
        return mean() + confidence();
    }

    public static void main(String[] args) 
    {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}