package pos.domainlayer;

public class Money {
	int amount;

	public Money(int amount) {
		this.amount = amount;
	}
	
	public Money() {
		//this.amount=0;
		this(0); // 인자가 있었던 다른생성자를 호출
	}

	public int getAmount() {
		return amount;
	}

	public Money times(int quantity) {
		return new Money(amount*quantity);
	}

	public Money minus(Money m) {
		return new Money(amount - m.getAmount());
	}

	public void add(Money m) {
		amount += m.getAmount();
	}
	 public String toString() {
		 return String.valueOf(amount);
	 }

	 //가격할인에서 추가
	public Money min(Money lowestTotal) {
		if(amount>lowestTotal.amount) {
			return lowestTotal;
		}
		return this;
	}

	public Money max(Money highestTotal) {
		if(amount<highestTotal.amount) {
			return highestTotal;
		}
		return this;
	}
}
