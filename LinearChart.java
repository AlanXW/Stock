import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class LinearChart extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Closing price");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis,yAxis);
		
		lineChart.setTitle(MarketGUI.shareName+"'s closing price");
		
		XYChart.Series series = getDatabase();
		Scene scene = new Scene(lineChart,800,600);
		lineChart.getData().add(series);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static XYChart.Series getDatabase() {
		XYChart.Series series = new XYChart.Series();
		for(int i=0;i<MarketGUI.allShareLlist.size();i++) {
			String date = MarketGUI.allShareLlist.get(i).getData().getYear()+"-"+MarketGUI.allShareLlist.get(i).getData().getMonth()+"-"+MarketGUI.allShareLlist.get(i).getData().getDay();
			series.getData().add(new XYChart.Data(date, MarketGUI.allShareLlist.get(i).getClose()));
		}
		return series;
	}
	
	public static void init(String args) {
		launch(args);
	}
	
}
