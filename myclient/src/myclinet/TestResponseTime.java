package myclinet;

import java.util.Vector;

public class TestResponseTime extends Thread{
	public String cName;//client name
	public int choice;//0send 1receive(delete)
	public int times;//how many times to exec
	public String qname;//target queue name
	//may add a vector
	
	public TestResponseTime(){
		this.cName = "";
		this.choice = 0;
		this.times = 0;
		this.qname = "";;
	}
	public TestResponseTime(String cName, int choice, int times, String qname){
		this.cName = cName;
		this.choice = choice;
		this.times = times;
		this.qname = qname;
	}
	public String toString(){
		String info = "";
		info = "cName: "+this.cName
		+" choice: "+this.choice
		+" times: "+this.times
		+" qname: "+this.qname;
		return info;
	}
	public void run(){
		SClient client = new SClient();
		if(this.choice == 0){
			client.CreateQueue(this.qname);
			System.out.println(client.ListQueue());
			//start time
			long startTime=System.currentTimeMillis();
			for(int i=0;i<this.times;i++){
				client.SendMessage(this.qname, this.cName+i);
			}
			long endTime=System.currentTimeMillis();
			System.out.println("delta: "+(endTime-startTime));
			//end time
		}else if(this.choice == 1){
			System.out.println(client.ListQueue());
			//start time
			long startTime=System.currentTimeMillis();
			for(int i=0;i<this.times;i++){
				Vector v = client.ReceiveMessage(this.qname);
				if(!v.get(0).equals("-1")){
					System.out.println(v);
					System.out.println(client.DeleteMessage(v.get(1).toString()));
					//client.DeleteMessage(v.get(1).toString());
				}else{
					System.out.println("receive nothing");
				}
			}
			long endTime=System.currentTimeMillis();
			System.out.println("delta: "+(endTime-startTime));
			//end time
		}
	}
}
