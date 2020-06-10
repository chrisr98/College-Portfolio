public class Customer extends Member{

    public int pocketMoney;
    public Customer() {
    	
    }
    
    public Customer(int idNum, String firstName, String lastName, String password, String userName, int pocketMoney) {
    	super(idNum,firstName,lastName, password,userName);
        this.pocketMoney = pocketMoney;
    }

    public int getPocketMoney() {
        return pocketMoney;
    }

    public void setPocketMoney(int pocketMoney) {
        this.pocketMoney = pocketMoney;
    }

    public void offsetPocketMoney(int offset){
        setPocketMoney(getPocketMoney()+offset);
    }
}