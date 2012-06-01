package mysqs;

public class IHashTable {
	private static final int SIZE = 9973;
	public int maxsize;
	public int cursize;//amount of msg
	public IHashCell htable[];
	
	public IHashTable(){
		this.maxsize  = SIZE;
		this.cursize = 0;
		this.htable = new IHashCell[SIZE];
	}
	public IHashTable(int newSize){
		this.maxsize  = newSize;
		this.cursize = 0;
		this.htable = new IHashCell[newSize];
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
	public int findPos(String key){
		int cIndex = hash(key);//candidate index
		int pos = cIndex;
		int upbd = (this.maxsize-1)/2 + 1;
		boolean flag = false;
		for(int i=1; i<=upbd; i++){
			pos = pos%this.maxsize;//candidate index mod hashtable size
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				IHashCell newCell = new IHashCell();
				this.htable[pos] = newCell;
			}
			//queue should be created
			if(this.htable[pos].qName.equals(key) 
					|| this.htable[pos].status == 1){
				flag = true;
				break;
			}else if(this.htable[pos].status == 0){//queue is not created
				flag = false;
				break;
			}else{//delete or active but not match
				//do nothing
			}
			pos = pos + i*2 - 1;//second detection
		}
		if(flag == false){
			pos = -1;
		}
		return pos;
	}
	//queue is already exist
	//key:qname, scell is empty obj
	//should assigned position infomation
	public boolean put(String key, MessageId mId, SHashCell scell){
		boolean flag = false;
		int pos = findPos(key);
		//System.out.println(pos);
		if(pos != -1){
			this.htable[pos].q.push(mId);
			scell.qidx = pos;
			scell.midx = this.htable[pos].q.rear-1;//may be not safe
			if(scell.midx < 0){
				scell.midx = this.htable[pos].q.maxsize-1;//last pos
			}
			//System.out.println(scell.qidx);
			//System.out.println(scell.midx);
			flag = true;
		}
		return flag;
	}
	//though just need msgId, we send a whole object MessageId
	//key:queueName
	public boolean get(String key,MessageId mId){
		boolean flag = false;
		int cIndex = hash(key);
		int pos = cIndex;
		int upbd = (this.maxsize-1)/2 + 1;
		for(int i=1; i<=upbd; i++){
			pos = cIndex%this.maxsize;
			//check if htable[pos] is null
			if(this.htable[pos] == null){
				IHashCell newCell = new IHashCell();
				this.htable[pos] = newCell;
			}
			if(this.htable[pos].qName.equals(key) 
					&& this.htable[pos].status == 1){//active, matched
				flag = this.htable[pos].q.receive(mId);//receive() should deal with empty condition
														//receive() impl lock machanism
				break;
			}else if(this.htable[pos].status == 0){//empty, this condition may not happen
				flag = false;
				break;
			}else{//active unmatch or deleted
				//do nothing
			}
			pos = pos + i*2 -1;	
		}
		return flag;
	}
	//scell contain the position
	//finally, htable[qindx].q.queue[position].msgId
	//midx should be change to the 'deleted position'
	public int delete(SHashCell scell){
		int target = -1;
		int qidx = scell.qidx;
		int midx = scell.midx;
		//erase by position
		//the return pos should be change to midx
		target = this.htable[qidx].q.eraseByPos(midx);
		//that is
		if(target != -1){//not erase top
			String msgId = this.htable[qidx].q.queue[target].msgId;
			scell.msgId = msgId;//target msgId store in deleted obj
			//change midx to 'midx'			
		}
		return target;//may be not needed
	}
	//key is qname
	//return position of queue in iHashTable
	public int createQueue(String key){
		int cIndex = hash(key);
		int pos = cIndex;
		int upbd = (this.maxsize-1)/2 + 1;
		boolean flag = false;
		for(int i=1;i<=upbd;i++){
			pos = pos%this.maxsize;
			if(this.htable[pos] == null){
				IHashCell newCell = new IHashCell();
				this.htable[pos] = newCell;
			}
			//add condition decision
			//0empty or deleted is correct position
			if(this.htable[pos].status == 0 
					|| this.htable[pos].status == 2){
				flag = true;
				//create queue
				this.htable[pos].qName = key;
				this.htable[pos].status = 1;//1active
				Squeue newQueue = new Squeue();
				this.htable[pos].q = newQueue;
				this.cursize++;
				break;
			}else if(this.htable[pos].status == 1 
					&& this.htable[pos].qName.equals(key)){
				flag = false;
				break;
			}
			pos = pos + i*2 - 1;
		}
		if(flag == false){
			pos = -1;
		}
		return pos;
	}
	//key: qname
	public boolean deleteQueue(String key){
		boolean flag = false;
		int pos = findPos(key);
		if(pos != -1){
			this.htable[pos].status = 2;//deleted
			flag = true;
			this.cursize--;
		}
		return flag;
	}
	
	
}
