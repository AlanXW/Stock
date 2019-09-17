import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MarketGUI {
	public static String stock[] = {"AAPL","BRK.A","C","GOOG","HOG","HPQ","INTC","KO"};
	public static ArrayList<Shares> allShareLlist = new ArrayList<Shares>();
	public static String shareName = "";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new StartUGUI();
//		StockDate startData = new StockDate();
//		startData.setYear(2018);
//		startData.setMonth(1);
//		startData.setDay(26);
//		StockDate endData = new StockDate();
//		endData.setYear(2018);
//		endData.setMonth(2);
//		endData.setDay(26);
//		Operation.ReadCSV("data/AAPL.csv", startData, endData);

	}
}

