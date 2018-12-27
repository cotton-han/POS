package pos.domainlayer;

public class TaxMaster {
	Money calcTax(Sale s) {
		int t = Integer.parseInt(s.getAllTotal().toString())
				+Integer.parseInt(s.getAllTotal().toString())/100*10;
		Money total = new Money(t);
		return total;
	}
}
