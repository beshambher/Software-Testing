/**
 * @Project Title: Software Testing Lab
 *
 * @Submitted By: Beshambher Chaukhwan (2K13/SE/024)
 */
package st_lab;

import java.io.*;

/**
 *
 * @author wmc
 */
public class CEGraph {

    private File sfile;
    private int min, max;

    public void setSourceFile(File f) {
        try {
            sfile = f;
        } catch (Exception e) {
            System.out.println("Error! in CEGraph setSourceFile() " + e);
        }
    }

    public void setMinMax(String mn, String mx) {
        min = toInt(mn);
        max = toInt(mx);
    }

    public void mainLex() {
        try {
            DTable D = new DTable();
            D.mark = 0;
            D.setSourceFile(sfile);
            D.setMinMax(min + "", max + "");
            D.mainLex();

            String ot[][] = D.getOt();
            String hd[] = D.gethd();
            Object obt[][] = new Object[D.fex][D.n];
            int k = 0;
            for (int i = 0; i < ot.length; i++) {
                if (ot[i][0] == null) {
                    break;
                } else if (!ot[i][0].toLowerCase().contains("out of range")) {
                    obt[k][0] = (k+1) + ".";
                    String temp[] = ot[i][1].split("\\s+");
                    int j = 1;
                    for (String t1 : temp) {
                        obt[k][j] = t1;
                        j++;
                    }
                    obt[k][j] = ot[i][0];
                    k++;
                }
            }
            FTesting TC = new FTesting();
            TC.setHead("Cause-Effect Graphing");
            TC.setCases(k);
            TC.setSource(sfile.getName());
            TC.setTable(obt, hd);
            TC.setColumnWidth(D.b + 1, 200);
            TC.setVisible(true);

            CEGPane CP = new CEGPane();
            CP.setLO(D.l, D.o, D.range);
            CP.fname = sfile.getName();
            CP.setData(D.getObj(), D.getOtInput(), D.getOt());
            CEG C = new CEG(CP);
            C.setVisible(true);
        } catch (Exception e) {
            System.err.println("ERROR!!! in CEGraph mainLex() " + e);
        }
    }

    private int toInt(String s) {
        int i = 0, j = 0;
        if (s.charAt(0) == '-') {
            j = 1;
        }
        for (; j < s.length(); j++) {
            i = (i * 10) + (s.charAt(j) - '0');
        }
        if (s.charAt(0) == '-') {
            i = -i;
        }
        return i;
    }

}
