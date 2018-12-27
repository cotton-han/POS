package pos.domainlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositePricingStrategy implements ISalePricingStrategy {
	protected List<ISalePricingStrategy> strategies = new ArrayList<ISalePricingStrategy>();
	
	public void add(ISalePricingStrategy s) {
		strategies.add(s);
	}
	@Override
	public abstract Money getTotal(Sale s);
}
