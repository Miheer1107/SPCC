import java.io.*;
import java.util.*;
class MNT
{
	String name;
	int addr;
	
	String ala[] = new String[10];

	public MNT (){	}
	public String getName(){ return name; }
	public int getAddr() {return addr; }
	public String getAla(int i){ return ala[i]; }

	public void setName(String name){ this.name = name ;}
	public void setAddr(int addr){ this.addr = addr ;}
	public void setAla(int i, String val){ this.ala[i] = val; }

	public int findInAla(String word){
		for(int i=0; i<ala.length;i++)
			if(ala[i].compareTo(word)==0)
				return i;
		return -1;
	}
}

public class Macro1{
	
	static BufferedReader br;
	static	MNT[] mnt = new MNT[10] ;
	
	static	boolean foundM = false, foundMend=false;
	static int mntc = 1, mdtp=0;
	static int mdtc = 1, i=0;
	static String mdt[] = new String[50];

	public static void main(String args[])
	{
		try
		{
			br = new BufferedReader(new FileReader("/home/rutvij/Desktop/mcode.asm"));
			String line = br.readLine();
			
			while(line!=null)
			{
				String arrOfStr[] = line.split(" ");
				int len = arrOfStr.length;
				if(foundM){
					i=0;
					mnt[mntc] = new MNT();
					for(String word : arrOfStr){
					
						if(word.startsWith("&")){
							mnt[mntc].setAla(i, word);
							//System.out.println("Arg - "+mnt[mntc].getAla(i) +" at loc "+i);
							i++;
						}else{
							mnt[mntc].setName(word); mnt[mntc].setAddr(mdtc);
							System.out.println("Name - "+mnt[mntc].getName() +" at addr "+mdtc);
						}
					}
					System.out.println("Ala of mnt"+(mntc)+":");
					for(int j=0;j<i;j++)
						System.out.print(mnt[mntc].getAla(j)+" ");
					System.out.println();
					mntc++;
					mdt[mdtc]=line; mdtc++;
					foundM = false;
				}else{
					if(line.compareTo("MACRO")==0){
						foundM = true; foundMend = false;
						//break; //read next line
					}
					else if(line.compareTo("MEND")==0){
						mdt[mdtc]=line; mdtc++;
						foundMend = true;
						//break; //read next line
					}
					else if(!foundMend){
						String temp;
						for(String word : arrOfStr){
							if(word.startsWith("&")){
								int index = mnt[mntc-1].findInAla(word);
								temp = "#"+Integer.toString(index);
								line = line.replace(word,temp);
							}
						}
						mdt[mdtc]=line;
						mdtc++;
						//break; //read next line	
					}
					else if(line.compareTo("END")==0){
						//break; //read next line	
					}
				}
				line = br.readLine();
			}
		
			System.out.println("MDT\n--------");
			for(int j=1;j<mdtc;j++)
				System.out.println(mdt[j]);
			System.out.println("Current mdtc at: "+mdtc);
			System.out.println("Current mntc at: "+mntc);
			br.close();

			System.out.println("-----Pass2-----");
			boolean noexpand = false;
			foundM = false; foundMend = false;
			br = new BufferedReader(new FileReader("/home/rutvij/Desktop/mcode.asm"));
			line = br.readLine();
			while(line!=null){
				String arrOfStr[] = line.split(" ");
				int len = arrOfStr.length;
				if(line.compareTo("MACRO")==0){
					foundM = true; foundMend = false;
				}
				else if(line.compareTo("MEND")==0){
					foundMend = true;	
				}
				else if(foundMend && foundM){
					int index=0;
					for(String word : arrOfStr){
						for(i=1;i<mntc;i++){
							if(word.compareTo(mnt[i].getName())==0){
								index = i; break;
							}
						}
					}
					if(index>0){
						mdtp=mnt[index].getAddr();
						i=0;
						for(String word : arrOfStr){
							if(word.compareTo(mnt[index].getName())!=0){
								mnt[index].setAla(i, word); 
								i++;
							}
						}
						String temp = mdt[++mdtp];
						while(temp.compareTo("MEND")!=0){
							String arrOfReplace[] = temp.split(" ");
							for(String word : arrOfReplace){
								if(word.startsWith("#")){
									String somestr = word.substring(1);
									int repIndex = Integer.parseInt(somestr);
									temp = temp.replace(word, mnt[index].getAla(repIndex));
								}
							}
							System.out.println(temp);
							temp = mdt[++mdtp];
						}
					}else{
						System.out.println(line);
					}
				}
				line = br.readLine();
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
/*OUTPUT
MCODE.ASM
MACRO
&ARG0 INCR &ARG1
&ARG0 A 1 , &ARG1
MEND
MACRO
&ARG0 INCR2 &ARG1
&ARG0 A 2 , &ARG1
ST 2 , 4
MEND
PRGM START 0
USING * , 15
LOOP INCR D1
LOOP2 INCR2 D2
END

java Macro1
Name - INCR at addr 1
Ala of mnt1:
&ARG0 &ARG1 
Name - INCR2 at addr 4
Ala of mnt2:
&ARG0 &ARG1 
MDT
--------
&ARG0 INCR &ARG1
#0 A 1 , #1
MEND
&ARG0 INCR2 &ARG1
#0 A 2 , #1
ST 2 , 4
MEND
Current mdtc at: 8
Current mntc at: 3
-----Pass2-----
PRGM START 0
USING * , 15
LOOP A 1 , D1
LOOP2 A 2 , D2
ST 2 , 4
END
*/
