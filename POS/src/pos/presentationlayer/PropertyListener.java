package pos.presentationlayer;

import pos.domainlayer.Money;
import pos.domainlayer.Sale;

public interface PropertyListener {
	public void onPropertyEvent(Sale source, String name, Money value);
}
