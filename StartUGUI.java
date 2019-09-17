import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class StartUGUI extends JFrame implements ActionListener {
	
	JComboBox  stockjcb,startyearjcb,startmonthjcb,startdayjcb;
	JComboBox  endyearjcb,endmonthjcb,enddayjcb,patterndayjcb;
	JLabel startTimeLabel,endTimeLabel,patternName;
	JFrame frame;
	public StartUGUI() {
		frame = new JFrame("Please select the appropriate conditions");
		frame.setSize(450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		String year[] = {"2018"};
		String month[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		String day[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"
				,"24","25","26","27","28","29","30","31"};
		String tag[] = {"Streamline","Complex"}; 
		
		JLabel userLabel = new JLabel("Stock Name:");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		
		stockjcb = new JComboBox(MarketGUI.stock);
		stockjcb.setBounds(100, 20, 80, 25);
		panel.add(stockjcb);
		
		startTimeLabel = new JLabel("Start Time:");
		startTimeLabel.setBounds(10, 50, 80, 25);
		panel.add(startTimeLabel);
		
		
		startyearjcb = new JComboBox(year);
		startyearjcb.setBounds(100, 50, 80, 25);
		panel.add(startyearjcb);
		
		startmonthjcb = new JComboBox(month);
		startmonthjcb.setBounds(200, 50, 80, 25);
		panel.add(startmonthjcb);
		
		startdayjcb = new JComboBox(day);
		startdayjcb.setBounds(300, 50, 80, 25);
		panel.add(startdayjcb);
		
		
		endTimeLabel = new JLabel("End Time:");
		endTimeLabel.setBounds(10, 80, 80, 25);
		panel.add(endTimeLabel);
		
		
		endyearjcb = new JComboBox(year);
		endyearjcb.setBounds(100, 80, 80, 25);
		panel.add(endyearjcb);
		
		endmonthjcb = new JComboBox(month);
		endmonthjcb.setBounds(200, 80, 80, 25);
		panel.add(endmonthjcb);
		
		enddayjcb = new JComboBox(day);
		enddayjcb.setBounds(300, 80, 80, 25);
		panel.add(enddayjcb);
		
		patternName = new JLabel("Pattern:");
		patternName.setBounds(10, 120, 80, 25);
		panel.add(patternName);
		
		patterndayjcb = new JComboBox(tag);
		patterndayjcb.setBounds(100, 120, 120, 25);
		panel.add(patterndayjcb);
		
		JButton loginButton = new JButton("Chick");
        loginButton.setBounds(100, 160, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);
           
        frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String stock = (String) stockjcb.getSelectedItem();
		StockDate startdata = new StockDate();
		startdata.setYear(Integer.parseInt((String)startyearjcb.getSelectedItem()));
		startdata.setMonth(Integer.parseInt((String)startmonthjcb.getSelectedItem()));
		startdata.setDay(Integer.parseInt((String)startdayjcb.getSelectedItem()));
		
		StockDate enddata = new StockDate();
		enddata.setYear(Integer.parseInt((String)endyearjcb.getSelectedItem()));
		enddata.setMonth(Integer.parseInt((String)endmonthjcb.getSelectedItem()));
		enddata.setDay(Integer.parseInt((String)enddayjcb.getSelectedItem()));
		
		String path = "data/"+stock+".csv";
		MarketGUI.allShareLlist = Operation.ReadCSV(path,startdata,enddata);
		MarketGUI.shareName = stock;
		Collections.reverse(MarketGUI.allShareLlist);
		if(startdata.BigerData(enddata)) {
			JOptionPane.showMessageDialog(null, "The start time needs to be less than the end time");
		}else {
			frame.setVisible(false);
			frame.dispose();
			if (patterndayjcb.getSelectedItem().equals("Streamline")) {
				LinearChart.init("");
			}else{
				KLineAllChart.allInit("");
			}
		}
		
		
		
	}
}
