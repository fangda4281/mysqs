package mysqs;

public class SHashTable {
	private static final int SIZE = 1000121;
	public int maxsize;
	public int cursize;
	public SHashCell htable[];
	
	public SHashTable(){
		this.maxsize  = SIZE;
		this.cursize = 0;
		this.htable = new SHashCell[SIZE];
	}
	public SHashTable(int newSize){
		this.maxsize = newSize;
		this.cursize = 0;
		//in htable, elem is 'null'
		this.htable = new SHashCell[newSize];
	}
	public String toString(){
		String s="";
		s = "maxsize:"+this.maxsize
				+" cursize:"+this.cursize;
		return s;
	}
	public int hash(String key){
		int hIndex = 0;
		hIndex = Math.abs(key.hashCode())%this.maxsize;
		return hIndex;
	}
	public int findEmptyPos(String key){//checked
		int cIndex = hash(key);//candidate index
		int pos = cIndex;//cIndex mod maxsize
		int upbd = (this.maxsize-1)/2 + 1;//second detectoin upperbound
		boolean flag = false;
		
		for(int i=1; i<=upbd; i++){
			pos = pos%this.maxsize;//candidate index mod hashtable size
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				SHashCell newCell = new SHashCell();
				this.htable[pos] = newCell;
			}
			//0empty or 1delete are both right
			if((this.htable[pos].status == 0) 
					|| (this.htable[pos].status == 2) ){//get it
				flag = true;
				break;
			}else if(this.htable[pos].status == 1){
				//active, do nothing
			}else{//not 0,1,2, meet bad node,put failure
				 flag = false;
				 break;
			}
			pos = pos + i*2 - 1;//second detection
		}
		if(flag == false){
			pos = -1;
		}
		//System.out.println(pos);
		return pos;
	}
	//put():
	//msgId is key, cell contain pos info
	public boolean put(String key, SHashCell cell){
		boolean flag = false;

		int pos = findEmptyPos(key);

		if(pos != -1){
			this.htable[pos] = cell;//may be contain status
			this.htable[pos].status = 1;//active,may be not needed,but leave here
			this.cursize++;
			flag = true;
		}
		return flag;
	}
	//get()
	//msgId is key, cell is empty to get msgid and content
	public boolean get(String key, SHashCell cell){
		boolean flag = false;
		int cIndex = hash(key);
		int upbd = (this.maxsize-1)/2 + 1;
		int pos = cIndex;
		for(int i=1; i<=upbd; i++){
			pos = pos%this.maxsize;//candidate index mod hashtable size
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				SHashCell newCell = new SHashCell();
				this.htable[pos] = newCell;
			}
			//1active & msgId is correct
			if(this.htable[pos].status == 0){//0empty
				flag = false;
				break;
			}else if(this.htable[pos].status == 1 
					&& this.htable[pos].msgId.equals(key) ){
				cell.assign(this.htable[pos]);//get it
				flag = true;
				break;
			}else{//2deleted or 1active but not match
				//continue, so do nothing
			}
			pos = pos + i*2 - 1;//second detection
		}
		return flag;
	}
	//delete(), cell is empty, get position of cell
	public boolean delete(String key, SHashCell cell){
		boolean flag = false;
		int cIndex = hash(key);
		int pos = cIndex;
		int upbd = (this.maxsize-1)/2 + 1;
		for(int i=1; i<= upbd; i++){
			pos = pos%this.maxsize;
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				SHashCell newCell = new SHashCell();
				this.htable[pos] = newCell;
			}
			if(this.htable[pos].status == 1 && 
					this.htable[pos].msgId.equals(key) ){//1active
				this.htable[pos].status = 2;//2 deleted
				cell.assign(this.htable[pos]);//get index
				this.cursize--;
				flag = true;
				break;
			}else if(this.htable[pos].status == 0){//0empty
				flag = false;
				break;
			}else{//deleted2 or active1 but key(msgid) not match 
				//do nothing
			}
			pos = pos + i*2 - 1;//second detection
		}
		return flag;
	}
	//change midx, after the change of IHashTable
	public boolean changeMidx(String key, int midx){
		boolean flag = false;
		int cIndex = hash(key);
		int pos = cIndex;
		int upbd = (this.maxsize-1)/2 + 1;
		for(int i=1; i<=upbd; i++){
			pos = pos%this.maxsize;//candidate index mod hashtable size
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				SHashCell newCell = new SHashCell();
				this.htable[pos] = newCell;
			}
			//1active & msgId is correct
			if(this.htable[pos].status == 0){//0empty
				flag = false;
				break;
			}else if(this.htable[pos].status == 1 
					&& this.htable[pos].msgId.equals(key) ){
				this.htable[pos].midx = midx;//find it
				flag = true;
				break;
			}else{//2deleted or 1active but not match
				//continue, so do nothing
			}
			pos = pos + i*2 - 1;//second detection
		}
		return flag;
	}

}
