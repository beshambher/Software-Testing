/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st_lab;

/**
 *
 * @author bishu
 */
public class Paths {

    private int F[][], V[], path[][], foo[][];
    private double T[][], N[][], P[][];
    private int cc, n;

    public Paths(int n) {
        N = new double[n][n];
        T = new double[n][n];
        P = new double[n][n];
        foo = new int[n][n];
        V = new int[n];
        this.n = n;
        for (int i = 0; i < n; i++) {
            V[i] = 0;
            for (int j = 0; j < n; j++) {
                N[i][j] = 2.0;
                T[i][j] = 1.0;
                P[i][j] = 0.0;
            }
        }
    }

    public void setMatrix(int m[][]) {
        F = m;
    }
    
    public void setCC(int c) {
        cc = c;
    }
    
    public int[][] getPath() {
        int i, j, k, l, st, c, pi, pj, flag;
        double sum, max, z;
        int f[] = new int[n], x, y, w;
        path = new int[cc][2 * n];
        print p = new print();
        //p.printString("Adjacency Matrix by getPath(): F");
        //p.printMatrix(F, n);
        c = cc;
        i = 1;
        j = pi = 0;
        pj = 1;
        while (c > 0) {
            st = path[pi][pj++] = i;
            sum = 0.0;
            while (st < n - 1) {
                z = 0.0;
                l = 0;
                for (k = 1; k < n; k++) {
                    if (F[i][k] == 1 && foo[i][k] < 10) {
                        z += (T[i][k] / N[i][k]);
                        f[l++] = k;
                    }
                }
                for (k = 0; k < l; k++) {
                    w = f[k];
                    P[i][w] = (T[i][w] / (N[i][w] * z));
                }
                if (l == 1) {
                    j = f[0];
                } else if (l > 1) {
                    max = 0.0;
                    x = y = 0;
                    for (k = 0; k < l; k++) {
                        y = f[k];
                        if (P[i][y] > max) {
                            max = P[i][y];
                        }
                    }
                    for (k = 0; k < l; k++) {
                        w = f[k];
                        if (max == P[i][w]) {
                            x++;
                            y = w;
                        }
                    }
                    if (x == 1) {
                        j = y;
                    } else {
                        j = 0;
                        for (k = 0; k < l; k++) {
                            w = f[k];
                            if (max == P[i][w] && w == n - 1) {
                                j = f[k];
                                break;
                            }
                        }
                        if (j == 0) {
                            for (k = 0; k < l; k++) {
                                w = f[k];
                                if (max == P[i][w] && V[w] == 0) {
                                    j = f[k];
                                    break;
                                }
                            }
                        }
                        if (j == 0) {
                            j = y;
                        }
                    }
                } else {
                    j = i + 1;
                }
                V[i] = 1;
                path[pi][pj++] = j;
                T[i][j] = T[i][j] + (1.0 / N[i][j]);
                N[i][j] *= 2.0;
                sum += T[i][j];
                foo[i][j]++;
                i = j;
                st = j;
            }
            flag = 1;
            for (k = 0; k < pi; k++) {
                j = 0;
                for (l = 0; l < path[k][0]; l++) {
                    if (path[pi][l] == path[k][l]) {
                        j++;
                    }
                }
                if (path[k][0] == pj && j == pj - 1) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                path[pi][0] = pj;
                pi++;
                c--;
                foo = new int[n][n];
            }
            pj = 1;
            i = 1;
        } //end while (c>0)
        return path;
    }

}
