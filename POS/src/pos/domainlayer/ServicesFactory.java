package pos.domainlayer;

public class ServicesFactory {
	private static ServicesFactory sf = null;
	private ITaxCalculatorAdapter taxCalculatorAdapter;
	
	ITaxCalculatorAdapter getTaxCalculatorAdapter() {
			String className = System.getProperty("taxcalculator.class.name");
			try {
				taxCalculatorAdapter = (ITaxCalculatorAdapter)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return taxCalculatorAdapter;
	}
	
	synchronized public static ServicesFactory getInstance() {
		if(sf==null) {
			sf = new ServicesFactory();
		}
		return sf;
	}
}
