package sqstest;
import mysqs.*;

public class TestSqueue {
	public void test(){
		//small scale test
		int qsize = 3;
		Squeue q = new Squeue(qsize);
		//System.out.println(q);

		//push()
		for(int i=0;i<3;i++){
			MessageId msg  = new MessageId(""+i);	
			if(!q.push(msg)){
				//System.out.println("insert fail");
			}
			//System.out.println(q);
		}
		System.out.println(q);
		
		//test receive():return available msgId
		MessageId msg2  = new MessageId();
		for(int i=0;i < 3;i++){
			if(!q.receive(msg2)){
				System.out.println("receive fail");
			}
			System.out.println(q);
			System.out.println(msg2);
			
		}
		
		//test pop(),should after recieved
//		for(int i=0;i<3;i++){
//			if(!q.pop()){
//				System.out.println("pop fail");
//			}
//			System.out.println(q);
//		}
		
		//test eraseByPos(), after received
//		int pos = q.eraseByPos(1);
//		if(pos != -1){
//			System.out.println(pos);
//			System.out.println(q.queue[1]);
//		}else{
//			System.out.println("earse head");
//		}
//		System.out.println(q);
		
	

		
	}
}
