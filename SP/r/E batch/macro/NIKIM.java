import java.io.*;
import java.util.*;
class MNT {
    String name;
    int addr;
    String ala[] = new String[10];

    public String getName() {
        return name;
    }

    public int getAddr() {
        return addr;
    }

    public String getAla(int i) {
        return ala[i];
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public void setAla(int i, String val) {
        this.ala[i] = val;
    }

    public int findInAla(String word) {
        for (int i = 0; i < ala.length; i++)
            if (ala[i].compareTo(word) == 0)
                return i;
        return -1;
    }
}
public class NIKIM {
    static BufferedReader br;
    static MNT[] mnt = new MNT[10];
    static boolean foundM = false, foundMend = false;
    static int mntc = 1, mdtp = 0;
    static int mdtc = 1, i = 0;
    static String mdt[] = new String[50];
    private static final String FILENAME = "Intermediate.asm";
    static BufferedWriter bw = null;
    static FileWriter fw = null;
    private static final String FILENAME1 = "Final.asm";
    static BufferedWriter bw1 = null;
    static FileWriter fw1 = null;

    public static void main(String args[]) {
        try {
            File file = new File(FILENAME);
            br = new BufferedReader(new FileReader("Codem.asm"));
            System.out.println();
            String line = br.readLine();
            while (line != null) {
                String arrOfStr[] = line.split(" "); //array of all words in string
                int len = arrOfStr.length;
                if (foundM) {
                    i = 0;
                    mnt[mntc] = new MNT();
                    for (String word: arrOfStr) { //for all words
                        if (word.startsWith("&")) { //prepare ALA (index,ARG)
                            mnt[mntc].setAla(i, word);
                            i++;
                            //System.out.println("Arg - "+mnt[mntc].getAla(i) +" at loc "+i);
                        } else {
                            mnt[mntc].setName(word);
                            mnt[mntc].setAddr(mdtc);
                            //System.out.println("Name - "+mnt[mntc].getName() +" at addr"+mdtc);
                        }
                    }
                    System.out.println("Ala of mnt " + mnt[mntc].getName() + ":");
                    for (int j = 0; j < i; j++)
                        System.out.println(mnt[mntc].getAla(j) + " at loc " + j);
                    System.out.println();
                    mntc++;
                    mdt[mdtc] = line;
                    mdtc++;
                    foundM = false;
                }
                else {
                    if (line.compareTo("MACRO") == 0) {
                        foundM = true;
                        foundMend = false;
                        //read next line
                    }
                    else if (line.compareTo("MEND") == 0) {
                        mdt[mdtc] = line;
                        mdtc++;
                        foundMend = true;
                        //read next line
                    } 
                    else if (!foundMend) { //mdt entry until mend
                        String temp;
                        for (String word: arrOfStr) {
                            if (word.startsWith("&")) {
                                int index = mnt[mntc - 1].findInAla(word);
                                temp = "#" + Integer.toString(index);
                                line = line.replace(word, temp);
                            }
                        }
                        mdt[mdtc] = line;
                        mdtc++;
                        //read next line
                    } 
                    else if (!foundM && foundMend) {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        fw = new FileWriter(file, true);
                        bw = new BufferedWriter(fw);
                        bw.write(line + "\n");
                        if (bw != null)
                            bw.close();
                        if (fw != null)
                            fw.close();
                    } 
                    else if (line.compareTo("END") == 0) {
                        //read next line
                    }
                }
                line = br.readLine();
            }

            System.out.println("--------MDT--------");
            
            for (int j = 1; j < mdtc; j++)
                System.out.println(j + " " + mdt[j]);
            
            System.out.println("Curr MDTC at: " + mdtc);
            System.out.println("Curr MNTC at: " + mntc);
            br.close();
            
            System.out.println("-----PASS 2-----");
            boolean noexpand = false;
            foundM = false;
            foundMend = false;
           
            br = new BufferedReader(new FileReader("Intermediate.asm"));
            System.out.println();
            line = br.readLine();
            File file1 = new File(FILENAME1);
            
            while (line != null) {
                String arrOfStr[] = line.split(" "); //array of all words in string
                int len = arrOfStr.length;
                int index = 0;
                for (String word: arrOfStr) {
                    for (i = 1; i < mntc; i++) {
                        if (word.compareTo(mnt[i].getName()) == 0) {
                            index = i;
                            break;
                        }
                    }
                }
                if (index > 0) {
                    mdtp = mnt[index].getAddr();
                    i = 0;
                    for (String word: arrOfStr) {
                        if (word.compareTo(mnt[index].getName()) != 0) {
                            mnt[index].setAla(i, word);
                            i++;
                        }
                    }
                    //mdtp++;
                    String temp = mdt[++mdtp];
                    //System.out.println(temp);
                    while (temp.compareTo("MEND") != 0) {
                        String arrOfReplace[] = temp.split(" ");
                        for (String word: arrOfReplace) {
                            if (word.startsWith("#")) {
                                String somestr = word.substring(1);
                                int repIndex = Integer.parseInt(somestr);
                                temp = temp.replace(word,
                                    mnt[index].getAla(repIndex));
                            }
                        }
                        System.out.println(temp);
                        temp = mdt[++mdtp];
                    }
                } else {
                    System.out.println(line);
                }
                line = br.readLine();
            }
            br.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}