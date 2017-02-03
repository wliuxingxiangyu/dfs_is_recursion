package test;

import java.util.ArrayList;

public class H00isValidIP {
	static int line=0;
	/**给一个由数字组成的字符串。求出其可能恢复为的所有IP地址。
样例
给出字符串 "25525511135"，所有可能的IP地址为：
[
  "255.255.11.135",
  "255.255.111.35"
]
（顺序无关紧要）
解题：深度优先遍历
	 * @return All possible valid IP addresses 不能包括01 001这样的格式
	 */
	public static ArrayList<String> restoreIpAddresses(String s) {
		ArrayList<String> list = new ArrayList<String>();
		String IP = "";
		int start = 0;
		int IPsize = 0;
		dfs(list, IP, s, start, IPsize);
		return list;
	}

	public static void dfs(ArrayList<String> list, String IP, String s, int start,	int IPsize) {
		if (start == s.length() || IPsize >= 4)  return;
		
		if (IPsize == 3) {//马上要成为有效的4段 子IP，
			String subIP = s.substring(start);//截取下标为start~最后  子串
			if (isStartZero(subIP))  return;//子IP段：以0开始，直接返回，
			if (!isLess255(subIP))  return;//子IP段：大于255，直接返回，
			IP += "." + subIP;
			System.out.println( (line++) +"行,"+"list.size()="+list.size()+" (IPsize == 3)----------- 加入IP="+IP);
			if (!list.contains(IP))     list.add(IP);
			return;
		} else {
			for (int i = start; i < s.length(); i++) {//外层循环for
				int j = 1;
				while (start + j < s.length() && j <= 4) {//内层循环while
					String subIP = s.substring(start, start + j );//截取下标为start~（start+j-1）子串
					System.out.println(  (line++) +"行,"+"while中划分 subIP="+subIP+",start="+start+",j="+j);
					if (isStartZero(subIP))    break;//跳出while
					if (!isLess255(subIP))   break;//跳出while
					
					if (IPsize == 0) {//一个subIP都没有，开始
						IP += subIP;
						IPsize++;
						System.out.println(  (line++) +"行,"+" (IPsize == 0) 进入dfs,IP="+IP+",(start + j)="+(start + j)+",IPsize="+IPsize);
						dfs(list, IP, s, start + j, IPsize);
						IP = "";//递归回来，IP已经加入list。
					} else {
						IP += "." + subIP;//IP=第0个元素.第1个元素。此时IP=2.5。所以下面的IPsize++。
						IPsize++;
						System.out.println( (line++) +"行," +" (IPsize ==1或2或3) 进入dfs,IP="+IP+",(start + j)="+(start + j)+",IPsize="+IPsize);
						dfs(list, IP, s, start + j, IPsize);//以下标为（start + j）开始的子串往下递归，即dfs。
						IP = IP.substring(0, IP.length() - j - 1);//递归回来，再在IP中细分。
						System.out.println( (line++) +"行," +"IP="+IP+",dfs回来(IP.length() - j - 1)="+(IP.length() - j - 1)+",IP.length()="+IP.length()+",j="+j);
					}// if   end

					IPsize--;
					j++;
				} // while    end
			} // for    end
		}//else end
	}// dfs   end

	public static boolean isLess255(String subIP) {
		Long numIP = Long.valueOf(subIP);
		if (numIP < 0 || numIP > 255)
			return false;
		return true;
	}

	public static boolean isStartZero(String subIP) {
		if (subIP.substring(0, 1).equals("0") && subIP.length() >= 2)
			return true;//以0×开头，返回true
		return false;
	}

	public static void main(String[] args) {
		String str="25525511135";
		restoreIpAddresses(str);
		System.out.println("运行结束");
	}
}
