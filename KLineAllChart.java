

import java.util.ArrayList;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class KLineAllChart extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		String string = MarketGUI.shareName+"'s price";
		MakeAllShare makeAllShare = new MakeAllShare(string,MarketGUI.allShareLlist);
		Scene scene = new Scene(makeAllShare);
		scene.getStylesheets().add("kstyle.css");
		
		primaryStage.setTitle("Stock");
		primaryStage.setScene(scene);
		newStage();
		primaryStage.show();
	}
	
	public void newStage() {
		Stage secondarySageStage = new Stage();
		String string = MarketGUI.shareName+"'s volume";
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		final BarChart<String, Number> bChart = new BarChart<String, Number>(xAxis, yAxis);
		bChart.setTitle(string);
		xAxis.setLabel("date");
		yAxis.setLabel("Volume");
		
		XYChart.Series series= new XYChart.Series();
		
		for(int i=0;i<MarketGUI.allShareLlist.size();i++) {
			String dateString = MarketGUI.allShareLlist.get(i).getData().getDate();
			double volum =  MarketGUI.allShareLlist.get(i).getVolume();
			series.getData().add(new XYChart.Data(dateString,volum));
		}
		
		Scene scene = new Scene(bChart,600,400);
		bChart.getData().add(series);
		secondarySageStage.setScene(scene);
		secondarySageStage.setTitle("Volume");
		secondarySageStage.show();
	}
	
	
	
	
	
	public static void allInit(String args) {
		launch(args);
	}

}
