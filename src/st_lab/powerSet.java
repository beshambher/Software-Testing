/*
 * @Project Title:  Software Testing Lab
 * @Submitted By:  Beshambher Chaukhwan (2K13/SE/024)
 * 
 */
package st_lab;

import static java.lang.Math.pow;

/**
 *
 * @author bishu
 */
public class powerSet {

    int p[][], i = 0;

    public void printAll() {
        for (int k = 0; k < i; k++) {
            for (int j = 1; j < p[k][0]; j++) {
                System.out.print(p[k][j] + " ");
            }
            System.out.println("");
        }
    }

    private void printSet(int a[], int size) {
        p[i][0] = size + 1;
        for (int j = 1; j <= size; j++) {
            p[i][j] = a[j];
            //System.out.print(a[j] + " ");
        }
        //System.out.println("");
        i += 1;
    }

    public void Powerset(int a, int n) {
        int k = (int) pow(2, (n - a + 1)) - 1;
        p = new int[k][n - a + 2];
        i = 0;
        k = n - a + 2;
        int stack[] = new int[k];
        stack[0] = a - 1; /* 0 is not considered as part of the set */

        k = 0;
        while (true) {
            if (stack[k] < n) {
                stack[k + 1] = stack[k] + 1;
                k++;
            } else {
                stack[k - 1]++;
                k--;
            }
            if (k == 0) {
                break;
            }
            printSet(stack, k);
        }
        System.out.println(" n = " + (n - a + 1) + " |PowerSet| = " + i);
        //printAll();
    }

    public static void main(String args[]) {
        try {
            powerSet P = new powerSet();
            for (int j = 1; j < 5; j++) {
                P.Powerset(1, j);
            }
        } catch (Exception e) {
            System.out.println("ERROR!!! in powerSet main() " + e);
        }
    }
}
