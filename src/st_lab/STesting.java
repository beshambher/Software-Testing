/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st_lab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.ImageIcon;

/**
 *
 * @author bishu
 */
public class STesting extends javax.swing.JFrame {

    private LEX t = new LEX();
    private print p = new print();
    private String s = "";

    /**
     * Creates new form FTesting
     */
    public STesting() {
        initComponents();
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icons\\test_icon.gif");
        setIconImage(icon.getImage());
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
        setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        head = new javax.swing.JLabel();
        source = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        code = new javax.swing.JTextPane();
        comp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        head.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        head.setText("Cyclomatic Complexity");

        source.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        source.setText("Source File: ");

        code.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jScrollPane2.setViewportView(code);
        code.getAccessibleContext().setAccessibleDescription("text/html");

        comp.setText("Cyclomatic Complexity = ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addComponent(head, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comp, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(head, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(comp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setHead(String s) {
        head.setText(s);
    }    
    public void setCode(String s) {
        code.setText(s);
    }    
    public void setComp(String s) {
        comp.setText(s);
    }
    public void setSource(String s) {
        source.setText(s);
    }

    public void setLex(LEX lex) {
        try {
            String S = "";
            int i = 2;
            t = lex;
            source.setText(source.getText() + t.getFile());
            BufferedReader bf = new BufferedReader(new FileReader(t.getFileType()));
            while (!(S = bf.readLine()).contains("main")) {
                s += ("0.  " + S + "\n");
            }
            s += ("1.  " + S + "\n");
            while ((S = bf.readLine()) != null) {
                s += (i + ".  " + S + "\n");
                i++;
            }
            code.setText(s);
            comp.setText(comp.getText() + t.getComplexity());
        } catch (Exception e) {
            System.err.println(e + " in setLex");
        }
    }

    public void setPaths() {
        int c = t.getComplexity();
        s = "";//code.getText();
        Paths P = new Paths(t.getMatrixSize());
        P.setMatrix(t.getMatrix());
        P.setCC(c);
        int path[][] = P.getPath();
        int m[][] = t.getMatrix(), n = t.getMatrixSize();
        s += "Graph Matrix:\n\n";
        for (int i = 1; i < n; i++) {
            s+="     ";
            for (int j = 1; j < n; j++) {
                s += (m[i][j] + "  ");
            }
            s += "\n";
        }
        s += "\nTotal Number of nodes = " + (n-1);

        s += "\n\nIndependent Paths:\n\n";
        for (int i = 0; i < c; i++) {
            s += ("      Path " + (i + 1) + ":  ");
            for (int j = 1; j < path[i][0]; j++) {
                s += (path[i][j] + "  ");
            }
            s += "\n";
        }
        code.setText(s);
    }

    /*public void boundaryTest(int min, int max) {
     head.setText("Boundary Value Analysis");
     int inputs[] = t.getInputs(), n = t.getInputNo(), i, j, k;
     String str[] = new String[n + 2], temp;
     str[0] = "S. No.";
     for (i = 1; i <= n; i++) {
     str[i] = "" + (char) inputs[i - 1];
     }
     str[n + 1] = "Expected Output";
     //System.out.println("inputs: ");
     //p.printMatrix(t.getInputs(), t.getInputNo());
     //data = new javax.swing.JTable(t.getInputNo()*4+1, t.getInputNo());

     int val[] = {min, min + 1, max - 1, max, (min + max) / 2};
     Object obj[][] = new Object[4 * n + 1][n + 2];
     k = (min + max) / 2;
     for (i = 0; i < 4 * n + 1; i++) {
     obj[i][0] = (i + 1) + ". ";
     for (j = 0; j < n; j++) {
     obj[i][j + 1] = "" + k;
     }
     obj[i][j + 1] = "Valid";
     }

     i = k = 0;
     while (i < 4 * n) {
     for (j = 0; j < 4; j++) {
     obj[i][k + 1] = "" + val[j];
     i++;
     }
     k++;
     }

     data.setModel(new javax.swing.table.DefaultTableModel(obj, str));
     cases.setText(cases.getText() + (4 * n + 1));
     }

     public void robustTest(int min, int max) {
     head.setText("Robustness Test Cases");
     int inputs[] = t.getInputs(), n = t.getInputNo(), i, j, k;
     String str[] = new String[n + 2], temp;
     str[0] = "S. No.";
     for (i = 1; i <= n; i++) {
     str[i] = "" + (char) inputs[i - 1];
     }
     str[n + 1] = "Expected Output";
     //System.out.println("inputs: ");
     //p.printMatrix(t.getInputs(), t.getInputNo());
     //data = new javax.swing.JTable(t.getInputNo()*4+1, t.getInputNo());

     int val[] = {min - 1, min, min + 1, max - 1, max, max + 1, (min + max) / 2};
     Object obj[][] = new Object[6 * n + 1][n + 2];
     k = (min + max) / 2;
     for (i = 0; i < 6 * n + 1; i++) {
     obj[i][0] = (i + 1) + ". ";
     for (j = 0; j < n; j++) {
     obj[i][j + 1] = "" + k;
     }
     if ((i % 6) > 0 && (i % 6) < 5) {
     obj[i][j + 1] = "Valid";
     } else {
     obj[i][j + 1] = "Invalid";
     }
     }

     i = k = 0;
     while (i < 6 * n) {
     for (j = 0; j < 6; j++) {
     obj[i][k + 1] = "" + val[j];
     i++;
     }
     k++;
     }

     data.setModel(new javax.swing.table.DefaultTableModel(obj, str));
     cases.setText(cases.getText() + (6 * n + 1));
     }

     public void worstTest(int min, int max) {
     head.setText("Worst-Case Test Cases");
     int inputs[] = t.getInputs(), n = t.getInputNo(), i, j, k;
     String str[] = new String[n + 2], temp;
     str[0] = "S. No.";
     for (i = 1; i <= n; i++) {
     str[i] = "" + (char) inputs[i - 1];
     }
     str[n + 1] = "Expected Output";
     //System.out.println("inputs: ");
     //p.printMatrix(t.getInputs(), t.getInputNo());
     //data = new javax.swing.JTable(t.getInputNo()*4+1, t.getInputNo());

     int val[] = {min, min + 1, max - 1, max, (min + max) / 2};

     System.out.println("Before str[][]");
     String s[][] = new String[n][5];
     for (i = 0; i < n; i++) {
     for (j = 0; j < 5; j++) {
     s[i][j] = "" + val[j];
     System.out.print(s[i][j] + " ");
     }
     System.out.println("");
     }

     Permute2DArray N = new Permute2DArray();
     List<String> lst = N.combinations(s);
     System.out.println("Total no. " + lst.size());
     String obj[][] = new String[lst.size()][];

     for (i = 0; i < lst.size(); i++) {
     obj[i] = ((i + 1) + ". " + lst.get(i).concat(" Valid")).split("\\s+");
     }
     data.setModel(new javax.swing.table.DefaultTableModel(obj, str));
     cases.setText(cases.getText() + (lst.size()));
     }

     public void robustworstTest(int min, int max) {
     head.setText("Robustness Worst-Case Test Cases");
     int inputs[] = t.getInputs(), n = t.getInputNo(), i, j, k;
     String str[] = new String[n + 2], temp;
     str[0] = "S. No.";
     for (i = 1; i <= n; i++) {
     str[i] = "" + (char) inputs[i - 1];
     }
     str[n + 1] = "Expected Output";
     //System.out.println("inputs: ");
     //p.printMatrix(t.getInputs(), t.getInputNo());
     //data = new javax.swing.JTable(t.getInputNo()*4+1, t.getInputNo());

     int val[] = {min - 1, min, min + 1, max - 1, max, max + 1, (min + max) / 2};

     System.out.println("Before str[][]");
     String s[][] = new String[n][7];
     for (i = 0; i < n; i++) {
     for (j = 0; j < 7; j++) {
     s[i][j] = "" + val[j];
     System.out.print(s[i][j] + " ");
     }
     System.out.println("");
     }

     Permute2DArray N = new Permute2DArray();
     List<String> lst = N.combinations(s);
     System.out.println("Total no. " + lst.size());
     String obj[][] = new String[lst.size()][];

     for (i = 0; i < lst.size(); i++) {
     if (lst.get(i).contains("" + (min - 1)) || lst.get(i).contains("" + (max + 1))) {
     obj[i] = ((i + 1) + ". " + lst.get(i).concat(" Invalid")).split("\\s+");
     } else {
     obj[i] = ((i + 1) + ". " + lst.get(i).concat(" Valid")).split("\\s+");
     }
     }
     data.setModel(new javax.swing.table.DefaultTableModel(obj, str));
     cases.setText(cases.getText() + (lst.size()));
     }*/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(STesting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(STesting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(STesting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(STesting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                STesting F = new STesting();
                F.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane code;
    private javax.swing.JLabel comp;
    private javax.swing.JLabel head;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel source;
    // End of variables declaration//GEN-END:variables
}
