package pos.domainlayer;

public class PercentDiscountPricingStrategy implements ISalePricingStrategy {

	@Override
	public Money getTotal(Sale s) {
		int t = Integer.parseInt(s.getAllTotal().toString());
		t = t-((t/100)*10);
		Money total = new Money(t);
		return total;
	}

}
