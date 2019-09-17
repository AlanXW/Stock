import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class MakeAllShare extends XYChart<String, Number> {
	
	protected static final Logger logger = Logger.getLogger(MakeAllShare.class.getName()); 
	protected int maxBarsToDisplay;  
	protected ObservableList<XYChart.Series<String, Number>> dataSeries; 
	protected Shares lastShares;  
	protected NumberAxis yAxis;   
	protected CategoryAxis xAxis;  
	
	public MakeAllShare(String title,ArrayList<Shares> shareList){
		this(title,shareList,Integer.MAX_VALUE);
	}

	public MakeAllShare(String title, ArrayList<Shares> shareList, int maxValue) {
		this(title, new CategoryAxis(),new NumberAxis(),shareList,maxValue);
	}
	
	
	
	public MakeAllShare(String title,CategoryAxis xAxis,NumberAxis yAxis,ArrayList<Shares> shareList,int maxValue) {
		super(xAxis, yAxis);
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.maxBarsToDisplay = maxValue;
		
		yAxis.autoRangingProperty().set(true);
		yAxis.forceZeroInRangeProperty().setValue(Boolean.FALSE);
		setTitle(title);
		setAnimated(true);
		getStylesheets().add(getClass().getResource("kstyle.css").toExternalForm());
		xAxis.setAnimated(true);
		yAxis.setAnimated(true);
		verticalGridLinesVisibleProperty().set(false);
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		ArrayList<Shares> list = getSubList(shareList, maxValue);
		for(Shares share:list) {
			String label = "";
			label = share.getData().getDate();
			series.getData().add(new XYChart.Data<>(label,share.getOpen(),share));
		}
		dataSeries = FXCollections.observableArrayList(series);
		setData(dataSeries);
		lastShares = list.get(list.size()-1);
	}
	
	public void setYAxisFormatter(DecimalAxisFormatter formatter) {
		yAxis.setTickLabelFormatter(formatter);
	}
	
	public void addShare(Shares share) {
		if(dataSeries.get(0).getData().size()>= maxBarsToDisplay) {
			dataSeries.get(0).getData().remove(0);
		}
		int datalength = dataSeries.get(0).getData().size();
		dataSeries.get(0).getData().get(datalength-1).setYValue(share.getOpen());
		dataSeries.get(0).getData().get(datalength-1).setExtraValue(share);
		String label = share.getData().getDate();
		
		lastShares = new Shares(share.getData(), share.getOpen(), share.getHigh(), share.getLow(), share.getClose(), share.getVolume());
		Data<String, Number> data = new XYChart.Data<String, Number>(label,lastShares.getOpen(),lastShares);
		dataSeries.get(0).getData().add(data);
		
		
	}
	
	public void updateLast(double price) {
        if (lastShares != null) {
        	lastShares.update(price);

            int datalength = dataSeries.get(0).getData().size();
            dataSeries.get(0).getData().get(datalength - 1).setYValue(lastShares.getOpen());

            dataSeries.get(0).getData().get(datalength - 1).setExtraValue(lastShares);
        }
    }
	
	protected ArrayList<Shares> getSubList(ArrayList<Shares> list,int maxBars){
		if (list.size()>maxBars) {
			return (ArrayList<Shares>) list.subList(list.size()-1-maxBars, list.size()-1);
		}else {
			return list;
		}
	}
	
	@Override
	protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
		// TODO Auto-generated method stub
		Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        if (shouldAnimate()) {
            candle.setOpacity(0);
            getPlotChildren().add(candle);
            // fade in new candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(1);
            ft.play();
        } else {
            getPlotChildren().add(candle);
        }
        // always draw average line on top
        if (series.getNode() != null) {
            series.getNode().toFront();
        }
	}

	@Override
	protected void dataItemChanged(Data<String, Number> item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
		// TODO Auto-generated method stub
		final Node candle = item.getNode();
        if (shouldAnimate()) {
            // fade out old candle
            FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
            ft.setToValue(0);
            ft.setOnFinished((ActionEvent actionEvent) -> {
                getPlotChildren().remove(candle);
            });
            ft.play();
        } else {
            getPlotChildren().remove(candle);
        }	
	}

	@Override
	protected void layoutPlotChildren() {
		// TODO Auto-generated method stub
		if(getData() == null) {
			return;
		}
		
		for(int seriesIndex=0;seriesIndex < getData().size();seriesIndex++) {
			Series<String, Number> series = getData().get(seriesIndex);
			Iterator<Data<String, Number>> iter = getDisplayedDataIterator(series);
			Path seriesPath = null;
			if(series.getNode() instanceof Path) {
				seriesPath = (Path) series.getNode();
				seriesPath.getElements().clear();
			}
			
			while (iter.hasNext()) {
				Data<String, Number> item = iter.next();
				double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));
				double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));
				Node itemNode = item.getNode();
				Shares share = (Shares) item.getExtraValue();
				 if (itemNode instanceof Candle && item.getYValue() != null) {
	                    Candle candle = (Candle) itemNode;

	                    double close = getYAxis().getDisplayPosition(share.getClose());
	                    double high = getYAxis().getDisplayPosition(share.getHigh());
	                    double low = getYAxis().getDisplayPosition(share.getLow());
	                    double candleWidth = 10;
	                    // update candle
	                    candle.update(close - y, high - y, low - y, candleWidth);

	                    // update tooltip content
	                    candle.updateTooltip(share.getOpen(), share.getClose(), share.getHigh(), share.getLow());

	                    // position the candle
	                    candle.setLayoutX(x);
	                    candle.setLayoutY(y);
	                }
			}
			
		}
	}

	@Override
	protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
		// TODO Auto-generated method stub
		 for (int j = 0; j < series.getData().size(); j++) {
	            Data item = series.getData().get(j);
	            Node candle = createCandle(seriesIndex, item, j);
	            if (shouldAnimate()) {
	                candle.setOpacity(0);
	                getPlotChildren().add(candle);
	                // fade in new candle
	                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
	                ft.setToValue(1);
	                ft.play();
	            } else {
	                getPlotChildren().add(candle);
	            }
	        }
	        // create series path
	        Path seriesPath = new Path();
	        seriesPath.getStyleClass().setAll("candlestick-average-line", "series" + seriesIndex);
	        series.setNode(seriesPath);
	        getPlotChildren().add(seriesPath);
	}

	@Override
	protected void seriesRemoved(Series<String, Number> series) {
		// TODO Auto-generated method stub
		for (XYChart.Data<String, Number> d : series.getData()) {
            final Node candle = d.getNode();
            if (shouldAnimate()) {
                // fade out old candle
                FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                ft.setToValue(0);
                ft.setOnFinished((ActionEvent actionEvent) -> {
                    getPlotChildren().remove(candle);
                });
                ft.play();
            } else {
                getPlotChildren().remove(candle);
            }
        }
	}
	
	
	 private Node createCandle(int seriesIndex, final Data item, int itemIndex) {
	        Node candle = item.getNode();
	        // check if candle has already been created
	        if (candle instanceof Candle) {
	            ((Candle) candle).setSeriesAndDataStyleClasses("series" + seriesIndex, "data" + itemIndex);
	        } else {
	            candle = new Candle("series" + seriesIndex, "data" + itemIndex);
	            item.setNode(candle);
	        }
	        return candle;
	    }
	 
	 @Override
	 protected void updateAxisRange() {
	        // For candle stick chart we need to override this method as we need to let the axis know that they need to be able
	        // to cover the whole area occupied by the high to low range not just its center data value
	      final Axis<String> xa = getXAxis();
	      final Axis<Number> ya = getYAxis();
	      List<String> xData = null;
	        List<Number> yData = null;
	        if (xa.isAutoRanging()) {
	            xData = new ArrayList<>();
	        }
	        if (ya.isAutoRanging()) {
	            yData = new ArrayList<>();
	        }
	        if (xData != null || yData != null) {
	            for (Series<String, Number> series : getData()) {
	                for (Data<String, Number> data : series.getData()) {
	                    if (xData != null) {
	                        xData.add(data.getXValue());
	                    }
	                    if (yData != null) {
	                        Shares extras = (Shares) data.getExtraValue();
	                        if (extras != null) {
	                            yData.add(extras.getHigh());
	                            yData.add(extras.getLow());
	                        } else {
	                            yData.add(data.getYValue());
	                        }
	                    }
	                }
	            }
	            if (xData != null) {
	                xa.invalidateRange(xData);
	            }
	            if (yData != null) {
	                ya.invalidateRange(yData);
	            }
	        }
	    }
	
	
	
	private class Candle extends Group{
		private final Line highLowLine = new Line();
        private final Region bar = new Region();
        private String seriesStyleClass;
        private String dataStyleClass;
        private boolean openAboveClose = true;
        private final Tooltip tooltip = new Tooltip();

        private Candle(String seriesStyleClass, String dataStyleClass) {
            setAutoSizeChildren(false);
            getChildren().addAll(highLowLine, bar);
            this.seriesStyleClass = seriesStyleClass;
            this.dataStyleClass = dataStyleClass;
            updateStyleClasses();
            tooltip.setGraphic(new TooltipContent());
            Tooltip.install(bar, tooltip);
        }

        public void setSeriesAndDataStyleClasses(String seriesStyleClass, String dataStyleClass) {
            this.seriesStyleClass = seriesStyleClass;
            this.dataStyleClass = dataStyleClass;
            updateStyleClasses();
        }

        public void update(double closeOffset, double highOffset, double lowOffset, double candleWidth) {
            openAboveClose = closeOffset > 0;
            updateStyleClasses();
            highLowLine.setStartY(highOffset);
            highLowLine.setEndY(lowOffset);
            if (candleWidth == -1) {
                candleWidth = bar.prefWidth(-1);
            }
            if (openAboveClose) {
                bar.resizeRelocate(-candleWidth / 2, 0, candleWidth, closeOffset);
            } else {
                bar.resizeRelocate(-candleWidth / 2, closeOffset, candleWidth, closeOffset * -1);
            }
        }

        public void updateTooltip(double open, double close, double high, double low) {
            TooltipContent tooltipContent = (TooltipContent) tooltip.getGraphic();
            tooltipContent.update(open, close, high, low);
        }

        private void updateStyleClasses() {
            getStyleClass().setAll("candlestick-candle", seriesStyleClass, dataStyleClass);
            highLowLine.getStyleClass().setAll("candlestick-line", seriesStyleClass, dataStyleClass,
                    openAboveClose ? "open-above-close" : "close-above-open");
            bar.getStyleClass().setAll("candlestick-bar", seriesStyleClass, dataStyleClass,
                    openAboveClose ? "open-above-close" : "close-above-open");
        }
	}
	
	private class TooltipContent extends GridPane{
		private final Label openValue = new Label();
        private final Label closeValue = new Label();
        private final Label highValue = new Label();
        private final Label lowValue = new Label();

        private TooltipContent() {
            Label open = new Label("OPEN:");
            Label close = new Label("CLOSE:");
            Label high = new Label("HIGH:");
            Label low = new Label("LOW:");
            open.getStyleClass().add("candlestick-tooltip-label");
            close.getStyleClass().add("candlestick-tooltip-label");
            high.getStyleClass().add("candlestick-tooltip-label");
            low.getStyleClass().add("candlestick-tooltip-label");
            setConstraints(open, 0, 0);
            setConstraints(openValue, 1, 0);
            setConstraints(close, 0, 1);
            setConstraints(closeValue, 1, 1);
            setConstraints(high, 0, 2);
            setConstraints(highValue, 1, 2);
            setConstraints(low, 0, 3);
            setConstraints(lowValue, 1, 3);
            getChildren().addAll(open, openValue, close, closeValue, high, highValue, low, lowValue);
        }

        public void update(double open, double close, double high, double low) {
            openValue.setText(Double.toString(open));
            closeValue.setText(Double.toString(close));
            highValue.setText(Double.toString(high));
            lowValue.setText(Double.toString(low));
        }
	}
	
	 protected static MakeAllShare chart;
	
	

}
