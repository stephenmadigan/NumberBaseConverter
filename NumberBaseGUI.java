//Stephen Madigan
//Assignment 1
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NumberBaseGUI implements ChangeListener {
	
	private JColorChooser tcc;
	private JLabel banner;
		
	private JFrame colors = new JFrame("Colors");
	private JFrame frm = new JFrame("Number Converter");
	
	private JLabel decimalLbl = new JLabel("Decimal");
	private JLabel binaryLbl = new JLabel("Binary");
	private JLabel octalLbl = new JLabel("Octal");
	private JLabel hexLbl = new JLabel("Hexadecimal");
	private JLabel charLbl = new JLabel("Character(s) (Limit 4)");
	private JLabel floatLbl = new JLabel("Float Decimal");

	private JTextField decimalTxt = new JTextField(20);
	private JTextField binaryTxt = new JTextField(20);
	private JTextField octalTxt = new JTextField(20);
	private JTextField hexTxt = new JTextField(20);
	private JTextField charTxt = new JTextField(20);
	private JTextField floatTxt = new JTextField(20);

	//buttons
	private JButton okBtn = new JButton("OK");
	private JButton convertBtn = new JButton("Convert");
	private JButton clearBtn = new JButton("Clear");
	private JButton chooseColorBtn = new JButton("Select Color");
	
	private JPanel pnlMain = new JPanel();
	private JPanel pnlCenter = new JPanel();
	private JPanel pnlBottom = new JPanel();
	private JPanel colorDisplay = new JPanel();

	
	public NumberBaseGUI() {
		banner = new JLabel();
		banner.setPreferredSize(new Dimension(0, 0));
		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner, BorderLayout.CENTER);
		
		tcc = new JColorChooser(banner.getForeground());
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Color"));
		bannerPanel.add(banner, BorderLayout.CENTER);
		bannerPanel.add(tcc, BorderLayout.PAGE_END);
		bannerPanel.add(okBtn);
				
		pnlMain.setLayout(new BorderLayout());
		pnlCenter.setLayout(new GridLayout(7, 2));
		pnlCenter.add(decimalLbl);
		pnlCenter.add(decimalTxt);
		pnlCenter.add(binaryLbl);
		pnlCenter.add(binaryTxt);
		pnlCenter.add(octalLbl);
		pnlCenter.add(octalTxt);
		pnlCenter.add(hexLbl);
		pnlCenter.add(hexTxt);	
		pnlCenter.add(charLbl);
		pnlCenter.add(charTxt);
		
		//color button
		pnlCenter.add(colorDisplay);
		pnlCenter.add(chooseColorBtn);
		pnlCenter.add(floatLbl);
		pnlCenter.add(floatTxt);
		
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlBottom.add(convertBtn);
		pnlBottom.add(clearBtn);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		
		clearBtn.addActionListener(
				
				new ActionListener() { //clears all input+color
			
			@Override
			public void actionPerformed(ActionEvent e) {
				decimalTxt.setText("");
				binaryTxt.setText("");
				octalTxt.setText("");
				hexTxt.setText("");
				floatTxt.setText("");
				charTxt.setText("");
				colorDisplay.setBackground(new Color(255, 255, 255));		
			}
		});
		
		chooseColorBtn.addActionListener(
				
				new ActionListener() { //opens up color choice menu
				
			@Override
			public void actionPerformed(ActionEvent e) {
				colors.setVisible(true);
				
			}
		});			
		
		okBtn.addActionListener(
				
				new ActionListener() { //sends color choice
			
			@Override
			public void actionPerformed(ActionEvent e) {
				binaryTxt.setText(colorToBinary(tcc.getColor()));
				decimalTxt.setText(baseToDecimal(binaryTxt.getText(), 2));
				octalTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 8, true));
				hexTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 16, true));
				floatTxt.setText(Float.toString(toFloat(binaryTxt.getText())));
				colorDisplay.setBackground(tcc.getColor());
				colors.dispose();
			}
		});
		
		convertBtn.addActionListener(
				
				new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!decimalTxt.getText().equals("")) { //if decimal is not empty
					binaryTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 2, true));
					updateAll();
				}
				else if (!binaryTxt.getText().equals("")) { //if binary text is not empty
					decimalTxt.setText(baseToDecimal(binaryTxt.getText(), 2));
					updateAll();
				}
				else if (!octalTxt.getText().equals("")) { //if octal text is not empty
					decimalTxt.setText(baseToDecimal(octalTxt.getText(), 8));
					updateAll();

				}
				else if (!hexTxt.getText().equals("")) {
					decimalTxt.setText(baseToDecimal(hexTxt.getText(), 16));
					updateAll();
				}		
				
				else if (!charTxt.getText().equals("")) {
					binaryTxt.setText(charToBinary(charTxt.getText()));
					updateAll();
				}
				
				else if (!floatTxt.getText().contentEquals("")) {
					binaryTxt.setText(floatToBinary(floatTxt.getText()));
					updateAll();
				}
			}
		});

		frm.add(pnlMain);
		frm.setPreferredSize(new Dimension(500, 500));
		frm.setVisible(true);
		frm.pack();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		colors.add(bannerPanel);
		colors.setPreferredSize(new Dimension(500, 500));
		colors.pack();

	}
	
	public static String numToChar(String num) {//32bit binary num to character
		String s = "";
		String char1String = num.substring(0,8);
		String char2String = num.substring(8, 16);
		String char3String = num.substring(16, 24);
		String char4String = num.substring(24, 32);
		char char1 = (char) Integer.parseInt(baseToDecimal(char1String, 2));
		char char2 = (char) Integer.parseInt(baseToDecimal(char2String, 2));
		char char3 = (char) Integer.parseInt(baseToDecimal(char3String, 2));
		char char4 = (char) Integer.parseInt(baseToDecimal(char4String, 2));
		s += char1 + "" + char2 + "" +  char3 + "" + char4;	
		return s;	
	}
	
	public static String charToBinary(String characterInput) {// converts a set of 1-4 characters to a binary string
		String s = "";
		for (int i = 0; i < characterInput.length(); i ++) { //limit 4 chars/32bit
			int q = characterInput.charAt(i);
			String temp = decimalToBase(q, 2, false);
			while (temp.length() != 8) { //make 8 bits
				temp = "0" + temp;
			}
			s += temp;	
		}
		return s;
	}
	
	public static Color binaryToColor(String num) { //converts binary value to integer RGB values, into color.
		//String omegaVal = num.substring(0, 8); //not needed (?)
		String redVal = num.substring(8, 16);
		String greenVal = num.substring(16, 24);
		String blueVal = num.substring(24, 32);
		return new Color(Integer.parseInt(baseToDecimal(redVal, 2)), Integer.parseInt(baseToDecimal(greenVal, 2)),
					Integer.parseInt(baseToDecimal(blueVal, 2)));
	}
	
	public static String colorToBinary(Color c) { //converts alpha+RBG color into 32 bit binary.
		String binaryNumber = "";
		binaryNumber += Integer.toBinaryString(c.getAlpha());
		binaryNumber += Integer.toBinaryString(c.getRed());
		binaryNumber += Integer.toBinaryString(c.getGreen());
		binaryNumber += Integer.toBinaryString(c.getBlue());
		return binaryNumber;
		
	}

	public static String baseToDecimal(String num, int base) { //converts any number base x to decimal (base 10)
		int length = num.length()-1;
		int val = 0;
		if (num.charAt(0) == '0' && num.charAt(1) == 'x') {//checks for hex numbers leading w/ 0x
			String temp = num.substring(2, num.length());
			num = temp;
			length = length - 2;
		}
		for (int i = 0; i <= length; i++) {
			if (num.charAt(i) != '0') {
				if (!Character.isDigit(num.charAt(i) ) ) {//if hex
					val += getInt(num.charAt(i)) * Math.pow(base, length - i);
				}
				else {
					val += (Character.getNumericValue(num.charAt(i)) * Math.pow(base, length - i));	
				}
			}
		}
		return Integer.toString(val);
	}
	
	public static String decimalToBase(int num, int base, boolean addLeading0s) { //converts any decimal number to any base (eg binary/octal/hex)
		String baseNumString = "";
		while (num != 0) {
			if (num % base == 0) { //remainder 0;
				num = num / base;
				baseNumString = "0"+ baseNumString;
			}
			else { // num%2 != 0
				int temp = num % base;
				num = num / base;
				if (temp < 10) { //is digit 0-9, not hexidecial
					baseNumString = temp + baseNumString;
				}
				else { //hexidecimal, 10-15
					baseNumString = getHex(temp) + baseNumString;
				}
			}		
		}
		if (base == 16) { //hex is formatted "0x____"
			baseNumString = "0x" + baseNumString;
		}
		if (base == 2 && (addLeading0s == true)) {  //binary we want to show all 32 bits.
			while (baseNumString.length() < 32 ) {
				baseNumString = "0" + baseNumString;
			}	
		}
		return baseNumString;
	}
	
	public static int getInt(char ch) {//hexidecimal char->number conversion
		//throw exception if not A-F
		if (ch == 'A') {
			return 10;
		}
		if (ch == 'B') {
			return 11;
		}
		if (ch == 'C') {
			return 12;
		}
		if (ch == 'D') {
			return 13;
		}
		if (ch == 'E') {
			return 14;
		}
		else {//F
			return 15;
		}

		
	}
	public static char getHex(int num) {//hexidecimal num-> char conversion
		//exception if num = 0-9, >15, negative
		if(num == 10) {
			return 'A';
		}
		if (num == 11) {
			return 'B';
		}
		if (num == 12) {
			return 'C';
		}
		if (num == 13) {
			return 'D';
		}
		if (num == 14) {
			return 'E';
		}
		else { //num == 15
			return 'F';
		}
	}
	
	
	public static float toFloat(String num) { //num = binary number
		while (num.length() < 32) {
			num = "0" + num; 
		}
		String sign = "" + num.charAt(0);
		String exponent = "";
		String mantissa = "";
		for (int i = 1; i < 9; i ++) {
			exponent = exponent + num.charAt(i);
		}
		for (int i = 9; i <32; i++ ) {
			mantissa = mantissa + num.charAt(i);
		}
		int exponentVal = 0;
		for (int i = 7; i >= 0; i--) {
			if (exponent.charAt(i) == '1' ) {
				//System.out.println(Math.pow(2, 7-i));
				exponentVal = (int) (exponentVal +  Math.pow(2, 7-i)) ;
			}
		}
		//for testing
		//System.out.println("sign = " + sign);
		//System.out.println("exponent = " + exponent);
		//System.out.println("mantissa = " + mantissa);
		//System.out.println("exponent value = " + (exponentVal - 127));
		float a = 0;
		mantissa = "1" + mantissa;
		for (int i = 0; i < 24; i ++) {
			if (mantissa.charAt(i) == '1') {
				a += (1 / Math.pow(2, (i)));
			}
		}
		float x  = (float) (a * Math.pow(2, (exponentVal - 127)));
		if (num.charAt(0) == '1') {
			x = (x * -1);
		}
		return x;
	}
	
	public String floatToBinary(String value) {
		float a = Float.parseFloat(value);
		int intBits = Float.floatToIntBits(a);
		String binary = Integer.toBinaryString(intBits);
		while (binary.length() < 32) {
			binary = "0" + binary;
		}
		return binary;
	}
	
//	public String floatToBinary(String value) {//wip
		//String temp = "";
		//float a = Float.parseFloat(value);
		//float b = Float.parseFloat(value);
		//int count = 0;
		//while (a > 1.0) {
			//System.out.println(a);
			//System.out.println( (int) (a % 2));
			//temp += "" + (int) (a % 2);
			//a = a / 2;
			//count ++;
			//System.out.println(a);
		//}
		//float val = a;
		//System.out.println(val);
		//val = (float) (val * Math.pow(2, 23));
		//System.out.println(Math.pow(2, 23));
		//System.out.println(val);
		//int intb = Float.floatToIntBits(val);
		//String binary = Integer.toBinaryString(intb);
		//System.out.println(binary);
		//System.out.println("exponent = " + count);
		//System.out.println(temp);
		//float mantissa = (float) (b / (Math.pow(2, (count + 127))));
		//System.out.println("mantissa = " + mantissa);
		//int intbits	= Float.floatToIntBits(mantissa);
		//String binary = Integer.toBinaryString(intbits);
		//System.out.println(binary);
		//return temp;
	//}
	
	
	public void updateAll() { //update function (requires either binary or decimal text to exist prior.
		if (!binaryTxt.getText().equals("")) {
			decimalTxt.setText(baseToDecimal(binaryTxt.getText(), 2));
		}
		if (!decimalTxt.getText().equals("")) {
			binaryTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 2, true));
		}
		octalTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 8, true));
		hexTxt.setText(decimalToBase(Integer.valueOf(decimalTxt.getText()), 16, true));
		floatTxt.setText(Float.toString(toFloat(binaryTxt.getText())));
		colorDisplay.setBackground(binaryToColor(binaryTxt.getText()));
		charTxt.setText(numToChar(binaryTxt.getText()));
	}

	


	public static void main(String[] args) {
		NumberBaseGUI app = new NumberBaseGUI();
	}

	@Override
	public void stateChanged(ChangeEvent e) {//for color button
		Color newColor = tcc.getColor();
		banner.setForeground(newColor);
	}
}


