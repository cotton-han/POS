package pos.domainlayer;

public class ItemID {
	String id;

	public ItemID(String id) {
		this.id = id;
	}

	public ItemID(int id) {
		this.id = String.valueOf(id); //문자열로 변환
	}
	
	public String getId() {
		return id;
	}

	public String toString() {
		return id;
	}
}
