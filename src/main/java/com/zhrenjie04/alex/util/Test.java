package com.zhrenjie04.alex.util;

import java.util.Stack;

public class Test {

	public static void main(String[] args) {
		StringBuffer input=null;
		StringBuffer output=new StringBuffer("aabbbcceeecffdddfkkk");
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
		//方法二
		input=new StringBuffer("aabbbcceeecffdddfkkk");
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
