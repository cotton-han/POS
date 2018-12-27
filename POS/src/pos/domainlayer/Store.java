package pos.domainlayer;

import java.util.Vector;

public class Store {
	private ProductCatalog catalog = new ProductCatalog();
	private Register register = new Register(catalog);
	
	public Register getRegister() { return register; }

	//추가
	public Vector<ItemID> getItemIds() {
		return catalog.getItemIds();
	}
}
