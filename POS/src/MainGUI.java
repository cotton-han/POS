import java.util.Vector;

import pos.domainlayer.ItemID;
import pos.domainlayer.Register;
import pos.domainlayer.Store;
import pos.presentationlayer.ProcessSaleJFrame;

public class MainGUI {

	public static void main(String[] args) {
		Store store = new Store();
		Register register = store.getRegister();
		
		Vector <ItemID> v_id = new Vector <ItemID>();
		v_id = store.getItemIds();
		new ProcessSaleJFrame(register, v_id);
	}

}
