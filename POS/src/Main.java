

import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;
import pos.domainlayer.Store;

public class Main {

	public static void main(String[] args) {
		
		Store store = new Store();
		Register register = store.getRegister();
		
		Sale sale = register.makeNewSale();
		register.enterItem(new ItemID(100), 3);
		register.enterItem(new ItemID(200), 2);
		
		register.endSale();
		
		System.out.println("Total = " + sale.getTotal());
		
		register.makePayment(new Money(10000));
		
		System.out.println("Balance = "+sale.getBalance());
		
	}

}
