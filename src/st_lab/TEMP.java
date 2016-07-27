/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st_lab;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.swing.*;

/**
 *
 * @author wmc
 */
public class TEMP {

    public TEMP() {
        Thread T = new Thread() {
            public void run() {
                init();
            }
        };
        T.run();
    }

    private static void init() {
        JFrame f = new JFrame("Loading");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\icons\\test_icon.gif");
        f.setIconImage(icon.getImage());
        f.setSize(400, 200);
        Toolkit toolkit = f.getToolkit();
        Dimension size = toolkit.getScreenSize();
        f.setLocation(size.width / 2 - f.getWidth() / 2, size.height / 2 - f.getHeight() / 2);
        ImageIcon load = new ImageIcon(System.getProperty("user.dir") + "\\icons\\ajax-loader.gif");
        f.add(new JLabel("loading...", load, JLabel.CENTER));
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        /*ScriptEngineManager mgr = new ScriptEngineManager();
         ScriptEngine engine = mgr.getEngineByName("JavaScript");
         int a = 2;
         String foo = "a=0; b=a+2; (a==2) || (b==0)";
         System.out.println(foo + ": " + engine.eval(foo));
         String S[] = "a>=0 && b>c".split("[^a-z^A-Z]");
         for (String s : S) {
         System.out.println(s + " " + s.length());
         }*/
        init();
    }
}
