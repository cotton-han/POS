package pos.domainlayer;

public class AbsoluteDiscountOverThresholdPricingStrategy implements ISalePricingStrategy {

	@Override
	public Money getTotal(Sale s) {
		int t=Integer.parseInt(s.getAllTotal().toString());
		if(t>20000) 
		{
			t = t-((t/100)*20);
		}
		Money total = new Money(t);
		return total;
	}

}
