/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int openCount;

    private WeightedQuickUnionUF myUF;
    private int N;
    private boolean[] openStats;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            /*
             create an object myUF containing n^2 internal
             points plus two auxiliary points which help to
             check if the system percolates or not
            */
            openCount = 0;
            myUF = new WeightedQuickUnionUF(n * n + 2);

            N = n;
            openStats = new boolean[n * n];
            // stats[i-1] = true: i-th site is open
            // stats[i-1] = false: i-th site is blocked
            for (int i = 0; i < openStats.length; i++) openStats[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1) || (row > N) || (col < 1) || (col > N)) {
            throw new IllegalArgumentException();
        }
        else {
            int index = (row - 1) * N + col - 1;
            int indexU = (row - 2) * N + col - 1;
            int indexD = (row) * N + col - 1;
            int indexL = (row - 1) * N + col - 2;
            int indexR = (row - 1) * N + col;

            // if site(row, col) is not open already
            if (!openStats[index]) {
                openStats[index] = true; // open the site
                openCount++; // keep tracking the count

                // check upper site
                if ((row != 1) && openStats[indexU]) {
                    myUF.union(index, indexU);
                }
                else if (row == 1) myUF.union(index, N * N);


                // check lower site
                if ((row != N) && openStats[indexD]) {
                    myUF.union(index, indexD);
                }
                else if (row == N) myUF.union(index, N * N + 1);

                // check left  site
                if ((col != 1) && openStats[indexL]) {
                    myUF.union(index, indexL);
                }

                // check right site
                if ((col != N) && openStats[indexR]) {
                    myUF.union(index, indexR);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = (row - 1) * N + col - 1;
        return openStats[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = (row - 1) * N + col - 1;
        return myUF.find(index) == myUF.find(N * N);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return myUF.find(N * N) == myUF.find(N * N + 1);
    }

}
