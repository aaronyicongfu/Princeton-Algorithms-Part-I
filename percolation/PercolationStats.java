/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] percoProbs;
    private int trails;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        percoProbs = new double[trials];
        this.trails = trials;

        for (int trial = 0; trial < trials; trial++) {

            // create the problem object
            Percolation problem = new Percolation(n);

            // create a list to store the orders
            int[] orders = new int[n * n];
            int x, y;

            // initialize, orders[] = 1 ~ n * n
            for (int i = 0; i < n * n; i++) {
                orders[i] = i + 1;
            }

            // Shuffle orders in a uniform fashion
            StdRandom.shuffle(orders);

            // generate coordinate pairs based on reordered orders[]
            for (int i = 0; i < n * n; i++) {

                x = (orders[i] - 1) % n + 1;
                y = (orders[i] - 1) / n + 1;
                problem.open(x, y);
                if (problem.percolates()) {
                    percoProbs[trial] = (double) (i + 1) / n / n;
                    break;
                }
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percoProbs);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percoProbs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(trails);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(trails);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, T);
        System.out.printf("%-24s = %.16f\n", "mean", p.mean());
        System.out.printf("%-24s = %.16f\n", "stddev", p.stddev());
        System.out.printf("%-24s = [%.16f, %.16f]\n", "95% confidence interval",
                          p.confidenceLo(), p.confidenceHi());
    }

}
