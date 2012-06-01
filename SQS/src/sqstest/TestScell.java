package sqstest;
import mysqs.*;

public class TestScell {
	public void test(){
		SHashCell cell = new SHashCell();
		cell.content = "cell";
		System.out.println(cell);
		
		//test assign by value
		SHashCell cell1 = new SHashCell();
		cell1.assign(cell);
		cell1.content = "cell1";
		System.out.println(cell);
		System.out.println(cell1);
		
	}
}
