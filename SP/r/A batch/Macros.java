import java.io.*;
import java.util.*;
public class Macro {
    public String name;
    public int mntIndex;
    public boolean hasLabel = false;
    public HashMap < Integer, String > ala = new HashMap < Integer, String > ();
    public Macro() {}
    public Macro(String name, int mntIndex, boolean hasLabel,
        HashMap < Integer, String > ala) {
        this.name = name;
        this.mntIndex = mntIndex;
        this.hasLabel = hasLabel;
        this.ala = ala;
    }
    public class MacroProcessor {
        public static ArrayList < String > mdt = new ArrayList < String > ();
        public static HashMap < String, Macro > mnt = new HashMap < String, Macro > ();
        public static void main(String[] args) throws IOException,
            FileNotFoundException {
                if (args.length == 0) {
                    System.out.println("Usage: java MacroProcessor <filename>");
                    return;
                }
                File file = new File(args[0]);
                File temp = new File("temp.asm");
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    if (line.trim().equalsIgnoreCase("MACRO")) {
                        System.out.println("\nSTART MACRO DEFINITION");
                        line = reader.readLine();
                        mdt.add(line);
                        String tokens[] = line.split(" |,|\t");
                        Macro macro = new Macro();
                        int offset = 0;
                        if (tokens[0].charAt(0) == '&') {
                            System.out.println("LABEL: " + tokens[0]);
                            macro.hasLabel = true;
                            offset = 1;
                        } else macro.hasLabel = false;
                        macro.name = tokens[offset];
                        macro.mntIndex = mdt.size() - 1;
                        System.out.println("MACRO NAME: " + macro.name);
                        mnt.put(macro.name, macro);
                        do {
                            line = reader.readLine();
                            mdt.add(line);
                            tokens = line.split(" ");
                        } while (line != null &&
                            !line.trim().equalsIgnoreCase("MEND"));
                        System.out.println("END MACRO DEFINITION\n");
                    } else {
                        System.out.println("Writing to temp file: " + line);
                        writer.write(line + "\n");
                    }
                    line = reader.readLine();
                }
                writer.close();
                System.out.println();
                System.out.println("---- MDT ----");
                for (int i = 0; i < mdt.size(); i++) {
                    System.out.println(i + "\t" + mdt.get(i));
                }
                System.out.println();
                System.out.println("---- MNT ----");
                Set < String > keySet = mnt.keySet();
                for (String s: keySet) {
                    System.out.println(mnt.get(s).mntIndex + "\t" + s);
                }
                System.out.println();
                replaceInMDT();
                for (Macro m: invertMap(mnt).keySet()) {
                    System.out.println("---- ALA: " + m.name + " ----");
                    Set < Integer > keys = m.ala.keySet();
                    for (int i: keys) {
                        System.out.println(i + "\t" + m.ala.get(i));
                    }
                    System.out.println();
                }
                System.out.println("---- FINAL MDT ----");
                for (int i = 0; i < mdt.size(); i++) {
                    System.out.println(i + "\t" + mdt.get(i));
                }
                System.out.println();
                System.out.println("\n======== STARTING PASS 2 ========\n");
                pass2(temp);
            }
        public static void replaceInMDT() {
            int i = 0;
            while (i < mdt.size()) {
                String macroName;
                String line = mdt.get(i);
                String tokens[] = line.split(" |,|\t");
                int j = 0;
                Macro macro;
                if (tokens[0].charAt(0) == '&') {
                    macroName = tokens[1];
                    macro = mnt.get(macroName);
                    macro.ala.put(macro.ala.size(), tokens[j++]);
                } else {
                    macroName = tokens[0];
                    macro = mnt.get(macroName);
                }
                j++;
                while (tokens.length > j) {
                    if (tokens[j].charAt(0) == '&') {
                        macro.ala.put(macro.ala.size(), tokens[j++]);
                    }
                    j++;
                }
                i++;
                do {
                    line = mdt.get(i);
                    tokens = line.split(" |,");
                    j = 1;
                    while (tokens.length > j) {
                        if (tokens[j].charAt(0) == '&') {
                            String newLine =
                                mdt.get(i).replace(tokens[j], "#" + invertMap(macro.ala).get(tokens[j]));
                            mdt.set(i, newLine);
                        }
                        j++;
                    }
                    i++;
                } while (!line.contains("MEND"));
            }
        }
        public static void pass2(File temp) throws IOException {
                File outputFile = new File("out.asm");
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                BufferedReader reader = new BufferedReader(new FileReader(temp));
                String line = reader.readLine();
                while (line != null) {
                    for (String mName: mnt.keySet()) {
                        if (line.contains(mName)) {
                            String tokens[] = line.split(" |,|\t");
                            int j = 0, offset = 0;
                            Macro macro = mnt.get(mName);
                            if (macro.hasLabel) {
                                macro.ala.put(0, tokens[0]);
                                offset = 1;
                            }
                            while (tokens.length > (j + 2)) {
                                macro.ala.put(j + offset, tokens[j + 2]);
                                j++;
                            }
                            System.out.println("---- ALA: " + mName + "-- --");
                                Set < Integer > keys = macro.ala.keySet();
                                for (int i: keys) {
                                    System.out.println(i + "\t" +
                                        macro.ala.get(i));
                                }
                                System.out.println(); int i = getIndexFromMDT(mName) + 1; line = mdt.get(i);
                                while (!line.contains("MEND")) {
                                    for (int a: macro.ala.keySet()) {
                                        String s = "#" + a;
                                        line = line.replace(s,
                                            macro.ala.get(a));
                                    }
                                    writer.write(line + "\n");
                                    i++;
                                    line = mdt.get(i);
                                }
                                line = reader.readLine();
                            }
                            else {
                                writer.write(line + "\n");
                                line = reader.readLine();
                            }
                        }
                        writer.write(line + "\n");
                        line = reader.readLine();
                    }
                    writer.close();
                    System.out.println("Pass 2 complete! Check final.asm file for output ");
                    }
                    public static int getIndexFromMDT(String macroName) {
                        for (int i = 0; i < mdt.size(); i++) {
                            if (mdt.get(i).contains(macroName)) {
                                return i;
                            }
                        }
                        return -1;
                    }
                    public static < K, V > Map < V, K > invertMap(Map < K, V > toInvert) {
                        Map < V, K > result = new HashMap < V, K > ();
                        for (K k: toInvert.keySet()) {
                            result.put(toInvert.get(k), k);
                        }
                        return result;
                    }
                }
