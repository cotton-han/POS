package pos.domainlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProductCatalog {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	private Map<String, ProductDescription> descriptions = new HashMap<String, ProductDescription>();
	private Vector<ItemID> v_id = new Vector<ItemID>();
	private Vector<Money> v_money = new Vector<Money>();
	private Vector<ProductDescription> v_pd = new Vector<ProductDescription>();
	
	public ProductCatalog(){
		try {
			connection = DriverManager.getConnection("jdbc:ucanaccess://POS.mdb");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT itemId, description, price FROM ProductDescriptions");
						
			int i=0;
			while(resultSet.next()) {
				v_id.add(new ItemID(resultSet.getString("itemId"))); 
				v_money.add(new Money(resultSet.getInt("price")));
				v_pd.add(new ProductDescription(v_id.get(i),v_money.get(i),resultSet.getString("description")));
				descriptions.put(v_id.get(i).getId(), v_pd.get(i));
				i++;
			}
			
			resultSet.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public ProductDescription getProductDescription(ItemID id){
		return descriptions.get(id.toString());
		}
	
	//추가
	public Vector <ItemID> getItemIds(){
		return v_id;
	}
	
	//추가
	public Money getPrice(ItemID id) {
		return descriptions.get(id.toString()).getPrice();
	}
	//추가
	public String getDesc(ItemID id) {
		return descriptions.get(id.toString()).getDescription();
	}
}
