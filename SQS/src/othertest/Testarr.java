//test for other things
package othertest;
import java.util.*;
import mysqs.*;
import haveatry.*;

class Demo{
	public String val1;
	public Demo(){
		this.val1 = "-1"; 
	}
	public Demo(String val){
		this.val1 = val; 
	}
	public void setVal(String val){
		this.val1 = val;
	}
	public String toString(){
		return this.val1;
	}
	
}

public class Testarr {
	public void test(){
		Vector v = new Vector();
		String s = "queue1";
		String s1 = "queue2";
		String s2 = "queue3";
		v.addElement(s);
		v.addElement(s1);
		v.addElement(s2);
		
		System.out.println(v);
		System.out.println(v.size());
		v.remove(s1);
		System.out.println(v);
		System.out.println(v.size());
		
		
	}
	
	public void changeDemo(Demo d){
		d.val1 = "100";
	}
}
