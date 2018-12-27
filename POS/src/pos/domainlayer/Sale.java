package pos.domainlayer;

import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import pos.presentationlayer.PropertyListener;

public class Sale {
	private List<SalesLineItem> lineItems =  new ArrayList<SalesLineItem>();
	private List<PropertyListener> property = new ArrayList<PropertyListener>();
	private CompositePricingStrategy isps = null;
	//private Date date = new Date();
	private boolean isComplete = false;
	private Payment payment;
	private Money total;

	//적용된 합계를 가져오는 함수
	public Money getAllTotal() {
		return total;
	}
	//세금 할인 적용된 돈 적용
	public void setTotal(Money total) {
		this.total = total;
	}
	
	//합계를 설정하는 함수
	public void setCurrentTotal(Money total) {
		this.total = total;
		publishPropertyEvent("sale.total",total);
	}
	
	//모든 Observer들에게 알림
	private void publishPropertyEvent(String name, Money total) {
		for(int i=0; i<property.size();i++) {
			property.get(i).onPropertyEvent(this, name, total);
		}
	}
	public Money getBalance()	{
		return payment.getAmount().minus(total);
	}

	public void becomeComplete(){ 
		isComplete = true; 
	}

	public boolean isComplete() {  
		return isComplete; 
	}

	public void makeLineItem(ProductDescription desc, int quantity)	{
		lineItems.add( new SalesLineItem(desc, quantity));
	}
	
	//소계를 얻는 함수
	public Money getTotal() {
		Money total = new Money();
		Money subtotal = null;

		for(SalesLineItem lineItem : lineItems)
		{
			subtotal = lineItem.getSubtotal();
			total.add(subtotal);
		}
		setCurrentTotal(total);
		return this.total;	
	}

	//할인정책된 총가격 반환
	public Money getDiscountTotal() {
		return isps.getTotal(this);
	}

	//strategy생성,추가
	public void createStrategy(String strategy) {
		if(strategy.equals("customer")) {
			isps = new CompositeBestForCustomerPricingStrategy();
		}
		else if(strategy.equals("store")) {
			isps = new CompositeBestForStorePricingStrategy();
		}
		isps.add(new PercentDiscountPricingStrategy());
		isps.add(new AbsoluteDiscountOverThresholdPricingStrategy());
	}

	public void makePayment(Money cashTendered) {
		payment = new Payment(cashTendered);
	}
	
	//propertyListener추가
	public void addPropertyListener(PropertyListener lis) {
		property.add(lis);
	}
}
