package haveatry;

public class Primer {
	//9973
	//1000121
	//100000007
	public boolean maxPrimer(int max){
		boolean flag = true;
		for(int i=2; i < Math.sqrt(max);i++){
			if(max%i != 0){
				continue;
			}else{
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public void test(){
		int p = 9973;
		if(maxPrimer(p)){
			System.out.println("is primer");
		}else{
			System.out.println("is not primer");
		}
	}
}
