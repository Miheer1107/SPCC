import java.util.*;
import java.io.*;

class MNT
{
	String name;	
	int addr;
	
	String ala[] = new String[10];
	
	public void mnt(){}
	public String getName() {return name;}
	public int getAddr() {return addr;}
	public String getAla(int i) {return ala[i];}
	
	public void setName(String name) {this.name = name;}
	public void setAddr(int addr) {this.addr = addr;}
	public void setAla(int i,String val) {this.ala[i] = val;}
	
	public int findInAla(String word)
	{
		for(int i=0; i<ala.length; i++)
			if(ala[i].compareTo(word)==0)
				{return i;}
		return -1;
	}
}

class mac
{
	static BufferedReader br;
	static MNT[] mnt = new MNT[10];
	static int foundM = 0, foundMend = 0;
	static int mntc=1, mdtc=1, mdtp=0, i=0; 
	static String mdt[] = new String[50];

	public static void main(String[] args)
	{
	
		try{
			br = new BufferedReader( new FileReader("/home/rutvij/Desktop/mcode.asm"));
			String line = br.readLine();
			//line = br.readLine();
			while(line!=null)
			{
				String arrOfStr[] = line.split(" ");
				int len = arrOfStr.length;
			
				if(foundM==1)
				{
					//Define a new macro. Hence new Macro in MNT
					//Just processing the first line after MACRO
					int i=0;
					mnt[mntc] = new MNT();
					for(String word : arrOfStr)
					{
						if(word.startsWith("&"))
						{
							mnt[mntc].setAla(i,word);
							i++;
						}
						else
						{
							mnt[mntc].setName(word);
							mnt[mntc].setAddr(mdtc);
							//System.out.println("MDTC:"+mdtc);
							System.out.println("Name: "+mnt[mntc].getName()+" Address: "+mdtc);	
						}
					}
						System.out.println("ALA of MNT "+mntc);
						for(int j=0; j<i; j++)
							System.out.print(mnt[mntc].getAla(j)+" ");
						System.out.println();
						mntc++;
						mdt[mdtc]=line;
						mdtc++;
						foundM=0;
				
				}
				else
				{
					if(line.compareTo("MACRO")==0)
					{
						foundM = 1;
						foundMend = 0;
					}
					else if(line.compareTo("MEND")==0)
					{
						foundMend = 1;
						mdt[mdtc]=line;
						mdtc++;
					}
					else if(foundMend!=1)
					{	
						String temp;
						for(String word : arrOfStr)
						{
							if(word.startsWith("&"))
							{
								int index = mnt[mntc-1].findInAla(word);
								temp = "#"+Integer.toString(index);
								line = line.replace(word,temp);		
							}
						}
						mdt[mdtc]=line;
						mdtc++;
						//System.out.println("MDTC in else: "+mdtc);
								
					}
					else if(line.compareTo("END")==0){
							//break; //read next line	
						}
				}
				line = br.readLine();
			
			}
		
			for(int j=1; j<mdtc; j++)
			{
				System.out.println(mdt[j]);
			}
			System.out.println("Current MNTC: "+mntc);
			System.out.println("Current MDTC: "+mdtc);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
	
	
	
	
	
	
	/*
	
		System.out.println("Pass2");
		System.out.println("Required: 1. asm file 2.mnt table to get index 3.Ala to get position");
	
		foundM=0; foundMend=0;
		br = new BufferedReader(new FileReader("/home/rutvij/Desktop/mcode.asm"));
	
		String line = br.readLine();
		while(line!=null)
		{
			String arrOfStr[] = line.split(" ");
			int len = arrOfStr.length;
			
			if(line.compareTo("MACRO")==0)
			{
				foundM=1;
				foundMend=0;
			}
			else if(line.compareTo("MEND")==0)
			{
				foundMend=1;
			}
			else if(foundM==1 && foundMend==1)
			{
				int index=0;
				for(String word : arrOfStr)
				{
					for(i=1; i<mntc; i++)
					{
						if(word.compareTo(mnt[i].getName())==1)
						{
							index=i;
							break;
						}
					}
				}
				if(index>0)
				{
					mdtp=mnt[index].getAddr();
					i=0;
					for(String word : arrOfStr)
					{
						
					}
				}
			}
		}*/
	}
}








