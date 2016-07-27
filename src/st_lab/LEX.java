/**
 * @Project Title: Software Testing Lab
 *
 * @Submitted By: Beshambher Chaukhwan (2K13/SE/024)
 *
 * @Inputs: Source files(C/C++).
 *
 * @Outputs: Adjacency Graph Matrix.
 *
 */
package st_lab;

import java.io.*;
import java.util.*;

/**
 * Main class: This class is the controller class of the project
 */
public class LEX {

    private int input[] = new int[150], b, fex = 0, line = 0;
    private int M[][] = new int[100][100], n = 0, cc = 0;
    private int varType[] = new int[150];
    private File sfile = null;
    private String sp[];

    /**
     * Function LEX(): This is the constructor of main class.
     */
    public LEX() {
        try {
            M[0][1] = 1;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Function getComplexity() It is used to return the cyclomatic complexity
     * of CFG by using the formula: CC = E - N + 2P
     *
     * @return
     */
    public int[] getInputs() {
        return input;
    }

    public String getFile() {
        return sfile.getName();
    }

    public File getFileType() {
        return sfile;
    }

    public int getInputNo() {
        return b;
    }

    public int getComplexity() {
        return cc;
    }

    public int[][] getMatrix() {
        return M;
    }

    public int getMatrixSize() {
        return line;
    }

    public void setSourceFile(File f) {
        try {
            sfile = f;
        } catch (Exception e) {
            System.out.println("Error! in setSourceFile " + e);
        }
    }

    public void mainLex() {
        try {
            //Open file frame
            File s = sfile;
            int a = 0;
            String file = s.getPath();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            /**
             * Checking the extention of file by default we are processing C++
             * files This will detect .c extention and inform the program that
             * the input file is a C file.
             */
            if (s.getName().charAt((s.getName().length() - 1)) == 'c') {
                fex = 1;
            }

            /**
             * getCFG() function to generate CFG Matrix.
             */
            getCFG(br);

        } catch (Exception e) {
            System.err.println("ERROR!!! in main() " + e);
        }
    }

    public int getVarType(char s) {
        for(int i=0; i<sp.length; i++) {
            if(sp[i].charAt(0)==s) {
                return varType[i];
            }
        }
        return 0;
    }
    
    /**
     * Function getCFG():
     *
     * @param f This function scan the source code of selected file generate the
     * adjacency matrix of its CFG by reading the source code line by line.
     */
    private void getCFG(BufferedReader f) {
        String s;
        Stack st = new Stack();
        int i, j, len;
        try {
            while (st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                if (len > 0) {
                    if (s.charAt(0) == '{' || s.charAt(len - 1) == '{') {
                        line++;
                        M[line][line + 1] = 1;
                        st.add(line);
                        st.add('m');
                    } else if (s.charAt(0) != '#' && !s.contains("namespace")) {
                        line++;
                        M[line][line + 1] = 1;
                    }
                }
            }
            while (!st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                line++;
                i = 0;
                if (len > 0 && s.charAt(0) != '/') {
                    if (len > 4 && s.contains("int")) {
                        sp = s.substring(4, len - 1).split(",\\s*");
                        for (int o = 0; o < sp.length; o++) {
                            varType[o] = 0;
                            //System.out.println(sp[o]);
                        }
                    } else if (len > 5 && s.contains("char")) {
                        sp = s.substring(5, len - 1).split(",\\s*");
                        for (int o = 0; o < sp.length; o++) {
                            varType[o] = 1;
                            //System.out.println(sp[o]);
                        }
                    }
                    if (fex == 0) {
                        if (len > 3) {
                            if (s.contains("cin>")) {
                                j = s.length();
                                for (i += 4; i < j; i++) {
                                    if ((s.charAt(i) > 96 && s.charAt(i) < 123) || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                                        input[b++] = s.charAt(i);                                        
                                    }
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                            }
                        }
                    } else if (s.charAt(i) == 's') {
                        if (len > 5) {
                            if (s.contains("scanf")) {
                                j = s.length();
                                for (i += 5; i < j; i++) {
                                    if ((s.charAt(i) == '&')) {
                                        input[b++] = s.charAt(i + 1);
                                    }
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                            }
                        }
                    }
                    if (s.charAt(0) == '/') {
                        line--;
                        continue;
                    }
                    i = 0;
                    if ((len > 6) && s.contains("switch")) {
                        if (s.charAt(len - 1) != '{') {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                        }
                        j = line;
                        st.add('s');
                        while (s.charAt(0) != '}' || s.charAt(len - 1) != '}') {
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                            if (s.contains("case") || s.contains("default")) {
                                M[j][line] = 1;
                            }
                            if (s.contains("break")) {
                                st.add(line);
                            } else {
                                M[line][line + 1] = 1;
                            }
                        }
                        while (!st.lastElement().toString().equals("s")) {
                            j = toInt(st.pop().toString());
                            M[j][line] = 1;
                        }
                        st.pop();
                    } else if ((len > 1) && s.contains("do")) {
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('d');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('d');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }
                    } else if ((len > 3) && s.contains("for")) {
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('f');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('f');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }
                    } else if ((len > 5) && s.contains("while") && s.charAt(len - 1) != ';') {
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('w');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('w');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }
                    } else if ((len > 6) && s.contains("else if")) {
                        if (s.charAt(0) == '}') {
                            funcBrace(st, f, s);
                        }
                        st.add("ei");
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('i');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }
                    } else if ((len > 2) && s.contains("if")) {
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('i');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }
                    } else if ((len > 3) && s.contains("else")) {
                        if (s.charAt(0) == '}') {
                            funcBrace(st, f, s);
                        }
                        if (s.charAt(len - 1) == '{') {
                            st.add('e');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('e');
                            }
                        }
                    } else if ((len > 0) && (s.charAt(0) == '}' || s.charAt(len - 1) == '}')) {
                        if (st.lastElement().toString().equals("m")) {
                            st.removeAllElements();
                            M[line++][line] = 1;
                            M[line++][line] = 1;
                        } else if (st.lastElement().toString().charAt(0) == 'e') {
                            st.pop();
                            j = toInt(st.pop().toString());
                            M[j][line + 1] = 1;
                            M[line][line + 1] = 1;
                            while (st.lastElement().toString().equals("ei")) {
                                st.pop();
                                j = toInt(st.pop().toString());
                                M[j][line + 1] = 1;
                            }
                        } else if (st.lastElement().toString().charAt(0) == 'i') {
                            st.pop();
                            j = toInt(st.pop().toString());
                            M[j][line + 1] = 1;
                            if ((len > 3) && s.contains("else")) {
                                st.add(line);
                            } else {
                                f.mark(line);
                                s = f.readLine().trim();
                                len = s.length();
                                if ((len > 3) && (s.contains("else"))) {
                                    st.add(line);
                                } else {
                                    M[line][line + 1] = 1;
                                }
                                f.reset();
                            }
                        } else if (st.lastElement().toString().charAt(0) == 'w'
                                || st.lastElement().toString().charAt(0) == 'f') {
                            st.pop();
                            j = toInt(st.pop().toString());
                            M[j][line + 1] = 1;
                            M[line][j] = 1;
                        } else if (st.lastElement().toString().charAt(0) == 'd') {
                            st.pop();
                            j = toInt(st.pop().toString());
                            M[line][j] = 1;
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                        }
                    } else if ((len > 0) && (s.charAt(i) == '{' || s.charAt(len - 1) == '{')) {
                        M[line][line + 1] = 1;
                    } else {
                        M[line][line + 1] = 1;
                    }
                } else {
                    M[line][line + 1] = 1;
                }
            }
            line--;
            n = line;

            int e = 0;
            for (i = 1; i < n; i++) {
                for (j = 1; j < n; j++) {
                    e += M[i][j];
                }
            }
            cc = e - (n - 1) + 2;
        } catch (Exception e) {
            System.err.println("in getCFG() " + e);
        }
    }

    private void funcBrace(Stack st, BufferedReader f, String s) throws Exception {
        int j, len = s.length();
        if (st.lastElement().toString().equals("m")) {
            st.removeAllElements();
            M[line++][line] = 1;
            M[line++][line] = 1;
        } else if (st.lastElement().toString().charAt(0) == 'e') {
            st.pop();
            j = toInt(st.pop().toString());
            M[j][line] = 1;
            M[line][line + 1] = 1;
            while (st.lastElement().toString().equals("ei")) {
                st.pop();
                j = toInt(st.pop().toString());
                M[j][line + 1] = 1;
            }
        } else if (st.lastElement().toString().charAt(0) == 'i') {
            st.pop();
            j = toInt(st.pop().toString());
            M[j][line] = 1;
            M[line][line + 1] = 1;
            if ((len > 3) && s.contains("else")) {
                st.add(line);
            } else {
                f.mark(line);
                s = f.readLine().trim();
                len = s.length();
                if ((len > 3) && (s.contains("else"))) {
                    st.add(line);
                } else {
                    M[line][line + 1] = 1;
                }
                f.reset();
            }
        } else {
            M[line][line + 1] = 1;
        }
    }

    private void p(String s) {
        System.out.println(line + ".\t" + s);
    }

    public int toInt(String s) {
        int i = 0, j = 0;
        if (s.charAt(0) == '-') {
            j = 1;
        }
        for (; j < s.length(); j++) {
            i = (i * 10) + (s.charAt(j) - '0');
        }
        if(s.charAt(0)=='-') {
            i=-i;
        }
        return i;
    }
}
/**
 * END OF CLASS.
 */
