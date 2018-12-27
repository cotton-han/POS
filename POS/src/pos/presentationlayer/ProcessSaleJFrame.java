package pos.presentationlayer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;

import pos.domainlayer.ItemID;
import pos.domainlayer.Money;
import pos.domainlayer.Register;
import pos.domainlayer.Sale;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ProcessSaleJFrame extends JFrame implements PropertyListener{
	private Register mRegister;
	private Sale sale;

	private JButton b_makeNewSale;

	private JButton b_enterItem, b_endSale, b_makePayment;
	private JComboBox<ItemID> cb_itemID;
	private Vector <ItemID> v_id = new Vector<ItemID>();
	private JTextField t_quantity;

	private JTextField t_amount, t_balance;
	private JTextArea ta;
	private JTextField t_desc;
	private JTextField t_current;
	private JButton b_calculateTax;
	private JTextField t_tax;
	private JButton b_discount;
	private JTextField t_discount;

	private JRadioButton forCustomer, forStore, taxMaster, goodAsGold;
	private ButtonGroup tax, discount;

	public ProcessSaleJFrame(Register r, Vector<ItemID> id){
		super("POS System (학번: 20161079 이름: 한예지)");
		this.mRegister = r;
		v_id = id;

		buildGUI();
		registerEventHandler();

		this.pack(); 
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void buildGUI() {
		//1.makeNewSale()
		JPanel p_button1 = new JPanel(new GridLayout(0,1));
		b_makeNewSale = new JButton("1. make New Sale()");
		p_button1.add(b_makeNewSale);


		JPanel p_make = new JPanel();
		p_make.setLayout(new GridLayout(0,2,5,5));
		p_make.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		JLabel l_itemID= new JLabel("Item ID:   ");
		cb_itemID = new JComboBox<ItemID>();
		for(int i=0; i<v_id.size();i++) {
			cb_itemID.addItem(v_id.get(i));
		}

		//콤보박스에 선택된 값이 바뀔때마다 description텍스트필드값이 변경(익명클래스로 구현)
		cb_itemID.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String desc = (mRegister.getDesc((ItemID)cb_itemID.getSelectedItem())).toString();
				t_desc.setText(desc);
			}
		});

		JLabel l_quantity= new JLabel("Quantity: ");
		t_quantity = new JTextField(10);

		JLabel l_desc= new JLabel("Discription: ");
		t_desc = new JTextField(10);

		String desc = (mRegister.getDesc((ItemID)cb_itemID.getSelectedItem())).toString();
		t_desc.setText(desc);

		t_desc.setEditable(false);

		cb_itemID.setEnabled(false);
		t_quantity.setEnabled(false);

		p_make.add(l_itemID);
		p_make.add(cb_itemID);
		p_make.add(l_quantity);
		p_make.add(t_quantity);
		p_make.add(l_desc);
		p_make.add(t_desc);

		//2.enter Item()
		JPanel p_button2 = new JPanel(new GridLayout(0,1));
		b_enterItem = new JButton("2. enter Item() (반복)");
		b_enterItem.setEnabled(false);
		p_button2.add(b_enterItem);

		JPanel p_current = new JPanel();
		p_current.setLayout(new GridLayout(0,2,5,5));
		p_current.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		JLabel l_current= new JLabel("Current Total: ");
		t_current = new JTextField(10);
		t_current.setEditable(false);

		p_current.add(l_current);
		p_current.add(t_current);

		//3.end Sale()
		JPanel p_button3 = new JPanel(new GridLayout(0,1));
		b_endSale = new JButton("3. end Sale()");
		b_endSale.setEnabled(false);
		p_button3.add(b_endSale);

		JPanel p_endSale = new JPanel();
		p_endSale.setLayout(new GridLayout(0,2,5,5));
		p_endSale.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		tax = new ButtonGroup();
		taxMaster = new JRadioButton("TaxMaster");
		goodAsGold = new JRadioButton("GoodAsGoldTaxPro");
		taxMaster.addItemListener(new SelectTaxItemListener());
		goodAsGold.addItemListener(new SelectTaxItemListener());
		taxMaster.setEnabled(false);
		goodAsGold.setEnabled(false);
		tax.add(taxMaster);
		tax.add(goodAsGold);

		p_endSale.add(taxMaster);
		p_endSale.add(goodAsGold);

		//4.calculateTax()
		JPanel p_button4 = new JPanel(new GridLayout(0,1));
		b_calculateTax = new JButton("4. calculateTax()");
		b_calculateTax.setEnabled(false);
		p_button4.add(b_calculateTax);

		JPanel p_calTax = new JPanel();
		p_calTax.setLayout(new GridLayout(0,2,5,5));
		p_calTax.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		discount = new ButtonGroup();
		forCustomer = new JRadioButton("BestForCustomer");
		forStore = new JRadioButton("BestForStore");
		forCustomer.addItemListener(new SelectDiscountItemListener());
		forStore.addItemListener(new SelectDiscountItemListener());
		forCustomer.setEnabled(false);
		forStore.setEnabled(false);
		discount.add(forCustomer);
		discount.add(forStore);

		JLabel l_tax= new JLabel("Total with Tax: ");
		t_tax = new JTextField(10);
		t_tax.setEditable(false);

		p_calTax.add(l_tax);
		p_calTax.add(t_tax);
		p_calTax.add(forCustomer);
		p_calTax.add(forStore);

		//5.applyDiscount()
		JPanel p_button5 = new JPanel(new GridLayout(0,1));
		b_discount = new JButton("5. applyDiscount()");
		b_discount.setEnabled(false);
		p_button5.add(b_discount);

		JPanel p_discount = new JPanel();
		p_discount.setLayout(new GridLayout(0,2,5,5));
		p_discount.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		JLabel l_discount= new JLabel("Total with Discount: ");
		t_discount = new JTextField(10);
		t_discount.setEditable(false);

		JLabel l_amount= new JLabel("Amount: ");
		t_amount = new JTextField(10);
		
		//텍스트필드에 값이 입력될 경우 payment버튼 활성화(익명클래스로 구현)
		t_amount.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent arg0) {
				if(t_amount.getText().length()>0)
					b_makePayment.setEnabled(true);
				else
					b_makePayment.setEnabled(false);
			}
		});
		
		t_amount.setEnabled(false);

		p_discount.add(l_discount);
		p_discount.add(t_discount);
		p_discount.add(l_amount);
		p_discount.add(t_amount);

		//6.makePayment()
		JPanel p_button6 = new JPanel(new GridLayout(0,1));
		b_makePayment = new JButton("6. Make Payment()");
		b_makePayment.setEnabled(false);
		p_button6.add(b_makePayment);


		JPanel p_makePayment = new JPanel();
		p_makePayment.setLayout(new GridLayout(0,2,5,5));
		p_makePayment.setBorder(BorderFactory.createEmptyBorder(10 , 0 , 10 , 0));// 상좌하우

		JLabel l_balance = new JLabel("Balance:  ");
		t_balance = new JTextField(10);
		t_balance.setEditable(false);

		p_makePayment.add(l_balance);
		p_makePayment.add(t_balance);

		//오른쪽 JTextArea
		ta = new JTextArea(10,10);
		Border border = BorderFactory.createLineBorder(Color.BLACK); 
		ta.setBorder(border); 
		ta.setEditable(false); 
		ta.setFont(new Font("맑은고딕", Font.PLAIN, 12)); 

		Container c= this.getContentPane();
		c.setLayout(new GridLayout(0,2,20,20));
		((JComponent) c).setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 20));

		//크게 두개의 JPanel로 구성
		JPanel p_left = new JPanel();
		p_left.setLayout(new BoxLayout(p_left, BoxLayout.Y_AXIS));

		p_left.add(p_button1);
		p_left.add(p_make);
		p_left.add(p_button2);
		p_left.add(p_current);
		p_left.add(p_button3);
		p_left.add(p_endSale);
		p_left.add(p_button4);
		p_left.add(p_calTax);
		p_left.add(p_button5);
		p_left.add(p_discount);
		p_left.add(p_button6);
		p_left.add(p_makePayment);

		c.add(p_left);
		c.add(new JScrollPane(ta));
	}

	private void registerEventHandler() {
		b_makeNewSale.addActionListener(makeNewSaleHandler);

		b_enterItem.addActionListener(enterItemHandler);

		b_endSale.addActionListener(endSaleHandler);

		b_calculateTax.addActionListener(calculateTaxHandler);

		b_discount.addActionListener(discountHandler);

		b_makePayment.addActionListener(makePaymentHandler);
	}
	ActionListener makeNewSaleHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			sale = mRegister.makeNewSale();
			initialize(sale);

			cb_itemID.setEnabled(true);
			cb_itemID.setSelectedIndex(0);
			t_quantity.setEnabled(true);
			b_enterItem.setEnabled(true);
			b_makeNewSale.setEnabled(false);
			t_current.setText("");
			t_tax.setText("");
			t_amount.setText("");
			t_balance.setText("");
			t_discount.setText("");
			tax.clearSelection();
			discount.clearSelection();

			ta.append("새 판매가 시작되었습니다.\n");
		}
	};

	ActionListener enterItemHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				ItemID id = new ItemID(cb_itemID.getSelectedItem().toString());

				int quantity = Integer.parseInt(t_quantity.getText());
				mRegister.enterItem(id, quantity);

				String money = (mRegister.getPrice(id)).toString();

				t_quantity.setText("");
				b_makePayment.setEnabled(false);
				b_endSale.setEnabled(true);

				ta.append("제품명: "+t_desc.getText().toString()+" 수량: "+quantity+" 가격: "+money+" 입력.\n");
				ta.append("현재 합계: "+t_current.getText().toString()+"\n");
			}
			catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"Quantity에 숫자가 아닌 값이 들어갔습니다.","경고",JOptionPane.WARNING_MESSAGE);
				t_quantity.setText("");
			}
		}
	};

	ActionListener endSaleHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mRegister.endSale();

			b_calculateTax.setEnabled(false);
			cb_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			b_enterItem.setEnabled(false);
			t_amount.setEditable(true);
			taxMaster.setEnabled(true);
			goodAsGold.setEnabled(true);
			b_endSale.setEnabled(false);

			ta.append("판매가 종료되었습니다.\n");
			ta.append("총가격: "+t_current.getText().toString()+"\n");
		}
	};

	ActionListener calculateTaxHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			b_calculateTax.setEnabled(false);
			taxMaster.setEnabled(false);
			goodAsGold.setEnabled(false);
			forCustomer.setEnabled(true);
			forStore.setEnabled(true);

			Money taxTotal = mRegister.calculateTax();
			t_tax.setText(taxTotal.toString());
			
			if(taxMaster.isSelected()) {
				ta.append("TaxMaster가 적용이 되어 "+t_tax.getText().toString()+"이 되었습니다.\n");
			}
			else if(goodAsGold.isSelected()) {
				ta.append("GoodAsGoldTaxPro가 적용이 되어 "+t_tax.getText().toString()+"이 되었습니다.\n");
			}
		}

	};

	ActionListener discountHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			b_discount.setEnabled(false);
			forCustomer.setEnabled(false);
			forStore.setEnabled(false);
			t_amount.setEnabled(true);

			Money discountTotal = mRegister.applyDiscount();
			t_discount.setText(discountTotal.toString());
			
			if(forCustomer.isSelected()) {
				ta.append("BestForCustomer가 적용되어 할인된 가격은 "+t_discount.getText().toString()+"입니다.\n");
			}
			if(forStore.isSelected()) {
				ta.append("BestForStore가 적용되어 할인된 가격은 "+t_discount.getText().toString()+"입니다.\n");
			}
		}

	};

	ActionListener makePaymentHandler = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			mRegister.makePayment(new Money(Integer.parseInt(t_amount.getText().toString())));
			t_balance.setText(String.valueOf(sale.getBalance()));
			
			cb_itemID.setEnabled(false);
			t_quantity.setEnabled(false);
			b_enterItem.setEnabled(false);
			b_endSale.setEnabled(false);
			b_makePayment.setEnabled(false);
			b_makeNewSale.setEnabled(true);
			t_amount.setEnabled(false);

			ta.append("받은 금액은 "+t_amount.getText().toString()+"이며\n");
			ta.append("잔액은 "+t_balance.getText().toString()+"입니다.\n");
			ta.append("판매 종료\n");
			}
			catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"Amount에 숫자가 아닌 값이 들어갔습니다.","경고",JOptionPane.WARNING_MESSAGE);
				t_amount.setText("");
			}
		}
	};
	
	//라디오버튼 리스너 : 버튼 누르기 직전에 객체 생성
	class SelectDiscountItemListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if(e.getStateChange()==ItemEvent.SELECTED)
			{
				if(forCustomer.isSelected()) {
					b_discount.setEnabled(true);
					mRegister.createStrategy("customer");
				}
				else if(forStore.isSelected()) {
					b_discount.setEnabled(true);
					mRegister.createStrategy("store");
				}
			}
		}
	}
	class SelectTaxItemListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			if(e.getStateChange()==ItemEvent.SELECTED)
			{
				if (taxMaster.isSelected())
				{
					b_calculateTax.setEnabled(true);
					System.setProperty("taxcalculator.class.name", "pos.domainlayer."+taxMaster.getText()+"Adapter");
				}
				else if (goodAsGold.isSelected())
				{
					b_calculateTax.setEnabled(true);
					System.setProperty("taxcalculator.class.name", "pos.domainlayer."+goodAsGold.getText()+"Adapter");
				}
			}
		}
	}

	@Override
	public void onPropertyEvent(Sale source, String name, Money value) {
		if(name.equals("sale.total"))
			t_current.setText(value.toString());
	}
	
	public void initialize(Sale s) {
		s.addPropertyListener(this);
	}
}
