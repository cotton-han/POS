package pos.domainlayer;

public class TaxMasterAdapter implements ITaxCalculatorAdapter {

	@Override
	public Money getTax(Sale sale) {
		TaxMaster tm= new TaxMaster();
		return tm.calcTax(sale);
	}

}
