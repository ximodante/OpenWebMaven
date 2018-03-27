package openadmin.util.edu;

import java.util.ArrayList;
import java.util.List;

public class StringUtilsEdu {
	/**
	 * Splits a delimited string into pieces and remove spaces
	 * @param pStr
	 * @param pDelimiter
	 * @return
	 */
	public static String[] splitAndTrim(String pStr, String pDelimiter) {
		String[] s=pStr.split(pDelimiter);
		for (int i=0; i<s.length; i++) s[i]=s[i].trim();
		return s;
	}
	/**
	 * Gets a List of groups of pLength elements from an array starting at pBegin
	 * @param pArr
	 * @param pBegin
	 * @param pLength
	 * @return
	 */
	/*
	public static <T extends Object> List<List<T>> extractGroups (T[] pArr, int pBegin, int pLength){
		List<List<T>> myList= new ArrayList<List<T>>();
		List<T>myTList=null;
		for (int i=pBegin; i<pArr.length; i++) {
			if ((i-pBegin) % pLength==0) {
				if (myTList!=null) myList.add(myTList);
				myTList=new ArrayList<T>();
				
			}
			myTList.add(pArr[i]);
		}
		
		myList.add(myTList);
		
		return myList;
	}
	*/
	public static List<String[]> extractGroups(String[] pArr, int pBegin, int pLength){
		List<String[]>myList= new ArrayList<String[]>();
		String[] aObj=null;
		int j=0;
		for (int i=pBegin; i<pArr.length; i++) {
			j=(i-pBegin) % pLength;
			if (j==0) {
				if (aObj!=null) myList.add(aObj);
				aObj=new String[pLength];
			}
			aObj[j]=pArr[i];
		}
		
		myList.add(aObj);
		
		return myList;
	}
	
	/**
	 * Gets the substring from the last count-th delimiter
	 *   For instance subStringFromRight(org.ximodante.utils.Clas01.main(1234)", ".", 2) 
	 *   will return "Clas01.main(1234)"
	 * @param delimiter
	 * @param count
	 */
	public static String subStringFromRight(String myString, String delimiter, int count) {
		
		if (count==0) return myString;
		else {
			int i=myString.length();
			int ii=delimiter.length();
			int myCount=0;
			int j=i;
			while (j>0 ) {
				if(j<ii) return null;
				    
				if (myString.substring(j-ii, j).equals(delimiter)) myCount++;
			
				if (myCount==count) return myString.substring(j);
				
				j--;
					
			}
			return null;
		}	
		
	}
	
    
	
	public static void main(String[] args) {
		String sx="1.22.333.4444.55555.666666.7777777(12)";
		for (int i=0; i<10; i++) System.out.println( subStringFromRight(sx,".",i));
		
		sx="1.:22.:333.:4444.:55555.:666666.:7777777(12)";
		for (int i=0; i<10; i++) System.out.println( subStringFromRight(sx,".:",i));
		
		
		String s="0,1,2,3,4,5,6,7,8,9,10";
		String ss[]=splitAndTrim(s,",");
		List<String[]>lst=(List<String[]>)extractGroups(ss,3,3);
		for (String[] ls: lst) {
			System.out.println("group-----------------------------");
			for (String s1: ls) System.out.println("   " + s1);
		}
		
	}
}
