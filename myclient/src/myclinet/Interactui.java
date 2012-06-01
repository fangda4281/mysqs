package myclinet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Interactui {
	public void test() throws IOException{
		int choice = 0;
		SClient client = new SClient();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		while(true){
			System.out.println("0 list queue;1 create queue;2 delete queue;");
			System.out.println("3 send msg;4 receive msg(contain delelte);5 bye");
			System.out.print("enter: ");
			String schoice = stdin.readLine();
			choice = Integer.parseInt(schoice);
			if(choice == 0){
				System.out.println(client.ListQueue());
			}else if(choice == 1){
				String newQueueName = "";
				System.out.print("enter new queue name: ");
				newQueueName = stdin.readLine();
				System.out.println(client.CreateQueue(newQueueName));
			}else if(choice ==2){
				String newQueueName = "";
				System.out.print("enter queue name: ");
				newQueueName = stdin.readLine();
				System.out.println(client.DeleteQueue(newQueueName));	
			}else if(choice ==3){
				String QueueName = "";
				System.out.print("enter queue name: ");
				QueueName = stdin.readLine();
				String msg = "";
				System.out.print("enter msg: ");
				msg = stdin.readLine();
				System.out.println(client.SendMessage(QueueName, msg));
			}else if(choice ==4){
				String QueueName = "";
				System.out.print("enter queue name: ");
				QueueName = stdin.readLine();
				Vector v = client.ReceiveMessage(QueueName);
				if(!v.get(0).toString().equals("-1")){
					System.out.println(v);
					System.out.println(client.DeleteMessage(v.get(1).toString()));
					//client.DeleteMessage(v.get(1).toString());
				}else{
					System.out.println("receive nothing");
				}
			}else if(choice ==5){
				System.out.println("bye");
				break;
			}else{
				System.out.println("unknow command");
			}
		}
	
	}
}
