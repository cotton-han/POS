package pos.domainlayer;

import java.util.Iterator;

public class CompositeBestForStorePricingStrategy extends CompositePricingStrategy {

	@Override
	public Money getTotal(Sale s) {
		Money highestTotal = s.getAllTotal();

		Iterator<ISalePricingStrategy> i = strategies.iterator();
		while(i.hasNext()) {
			ISalePricingStrategy strategy = i.next();
			Money total =strategy.getTotal(s);
			highestTotal = total.max(highestTotal);
		}
		return highestTotal;
	}

}
