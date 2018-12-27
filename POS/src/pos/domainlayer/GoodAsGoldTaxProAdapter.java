package pos.domainlayer;

public class GoodAsGoldTaxProAdapter implements ITaxCalculatorAdapter {

	@Override
	public Money getTax(Sale sale) {
		GoodAsGoldTax gag = new GoodAsGoldTax();
		return gag.getTax(sale);
	}

}
