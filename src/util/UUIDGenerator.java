package util;

import java.util.StringTokenizer;
import java.util.UUID;

public class UUIDGenerator {

	public UUIDGenerator() {
	}

	/**
	 * 獲得一個UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符號
		return s.replace("-", "").substring(0, 15);
	}

	/**
	 * 獲得指定數目的UUID
	 * 
	 * @param number int 需要獲得的UUID數量
	 * @return String[] UUID陣列
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	
	public static void main(String[] args) {
	String mt="122"+"cp"+getUUID();
	System.out.println("mt="+mt);
		StringTokenizer tk=new StringTokenizer(mt,"cp");
		if(tk.hasMoreElements()) {
			
			System.out.println(tk.nextToken());
		}
		
		
		
		String[] s= {"How", "To", "Do", "In", "Java"};
		String joinedString = String.join("#",s);
		System.out.println(joinedString.replaceAll("\\s+", "").length());
		System.out.println(joinedString);
	}


}
