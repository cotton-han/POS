package pos.domainlayer;

import java.util.Iterator;

public class CompositeBestForCustomerPricingStrategy extends CompositePricingStrategy {

	@Override
	public Money getTotal(Sale s) {
		Money lowestTotal = new Money(Integer.MAX_VALUE);
		
		Iterator<ISalePricingStrategy> i = strategies.iterator();
		while(i.hasNext()) {
			ISalePricingStrategy strategy = i.next();
			Money total =strategy.getTotal(s);
			lowestTotal = total.min(lowestTotal);
		}
		return lowestTotal;
	}

}
