/**
 * @Project Title: Software Testing Lab
 *
 * @Submitted By: Beshambher Chaukhwan (2K13/SE/024)
 */
package st_lab;

import java.io.*;
import static java.lang.Math.pow;
import java.util.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

/**
 * Main class: This class is the controller class of the project
 */
public class DTable {

    /**
     * Class C: The objects of this class store the information about the
     * decision nodes which is used to identify which type of decision the node
     * is taking on which set of variables.
     */
    public class C {

        int v[] = new int[50], val, k;
        String s;

        C() {
            val = 0;
            k = 0;
            s = "";
            for (int i = 0; i < 50; i++) {
                v[i] = -1;
            }
        }
    }

    private C func[] = new C[200];
    private int M[][] = new int[100][100], min, max;
    int l = 0, n = 0, b = 0, cc = 0, fex = 0, o = 0, range = 0, mark = 1;
    private int varType[] = new int[150];
    char input[] = new char[150];
    private String sp[], hd[];
    private File sfile;

    public DTable() {
        try {
            for (int i = 0; i < 200; i++) {
                func[i] = new C();
            }
            M[0][1] = 1;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setSourceFile(File f) {
        try {
            sfile = f;
        } catch (Exception e) {
            System.out.println("Error! in DTable setSourceFile() " + e);
        }
    }

    public String[] gethd() {
        return hd;
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

            setTable(testCasesWorst());

        } catch (Exception e) {
            System.err.println("ERROR!!! in DTable mainLex() " + e);
        }
    }

    public int getVarType(char s) {
        for (int i = 0; i < sp.length; i++) {
            if (sp[i].charAt(0) == s) {
                return varType[i];
            }
        }
        return 0;
    }

    /**
     * Function getComplexity() It is used to calculate the cyclomatic
     * complexity of CFG by using the formula: CC = E - N + 2P
     */
    int getComplexity() {
        int i, j, e = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                e += M[i][j];
            }
        }
        cc = e - n + 2;
        return cc;
    }

    /**
     * Function getCFG():
     *
     * @param f This function scan the source code of selected file generate the
     * adjacency matrix of its CFG by reading the source code line by line.
     */
    public void getCFG(BufferedReader f) {
        String s;
        Stack st = new Stack();
        int i, j, len;
        try {
            //System.out.println("Line in beginning: " + line);
            while (st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                //line++;
                //p(" getCFG " + s + st.toString());
                if (len > 0) {
                    if (s.charAt(0) == '{' || s.charAt(len - 1) == '{') {
                        line++;
                        M[line][line + 1] = 1;
                        st.add(line);
                        st.add('m');
                    } else if (s.charAt(0) != '#' && !s.contains("namespace")) {
                        line++;
                        M[line][line + 1] = 1;
                        //line++;
                    } //not supposed to be here
                }
                //p(s);
                //M[line][line + 1] = 1;
            }
            //System.out.println("Line before while(!st.empty): " + line);

            while (!st.empty()) {
                s = f.readLine().trim();
                len = s.length();
                line++;
                i = 0;
                //p(s);
                //p(s + " st: " + st.toString());
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
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 4; i < j; i++) {
                                    if ((s.charAt(i) > 96 && s.charAt(i) < 123) || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z')) {
                                        input[b++] = s.charAt(i);
                                    } //System.out.println(i+" "+j);
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                                //p(s);
                            } //System.out.println("Here in cpp");
                        }

                    } else if (s.charAt(i) == 's') { //System.out.println("Here in c");
                        if (len > 5) {
                            if (s.contains("scanf")) {
                                j = s.length(); //System.out.println(s+j);               
                                for (i += 5; i < j; i++) {
                                    if ((s.charAt(i) == '&')) {
                                        input[b++] = s.charAt(i + 1); //System.out.println(b+" - "+input[b-1]);
                                    } //System.out.println(i+" "+j);
                                }
                                M[line][line + 1] = 1;
                                s = f.readLine().trim();
                                len = s.length();
                                line++;
                                //p(s);
                            }
                        }
                    }

                    if (s.charAt(0) == '/') {
                        line--;
                        continue;
                    }
                    cout(s);
                    getStubs(s);

                    i = 0;
                    if ((len > 6) && s.contains("switch")) {
                        // System.out.println("in switch-case");
                        if (s.charAt(len - 1) != '{') {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                            //p(s + " st: " + st.toString());
                        }
                        j = line;
                        st.add('s');
                        while (s.charAt(0) != '}' || s.charAt(len - 1) != '}') {
                            s = f.readLine().trim();
                            len = s.length();
                            line++;
                            cout(s);
                            getStubs(s);
                            //p(s + " st: " + st.toString());
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
                        //p(" st: " + st.toString());
                    } else if ((len > 1) && s.contains("do")) {
                        // System.out.println("in do()");
                        st.add(line);
                        //p("Line at do-while(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('d');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('d');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 3) && s.contains("for")) {
                        //System.out.println("in for()");      
                        st.add(line);
                        //p("Line at for(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('f');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('f');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 5) && s.contains("while") && st.lastElement().toString().charAt(0) != 'd') {
                        //System.out.println("in while()");
                        st.add(line);
                        //p("Line at while(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == ';') {
                            st.pop();
                            M[line][line] = 1;
                            M[line][line + 1] = 1;
                        } else if (s.charAt(len - 1) == '{') {
                            st.add('w');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('w');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 6) && s.contains("else if")) {
                        //System.out.println("in else if()");
                        if (s.charAt(0) == '}') {
                            funcBrace(st, f, s);
                        }
                        //p("Line at else if(): " + s + " st: " + st.toString());
                        //st.pop();
                        //M[toInt(st.pop().toString())][line] = 1;
                        st.add("ei");
                        st.add(line);
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('i');
                            } else {
                                M[toInt(st.pop().toString())][line] = 1;
                            }
                        }

                    } else if ((len > 2) && s.contains("if")) {
                        st.add(line);
                        //p("Line at if(): " + s + " st: " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('i');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
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
                        //p("Line at else: " + s + " st: " + st.toString());
                        //st.add(line - 1);
                        //System.err.println(" at line " + line + " " + st.toString());
                        if (s.charAt(len - 1) == '{') {
                            st.add('e');
                            M[line][line + 1] = 1;
                        } else {
                            M[line][line + 1] = 1;
                            s = f.readLine().trim();
                            line++;
                            //p(s);
                            M[line][line + 1] = 1;
                            if (s.charAt(0) == '{') {
                                st.add('e');
                            }
                        }
                    } else if ((len > 0) && (s.charAt(0) == '}' || s.charAt(len - 1) == '}')) {
                        RECALL:
                        {
                            if (st.lastElement().toString().equals("m")) {
                                st.removeAllElements();
                                M[line++][line] = 1;
                                M[line++][line] = 1;
                                //} else if (st.lastElement().toString().equals("ei")) {

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
                                //p("\t*****************\t current line is " + s + " at no. " + (line) + " st: " + st.toString());
                                if ((len > 3) && s.contains("else")) {
                                    st.add(line);
                                } else {
                                    f.mark(line);
                                    s = f.readLine().trim();
                                    len = s.length();
                                    //p(s);     //                  //p("I read this " + s);
                                    if ((len > 3) && (s.contains("else"))) {
                                        st.add(line);
                                        //System.out.println("\tFOUND ELSE at current line is " + s + " at no. " + (line) + " st: " + st.toString());
                                    } else {
                                        //System.out.println("\tNOT FOUND Resetting " + s + " at no. " + (line) + " st: " + st.toString());
                                        M[line][line + 1] = 1;
                                    }
                                    f.reset();
                                }
                                //p("\t*****************\t j is " + j + " and line+1 is " + (line + 1) + " st: " + st.toString());
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
                        }
                    } else if ((len > 0) && (s.charAt(i) == '{' || s.charAt(len - 1) == '{')) {
                        //st.add(line);
                    } else {
                        M[line][line + 1] = 1;
                    }

                } else {
                    M[line][line + 1] = 1;
                }
                //p(" while(!st.empty()) st: " + s + st.toString());
            }
            line--;
            //System.out.println(" out of while(!st.empty()). last line: " + line);
            //printMatrix(M, line);
            System.out.println(" END ");
            setFunc();
            for (int y = 0; y < n; y++) {
                System.out.println(func[y].s + " " + func[y].val);
            }

        } catch (Exception e) {
            System.err.println("in getCFG() " + e);
        }
    }

    public int line = 0;

    private void funcBrace(Stack st, BufferedReader f, String s) throws Exception {
        int j, len = s.length();
        //System.out.println("@@@@@@@@@@@@@@@@ Here in funcBraces @@@@@@@@@@@@@");
        if (st.lastElement().toString().equals("m")) {
            st.removeAllElements();
            M[line++][line] = 1;
            M[line++][line] = 1;
            //} else if (st.lastElement().toString().equals("ei")) {

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
            //p("\t*****************\t current line is " + s + " at no. " + (line) + " st: " + st.toString());
            if ((len > 3) && s.contains("else")) {
                st.add(line);
            } else {
                f.mark(line);
                s = f.readLine().trim();
                len = s.length();
                //p(s);//                  p("I read this " + s);
                if ((len > 3) && (s.contains("else"))) {
                    st.add(line);
                    //System.out.println("\tFOUND ELSE at current line is " + s + " at no. " + (line) + " st: " + st.toString());
                } else {
                    //System.out.println("\tNOT FOUND Resetting " + s + " at no. " + (line) + " st: " + st.toString());
                    M[line][line + 1] = 1;
                }
                f.reset();
            }
            //p("\t*****************\t j is " + j + " and line is " + (line) + " st: " + st.toString());
        } else {
            cout(s);
            getStubs(s);
            M[line][line + 1] = 1;
        }
    }

    private void cout(String t) {
        try {
            if (t.contains("cout<<")) {
                int e, flag = 0;
                String s = t.substring(t.indexOf("cout<<") + 4, t.length()).replaceAll("<<", "");
                s = s.replaceAll("\"", "").replaceAll("endl", "").replace(';', ' ').trim();
                if (!s.toLowerCase().contains("enter") && s.length() > 0) {
                    for (int i = 0; i < n; i++) {
                        if (s.equals(func[i].s)) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        func[n].s = s;
                        func[n].k = 1;
                        //System.out.println(" S: " + s);
                        func[n].val = line;
                        n++;
                        o++;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR!!! in cout() " + e);
        }
    }

    private void getStubs(String t) {
        if (t.contains("if")) {
            String s = t.replaceAll("if|else|&", "").replaceAll("\\{|\\)|\\|", "").trim();
            String st[] = s.split("\\(");
            //System.out.println(s+" st.length: "+st.length);
            int flag = 0;
            for (String st1 : st) {
                if (st1.length() > 0) {
                    for (int j = 0; j < n; j++) {
                        if (st1.equals(func[j].s)) {
                            flag = 1;
                            //System.out.println("flag is 1 due to " + st1 + " equals " + func[j].s);
                        }
                    }
                    //System.out.println("flag = " + flag);
                    if (flag == 0) {
                        func[n].s = st1;
                        //System.out.println("St: " + st1);
                        func[n].val = line;
                        n++;
                        l++;
                    }
                }
            }
        }
    }

    private void setFunc() {
        int i, j;
        C temp;
        for (i = 0; i < n; i++) {
            if (func[i].k == 1) {
                for (j = i + 1; j < n; j++) {
                    if (func[j].k == 0) {
                        temp = func[i];
                        func[i] = func[j];
                        func[j] = temp;
                    }
                }
            }
        }
    }

    public void setMinMax(String mn, String mx) {
        min = toInt(mn);
        max = toInt(mx);
    }

    private List<String> testCasesWorst() {
        int val[] = {min - 1, max + 1, min, min + 1, max - 1, max, (min + max) / 2}, i, j;
        //System.out.println("Before str[][]");
        String s[][] = new String[b][7];
        for (i = 0; i < b; i++) {
            for (j = 0; j < 7; j++) {
                s[i][j] = input[i] + "=" + val[j] + ";";
                if (getVarType(input[i]) == 1) {
                    s[i][j] = input[i] + "=" + (char) val[j] + ";";
                }
                //System.out.print(s[i][j] + " ");
            }
            //System.out.println("");
        }

        Permute2DArray N = new Permute2DArray();
        List<String> lst = N.combinations(s);
        /*/System.out.println("Total no. " + lst.size());
         for (i = 0; i < lst.size(); i++) {
         System.out.println(lst.get(i) + " " + lst.get(i).length());
         }*/
        return lst;
    }

    private String ot[][] = new String[100][2];
    private Object obj[][];

    private void setTable(List<String> st) {
        try {
            int m = 0, i, j, k, y;
            int total = (o + l + 1), pr;
            //Initialization
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");

            EXE E = new EXE();
            E.setFile(sfile);
            List<String> h = new ArrayList<String>();
            List<String> ob = new ArrayList<String>();
            String s, t, v, a;
            h.add("Conditions \\ Test Cases");
            j = 0;
            for (i = 0; i < n; i++) {
                if (func[i].k == 0) {
                    ob.add(func[i].s + "|");
                    j++;
                }
            }
            ob.add("Rule Count|");
            for (i = 0; i < n; i++) {
                if (func[i].k == 1) {
                    ob.add(func[i].s + "|");
                }
            }
            //Compute T/F
            k = 0;
            m = 1;
            for (int x = 0; x < o;) {
                E.init();
                //s = st.get(k).replaceAll("[^0-9 ]", "");
                s = st.get(k).replaceAll("[a-z]|[A-Z]|=|;", "");
                E.giveInput(s);
                E.stdinClose();
                t = E.getOutput();
                while (t.toLowerCase().contains("enter ")) {
                    a = t.toLowerCase();
                    t = t.replace(t.substring(a.indexOf("enter"), a.indexOf(":") + 1), "");
                }
                t = t.trim();
                E.processWait();
                //System.out.println(st.get(k) + " Input: " + s + " Output: " + t);
                pr = 0;
                //Check if the current output leads to a new output
                for (i = j + 1, y = l; i < total; i++, y++) {
                    //System.out.println(t + " " + func[y].s+" Outside  ");
                    if (t.contains(func[y].s) || compare(t, func[y].s, s) > 95) {//t.contains(func[y].s.substring(0, func[y].s.length() / 2))) {
                        if (func[y].v[0] == -1) {
                            //if (m>range && checkImpossible(st, func[y].s, k, engine)) {
                            pr = 1;
                            //} else {
                            //System.out.println(" checkImpossible returned false ");
                            //}          //System.out.println(t + " " + func[y].s);
                        }
                    }
                }
                if (pr == 1) {
                    h.add(m + "");
                    for (i = 0; i < j; i++) {
                        v = engine.eval(st.get(k) + func[i].s).toString();
                        a = v.toUpperCase().charAt(0) + "|";
                        //System.out.println(st.get(k) + func[i].s+" "+v+" "+a);
                        ob.set(i, ob.get(i).concat(a));
                        if (a.equals("F|")) {
                            //System.out.println("\t \t " + st.get(k) + func[i].s + " " + v);
                            if (t.toLowerCase().contains("out of range") || t.toLowerCase().contains("invalid")) {
                                ot[m - 1][0] = t;
                                ot[m - 1][1] = st.get(k).replaceAll("[a-z]|[A-Z]|=|;", "");
                                i++;
                                while (i < j) {
                                    ob.set(i, ob.get(i).concat("-|"));
                                    //System.out.println(ob.get(i));
                                    i++;
                                }
                                break;
                            }
                        }
                    }
                    //System.out.println("t: " + t + " m = " + m + " j = " + j + " total = " + total);
                    int e;
                    for (e = j + 1, y = l; e < total; e++, y++) {
                        //System.out.print(" Inside ");
                        if (t.contains(func[y].s) || (compare(t, func[y].s, s) > 95)) {
                            //if (t.contains(func[y].s.substring(0, func[y].s.length() / 2))) {
                            System.out.println("Compare=> " + t + " " + func[y].s + ": " + compare(t, func[y].s, s));
                            ob.set(e, ob.get(e).concat("X|"));
                            if (func[y].v[0] == -1) {
                                x++;
                                func[y].v[0] = 0;
                                ot[m - 1][0] = t;
                                ot[m - 1][1] = s;
                                //System.out.println("\n\t\t\t found "+t + " " + func[y].s+" x = "+x);
                            }
                        } else {
                            ob.set(e, ob.get(e).concat(" |"));
                        }
                    }

                    if (t.toLowerCase().contains("out of range")) {
                        m = inputsOutOfRange(ob, st, engine);
                        for (int p = 2; p <= m; p++) {
                            h.add(p + "");
                        }
                        //System.out.println(" m = " + m + " x = " + x);
                    }

                    m++;
                } //x++;
                k++;
            }
            System.out.println("h.size() = " + h.size() + "  ob.size() = " + ob.size());
            //Set Decision Table
            String head[] = new String[h.size()];
            obj = new Object[total][];
            System.out.println("\nHeader: ");
            for (i = 0; i < h.size(); i++) {
                System.out.print(h.get(i) + " ");
                head[i] = h.get(i);
            }
            System.out.println("\nOb: ");
            for (i = 0; i < total; i++) {
                System.out.println(i + ": " + ob.get(i) + " ");
                obj[i] = ob.get(i).split("\\|");
            }

            for (j = 1; j < obj[0].length; j++) {
                y = 0; //System.out.println("");
                for (i = 0; i < l; i++) {
                    //System.out.print(" "+obj[i][j]);
                    if (obj[i][j].equals("-")) {
                        y++;
                    }
                }
                ob.set(l, ob.get(l).concat((int) pow(2, y) + "|"));
            }
            obj[l] = ob.get(l).split("\\|");

            if (mark > 0) {
                FTesting FT = new FTesting();
                FT.setHead("Decision Table Testing");
                FT.setCases(h.size() - 1);
                FT.setSource(sfile.getName());
                FT.setTable(obj, head);
                FT.setColumnWidth(0, 250);
                FT.setVisible(true);
            }
            //Set Test Cases
            hd = new String[b + 2];
            Object obt[][] = new Object[m - 1][b + 2];
            hd[0] = "Test Cases";
            for (i = 1; i <= b; i++) {
                hd[i] = "" + input[i - 1];
            }
            hd[i] = "Expected Output";
            System.out.println("\nTest Cases: ");
            for (i = 0; i < m - 1; i++) {
                obt[i][0] = (i + 1) + ".";
                String temp[] = ot[i][1].split("\\s+");
                j = 1;
                for (String t1 : temp) {
                    obt[i][j] = t1;
                    j++;
                }
                obt[i][j] = ot[i][0];
                System.out.println(i + ": " + ot[i][0] + " " + ot[i][1]);
            }
            n = h.size() - 1;
            fex = obt.length;
            if (mark > 0) {
                FTesting TC = new FTesting();
                TC.setHead("Decision Table Testing");
                TC.setCases(h.size() - 1);
                TC.setSource(sfile.getName());
                TC.setTable(obt, hd);
                TC.setColumnWidth(b + 1, 200);
                TC.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println("in DTable setTable() " + e);
        }
    }

    private int inputsOutOfRange(List<String> ob, List<String> st, ScriptEngine engine) {
        try {
            int i = 1, k = 0, j;
            String res, s;
            while (func[i].val == func[i - 1].val && k < st.size()) {
                s = st.get(k);
                for (j = 0; j < i; j++) {
                    s += (func[j].s + " && ");
                }
                s += ("!(" + func[j].s + ")");
                res = engine.eval(s).toString();
                //System.out.println(s+" "+res);
                k++;
                if (res.equals("true")) {
                    for (int q = 0; q < i; q++) {
                        ob.set(q, ob.get(q).concat("T|"));
                    }
                    ob.set(i, ob.get(i).concat("F|"));
                    for (int q = i + 1; q < l; q++) {
                        ob.set(q, ob.get(q).concat("-|"));
                    }
                    for (int q = l; q < n; q++) {
                        if (func[q].s.toLowerCase().contains("out of range")) {
                            ot[i][0] = ot[i - 1][0];
                            ot[i][1] = st.get(k - 1).replaceAll("[a-z]|[A-Z]|=|;", "");;
                            //System.out.println(func[q-1].s.toLowerCase() + " q = " + (q-1) + " l = " + l + " ob: " + ob.get(q));
                            ob.set(q + 1, ob.get(q + 1).concat("X|"));
                        } else {
                            ob.set(q + 1, ob.get(q + 1).concat(" |"));
                        }
                    }
                    i++;
                    k = 0;
                }
            }
            range = i;
            return i;
        } catch (Exception e) {
            System.out.println("ERROR!!! in inputsOutOfRange() " + e);
        }
        return 0;
    }

    private boolean checkImpossible(List<String> st, String s, int k, ScriptEngine engine) {
        int i;
        try {
            if (range + 1 == l) {
                return true;
            }
            for (i = range + 1; i < l; i++) {
                if (engine.eval(st.get(k) + func[i].s).toString().equals("true")) {
                    break;
                }
            }
            //System.out.println("in checkImpossible() i = " + i + " s = " + s);
            if (i == l && !s.toLowerCase().contains("out of range") && !s.toLowerCase().contains("invalid")) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("ERROR! in checkImpossible() " + e);
        }
        return true;
    }

    public Object[][] getObj() {
        return obj;
    }

    public String[][] getOt() {
        return ot;
    }

    public String[] getOtInput() {
        String ip[] = new String[obj[0].length - 1];
        for (int k = 0; k < (obj[0].length - 1); k++) {
            ip[k] = "";
            String[] temp = ot[k][1].split("\\s+");
            for (int j = 0; j < b; j++) {
                temp[j] = input[j] + "=" + temp[j] + "; ";
                ip[k] += temp[j];
            }
        }
        return ip;
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

    public int getOutDegree(int j) {
        int i = 0;
        for (int k = 0; k < n; k++) {
            i += M[j][k];
        }
        return i;
    }

    private int getInputPos(String s) {
        for (int i = 0; i < b; i++) {
            if (s.charAt(0) == input[i]) {
                return i;
            }
        }
        return -1;
    }

    private int compare(String s, String t, String inp) {
        //System.out.print("compare( " + s + ", " + t + "): ");
        String s1[] = s.split("\\s+"), s2[] = t.split("\\s+");
        int i = 0, j = 0, p = s.length(), q = 0;
        if (s1.length == s2.length) {
            while (i < s1.length) {
                if (s1[i].equals(s2[i])) {
                    j++;
                    //q=q+s1[i].length();
                } else {
                    //p=p-s1[i].length()+1;
                }
                //System.out.println(s1[i] + " " + s2[i] + " j: " + j);
                i++;
            }
            double d = (j / (double) s1.length) * 100.0;
            //double d = (q / (double) p) * 100.0;
            //System.out.println(" p = "+p+" q = "+q+" d = "+d+"\n");
            //System.out.println("compare( " + s + ", " + t + "): " + d);
            i = (int) d;

            if (i < 96) {
                //System.out.println(s+" ");
                for (j = 0; j < s2.length; j++) {
                    if (s2[j].length() == 1 && getInputPos(s2[j]) != -1) {
                        s2[j] = inp.split("\\s+")[getInputPos(s2[j])];
                    } //System.out.print(s2[j]+" ");
                }
                i = j = 0;
                while (i < s1.length) {
                    if (s1[i].equals(s2[i])) {
                        j++;
                    }
                    //System.out.println(s1[i] + " " + s2[i] + " j: " + j);
                    i++;
                }
                d = (j / (double) s1.length) * 100.0;
                i = (int) d;
                //System.out.println(" "+i);
            }
        }
        //System.out.println(" " + i);
        return i;
    }
}
/**
 * END OF PROJECT.
 */
