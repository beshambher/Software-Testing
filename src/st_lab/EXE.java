/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st_lab;

import java.io.*;

/**
 *
 * @author bishu
 */
public class EXE {

    private File file;
    private Process process;
    private OutputStream stdin = null;
    private InputStream stderr = null;
    private InputStream stdout = null;
    private FileWriter F;

    public void setFile(File f) {
        file = f;
    }

    public void giveInput(String line) {
        try {
            //System.out.print(line);
            line = line.concat("\n");
            stdin.write(line.getBytes());
            stdin.flush();
        } catch (Exception ex) {
            System.out.println(ex + " in giveInput()");
        }
    }

    public String getOutput() {
        String s = "", line;
        try {
            //get output from stdout and stderr
            BufferedReader bro = new BufferedReader(new InputStreamReader(stdout));
            while ((line = bro.readLine()) != null) {
                //if (!line.contains("Enter")) {
                //System.out.println(line);
                F.write(line + "\n");
                F.flush();
                //}
            }

            bro.close();
            BufferedReader bre = new BufferedReader(new InputStreamReader(stderr));
            while ((line = bre.readLine()) != null) {
                //System.out.println(line);
                F.write(line + "\n");
                F.flush();
            }

            bre.close();
            F.close();

            FileReader f = new FileReader(System.getProperty("user.dir") + "//output.txt");
            BufferedReader br = new BufferedReader(f);
            while ((line = br.readLine()) != null) {
                s = line;
            }
            stdout.close();
            stderr.close();
            while (s.toLowerCase().contains("enter ")) {
                line = s.toLowerCase();
                s = s.replace(s.substring(line.indexOf("enter"), line.indexOf(":") + 1), "");
            }
            s = s.trim();

        } catch (Exception ex) {
            System.out.println(ex + " in getOutput()");
        }
        return s;
    }

    public void streamInit() {
        stdin = process.getOutputStream();
        stderr = process.getErrorStream();
        stdout = process.getInputStream();
    }

    public void processWait() throws InterruptedException {
        process.waitFor();
    }

    public void stdinClose() throws Exception {
        //stdin.flush();
        stdin.close();
    }

    public void init() {
        try {
            String line;
            int len;
            F = new FileWriter(System.getProperty("user.dir") + "//output.txt");
            //launch exe file
            line = file.getAbsolutePath();
            len = line.length();

            while (line.charAt(len - 1) != '.') {
                len--;
            }
            line = line.substring(0, len - 1) + ".exe";
            //System.out.println(line);

            process = Runtime.getRuntime().exec(line);
            streamInit();
            //write parameters into stdin
            /*line = "2\n";
             giveInput(line);
             line = "5\n";
             giveInput(line);
             line = "3\n";
             giveInput(line);
             stdin.close();*/

            //getOutput();
            //process.waitFor();
            //System.out.println("Process Terminated");
        } catch (Exception e) {
            System.out.println("ERROR!!! in EXE init() " + e);
        }
    }
}
