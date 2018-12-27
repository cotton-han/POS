package pos.domainlayer;

public class GoodAsGoldTax {
	Money getTax(Sale s) {
		int t = Integer.parseInt(s.getAllTotal().toString())
				+Integer.parseInt(s.getAllTotal().toString())/100*20;
		Money total = new Money(t);
		return total;
	}
}
