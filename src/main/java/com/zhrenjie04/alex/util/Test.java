package com.zhrenjie04.alex.util;

import java.util.Stack;

public class Test {

	public static void main(String[] args) {
		StringBuffer input=null;
		StringBuffer output=new StringBuffer("aabbbcceddddddddaabbccddeecffdddfkkk");
		do{
		    input=output;
		    output=new StringBuffer();
		    int start=0;
		    int count=0;
		    char c='\n';
		    for(int i=0;i<input.length();i++){
		         if(input.charAt(i)==c){
		               ++count;
		         }else{
		             if(count>=3){
		             }else{
		                   for(int j=start;j<i;j++){
		                          output.append(input.charAt(j));
		                   }
		             }
		             start=i;
		             c=input.charAt(i);
		             count=1;
		         }
		    }
		    if(input.length()-start<3) {
                for(int j=start;j<input.length();j++){
                    output.append(input.charAt(j));
                }
		    }
			System.out.println(input);
			System.out.println(output);
			System.out.println(output==input);
			System.out.println(output.equals(input));
		}while(!output.toString().equals(input.toString()));
		System.out.println(output);
		//消消乐解答方法二
		//下标i一直往前计算currentChar的个数，当s[i]!=currentChar时，进行判断，
		//		如果currentChar的个数大于等于3个，那就继续取下一组相同字符，
		//		                再判断这一组的字符数与栈尾的字符是否相同，如果相同且总数大于等于3，则出栈
		//		                如果不相同，则下一组字符入栈，继续总循环
		//		如果x的个数小于3个，则入栈
		//efffee步骤是这样的：
		//1、
		//堆栈 空
		//扫到e
		//2、
		//堆栈 空
		//扫到f，因为f！=currentChar（e），所以e入栈
		//堆栈 1e
		//3、
		//堆栈 1e
		//扫到f，因为f==currentChar(f),所以++currentCount变为2
		//4、
		//堆栈没变
		//又扫到f，因为f==currentChar(f),所以++currentCount变为3
		//4、
		//堆栈没变
		//扫到e，因为e！=currentChar(f),又因为currentCount>=3所以忽略f的入栈
		//             此时继续取下一组连续字符：2e，取完后，判断堆栈的最后一个字符，发现与2e字符相同而且累加后总数>=3，所以出栈
		//             循环继续取下一组连续字符直到堆栈的最后一个字符与新这一组的字符不同
		//5、继续扫描
		input=new StringBuffer("aabbbcceddddddddaabbccddeecffdddfkkk");
		Stack<String>charStack=new Stack<>();
		Stack<Integer>countStack=new Stack<>();
		char currentChar='\n';
		Integer currentCount=0;
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i)==currentChar) {
				++currentCount;
			}else {
				if(currentCount>=3) {
					currentChar=input.charAt(i);
					currentCount=1;
					for(++i;i<input.length();i++) {
						if(input.charAt(i)!=currentChar) {
							break;
						}
						++currentCount;
					}
					while(charStack.lastElement().equals(""+currentChar)&&(countStack.lastElement()+currentCount>=3)) {
						charStack.pop();
						countStack.pop();
						if(i<input.length()) {
							currentChar=input.charAt(i);
							currentCount=1;
							for(++i;i<input.length();i++) {
								if(input.charAt(i)!=currentChar) {
									break;
								}
								++currentCount;
							}
						}
					}
					if(currentCount<3) {
						charStack.push(""+currentChar);
						countStack.push(currentCount);
					}
					if(i<input.length()) {
						currentChar=input.charAt(i);
						currentCount=1;
					}else {
						currentChar='\n';
						currentCount=0;
					}
				}else {
					charStack.push(""+currentChar);
					countStack.push(currentCount);
					currentChar=input.charAt(i);
					currentCount=1;
				}
			}
		}
		if(currentCount<3) {
			charStack.push(""+currentChar);
			countStack.push(currentCount);
		}
		for(int i=1;i<charStack.size()-1;i++) {
			System.out.print(countStack.elementAt(i)+charStack.elementAt(i)+" ");
		}
		System.out.println();
	}

}
