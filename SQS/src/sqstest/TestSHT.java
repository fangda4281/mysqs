package sqstest;
import mysqs.*;

public class TestSHT {
	public void test(){
		//small scale test
		SHashTable sht = new SHashTable(3);
		System.out.println(sht);
		
		//initialize
		SHashCell shc = new SHashCell();
		shc.msgId = "001";
		shc.content = "001content";
		SHashCell shc2 = new SHashCell();
		shc2.msgId = "002";
		shc2.content = "002content";
		SHashCell shc3 = new SHashCell();
		shc3.msgId = "003";
		shc3.content = "003content";
		SHashCell shc4 = new SHashCell();
		shc4.msgId = "004";
		shc4.content = "004content";
		System.out.println(shc);
		System.out.println(shc2);
		System.out.println(shc3);
		System.out.println(shc4);
		
		//put()
		sht.put(shc.msgId, shc);
		System.out.println(sht);
		sht.put(shc2.msgId, shc2);
		System.out.println(sht);
		sht.put(shc3.msgId, shc3);
		System.out.println(sht);
		sht.put(shc4.msgId, shc4);
		System.out.println(sht);
		//System.out.println(sht.htable[2]);
		
		System.out.println(shc);
		System.out.println(shc2);
		System.out.println(shc3);
		System.out.println(shc4);
		
		//get()
//		SHashCell emptyCell = new SHashCell();
//		sht.get("001", emptyCell);
//		System.out.println(emptyCell);
//		sht.get("002", emptyCell);
//		System.out.println(emptyCell);
//		sht.get("003", emptyCell);
//		System.out.println(emptyCell);
//		sht.get("004", emptyCell);
//		System.out.println(emptyCell);
		
		//delete
//		SHashCell emptyCell2 = new SHashCell();
//		sht.delete("001", emptyCell2);
//		System.out.println(emptyCell2);
//		sht.delete("002", emptyCell2);
//		System.out.println(emptyCell2);
//		sht.delete("003", emptyCell2);
//		System.out.println(emptyCell2);
//		sht.delete("004", emptyCell2);
//		System.out.println(emptyCell2);
		
		//get() after delete()
//		SHashCell emptyCell3 = new SHashCell();
//		sht.get("001", emptyCell3);
//		System.out.println(emptyCell3);
//		sht.get("002", emptyCell3);
//		System.out.println(emptyCell3);
//		sht.get("003", emptyCell3);
//		System.out.println(emptyCell3);
//		sht.get("004", emptyCell3);
//		System.out.println(emptyCell3);
		
		//changeMidx()
//		SHashCell emptyCell4 = new SHashCell();
//		sht.changeMidx("002", 100);
//		sht.get("002", emptyCell4);
//		System.out.println(emptyCell4);
		
		
		
		
		
		
	}
}
