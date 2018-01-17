package com.mypackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {
	List<String> strList = new ArrayList<String>();
	HashMap<Integer, String> map = new HashMap<Integer, String>();
	String[] strArrayStrings = new String[10];
	public static final String fileName = "E:\\test1.txt";
	String[] sarr = new String[6];
	String ssString = "";

	public void setValue() {
		strList.add("cat");
		strList.add("dog");
		strList.add("elephant");
		Arrays.asList(strArrayStrings);

	}

	public static int intMethod() {
		return 5;
	}

	public void show() {
		System.out.println("Mytest");
	}

	public static void getLongest(String s) {
		int[] checkArr = new int[256];
		int length = 0;
		int Max = 0;
		char[] charArr = s.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			if (checkArr[charArr[i]] == 0) {
				checkArr[charArr[i]] = i;
				length++;
			} else {
				if (Max < length)
					Max = length;
				int interval = i - checkArr[charArr[i]];
				if (Max < interval) {
					Max = interval;
				}
				length = 1;
				checkArr[charArr[i]] = i;
			}
		}
		System.out.println(Max);
	}

	public static void printInt(int n) {
		System.out.println(n);
		if (n > 5000) {
			System.out.println(n);
			return;
		}
		printInt(n * 2);
		System.out.println(n);
	}
	
	
	public void test(){
		AtomicInteger integer = new AtomicInteger(1);
		Scanner scan = new Scanner(System.in);
		int[] ii = new int[2];
		int sum = 0;
		sum+=ii[0];
		String string = "aaa";
		HashMap<Integer, Integer> wordCounts = new HashMap<Integer, Integer>();
		String pattern = "[a-z]";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(string);

	}

	public static void getCombination(List<Integer> originList, int sum, int currentSum,
			List<Integer> l) {
		if (currentSum == sum) {
			for (Integer i:l)
			{
				System.out.print(i+" ");			
			}
			System.out.print("\n");

		} else if (currentSum < sum){
			if (originList.size() <= 0)
				return;
			else {
				int tmp = originList.get(0);
				List<Integer> subList = null;
				if (originList.size()>1)
				{
					subList = originList.subList(1, originList.size());
				}
				else{
					subList = new ArrayList<Integer>();
				}
				List<Integer> newl = new ArrayList<Integer>();
				for (Integer integer:l)
				{
					newl.add(integer);
				}
				getCombination(subList , sum, currentSum, newl);
				newl.add(tmp);
				getCombination(subList, sum, currentSum+tmp, newl);
			}
		}
	}

	public static void sortHashmap(){
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("a", 3);
		map.put("b", 2);
		map.put("c", 7);
		map.put("d", 1);
		
		List<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String,Integer> o2){
				return (o1.getValue()-o2.getValue());
			}
		});
		for (Map.Entry<String, Integer> m:mapList){
			System.out.println(m.toString());
		}
	}
	
	public static void main(String[] args) {
		//List<Integer> list = new ArrayList<Integer>(Arrays.asList(3,1,6,4,2,9,7));
		//List<Integer> l = new ArrayList<Integer>();
		//getCombination(list, 10, 0, l);
		sortHashmap();
	}

	public static void readFile() {
		try {
			FileReader fReader = new FileReader(fileName);
			BufferedReader bf = new BufferedReader(fReader);
			String s = "";
			while ((s = bf.readLine()) != null) {
				System.out.println(s);
			}
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void writeFile() {

		try {
			FileWriter fWriter = new FileWriter(fileName);

			BufferedWriter bufferedWriter = new BufferedWriter(fWriter);

			String dataString = "xxxxxx";
			bufferedWriter.write(dataString);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printWrite() {
		try {
			FileWriter fWriter = new FileWriter(fileName, true);
			PrintWriter pWriter = new PrintWriter(fWriter);

			String data = "\n test write";
			try {
				pWriter.print(data);
			} finally {
				pWriter.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class NewTest extends MyTest {
	public void show() {
		System.out.println("NewTest");
		
	}

}


