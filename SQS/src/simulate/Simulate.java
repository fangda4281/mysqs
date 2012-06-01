package simulate;
import mysqs.*;
//simulate on local
public class Simulate {
	public String qnameList[];//queue name list
	
	//interface 1
	public boolean CreateQueue(String QueueName){
		boolean flag = false;
		return flag;
	}
	//interface 2
	public String[] ListQueue(){
		String[] qlist = this.qnameList;
		return qlist;
	}
	//interface 3
	public boolean DeleteQueue(String QueueName){
		boolean flag = false;
		return flag;
	}
	//interface 4
	public boolean SendMessage(String QueueName, String Message){
		boolean flag = false;
		return flag;
	}
	//interface 5
	public String ReceiveMessage(String QueueName, String MessageID){
		String msg = "";
		return msg;
	}
	public boolean DeleteMessage(String MessageID){
		boolean flag = false;
		return flag;
	}
}
