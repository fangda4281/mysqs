package sqstest;
import mysqs.*;
public class TestIHT {
		public void test(){
			//small scale test
			IHashTable iht = new IHashTable(3);
			System.out.println(iht);
			
			//test createQueue()
			System.out.println("test createQueue()");
			for(int i=0; i < 4; i++){
				String qname = "qname"+i;
				int pos = iht.createQueue(qname);
//				System.out.println(pos);
//				System.out.println(iht);
				
			}
			//System.out.println(iht.htable[2]);
			
			//test put()
			//put into the same queue
			//System.out.println("test put()");
//			for(int i=0; i<4;i++){
//				MessageId msgid = new MessageId();
//				msgid.msgId = "00"+i;
//				SHashCell shc = new SHashCell();
//				boolean flag = iht.put("qname0", msgid, shc);
//				if(!flag){
//					System.out.println("put fail");
//				}
//				//System.out.println(iht.htable[2].q);//check table
//				System.out.println(shc);
//			}
			
			//test put()
			//put into different queue
//			System.out.println("test put()");
//			for(int i=0; i<4;i++){
//				MessageId msgid = new MessageId();
//				msgid.msgId = "00"+i;
//				SHashCell shc = new SHashCell();
//				boolean flag = iht.put("qname"+i, msgid, shc);
//				if(!flag){
//					System.out.println("put fail");
//				}
//				System.out.println(iht);//check table
//				System.out.println(shc);
//			}
			
			//test get()
//			System.out.println("test get()");
//			MessageId msgid1 = new MessageId();
//			String key1 = "qname0";
//			for(int i=0; i <5; i++){
//				boolean flag = iht.get(key1, msgid1);
//				if(!flag){
//					System.out.println("get fail");
//				}
//				System.out.println(msgid1);
//				System.out.println(iht);
//			}
			
			//test delete()
			//delete from same queue
//			SHashCell scell1 = new SHashCell();
//			scell1.qidx = 2;
//			scell1.midx = 2;
//			iht.delete(scell1);
//			System.out.println(iht.htable[2].q);
//			System.out.println(iht.htable[2].q.queue[2]);
//			
//			SHashCell scell2 = new SHashCell();
//			scell2.qidx = 2;
//			scell2.midx = 2;
//			iht.delete(scell2);
//			System.out.println(iht.htable[2].q);
//			System.out.println(iht.htable[2].q.queue[2]);
			
			//test deleteQueue()
//			System.out.println("test deleteQueue");
//			iht.deleteQueue("qname0");
//			System.out.println(iht);
//			System.out.println(iht.htable[2]);
			

			
			
			
			
		}
}
