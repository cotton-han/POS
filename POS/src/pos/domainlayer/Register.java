package pos.domainlayer;

public class Register {

	private ProductCatalog catalog;
	private Sale currentSale;
	private ITaxCalculatorAdapter itca;

	public Register(ProductCatalog catalog){
		this.catalog = catalog;
	}

	public void endSale()
	{
		currentSale.becomeComplete();
	}

	public void enterItem(ItemID id, int quantity)
	{
		ProductDescription desc = catalog.getProductDescription(id);
		currentSale.makeLineItem(desc, quantity);
		currentSale.getTotal();
	}

	
	//세금계산 
	public Money calculateTax()
	{
		itca = ServicesFactory.getInstance().getTaxCalculatorAdapter();
		Money taxTotal = itca.getTax(currentSale);
		currentSale.setTotal(taxTotal);
		return taxTotal;
	}

	//할인전략 생성
	public void createStrategy(String strategy) {
		currentSale.createStrategy(strategy);
	}

	//할인 계산
	public Money applyDiscount() {
		Money discountTotal = currentSale.getDiscountTotal();
		currentSale.setTotal(discountTotal);
		return discountTotal;
	}

	//TextArea출력위한 값 가져오기 
	public Money getPrice(ItemID id) {
		return catalog.getPrice(id);
	}
	
	//TextArea출력위한 제품명 가져오기
	public String getDesc(ItemID id) {
		return catalog.getDesc(id);
	}
	
	public Sale makeNewSale()
	{
		currentSale = new Sale();
		return currentSale;
	}

	public void makePayment(Money cashTendered)
	{
		currentSale.makePayment(cashTendered);
	}
}