package myclinet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ClientTest {

	public static void main(String[] args) throws IOException {
		int flag = 0;//0normal, 1send,2receive
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter mode: 0 forCoder(not recmmend);1 for testSendResponse;"
				+"2 for testReceiveResponse;3 for interactive ui(recmmend) ");
		System.out.print("enter: ");
		String sflag = stdin.readLine();
		flag = Integer.parseInt(sflag);
		if(flag == 0){
			SClient client = new SClient();
			String qName = "queueName1";
			String msg = "Message";
			String msgid = "Messageid";
			//System.out.println(client.CreateQueue(qName));
			//System.out.println(client.DeleteQueue(qName));
			System.out.println(client.ListQueue());
			//System.out.println(client.DeleteQueue(qName));
			//System.out.println(client.SendMessage("queueName1", msg));
//			Vector v = client.ReceiveMessage("queueName1");
//			if(!v.get(0).toString().equals("-1")){
//				System.out.println(v);
//				System.out.println(client.DeleteMessage(v.get(1).toString()));
//				//client.DeleteMessage(v.get(1).toString());
//			}else{
//				System.out.println("receive nothing");
//			}
		}else if(flag == 1){//test send response
			int numOfClients = 1;
			
			int quantity = 0;
			String queueName = "";
			System.out.print("enter qname(ex:q): ");
			queueName = stdin.readLine();
			System.out.print("enter quantity(ex:1000): ");
			String squantity = stdin.readLine();;
			quantity =  Integer.parseInt(squantity);
			
			
			TestResponseTime trtSend = new TestResponseTime("0",0,quantity,queueName);
			for(int i=0; i<numOfClients;i++){
				trtSend.cName = ""+i;
				trtSend.qname = "q"+i;
				trtSend.run();
			}
		}else if(flag == 2){//test receiveresponse
			int numOfClients = 1;
			
			int quantity = 0;
			String queueName = "";
			System.out.print("enter qname(ex:q): ");
			queueName = stdin.readLine();
			System.out.print("enter quantity(ex:1000): ");
			String squantity = stdin.readLine();;
			quantity =  Integer.parseInt(squantity);
			
			TestResponseTime trtResv = new TestResponseTime("0",1,quantity,queueName);
			for(int i=0; i<numOfClients;i++){
				trtResv.qname = "q"+i;
				trtResv.run();
			}
		}else if(flag == 3){
			System.out.println("it's a new world");
			Interactui interac = new Interactui();
			interac.test();
			
		}
	}
	
	

}
