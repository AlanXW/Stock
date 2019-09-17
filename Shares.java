public class Shares {
	private StockDate data;
	private double open;
	private double high;
	private double low;
	private double close;
	private double volume;
	public Shares() {
		
	}
	
	public Shares(StockDate data,double open,double high,double low,double close,double volume) {
		this.data = data;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}
	
	public StockDate getData() {
		return data;
	}
	public void setData(StockDate data) {
		this.data = data;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Date:").append(data.getDate());
		sb.append(" Open:").append(open);
		sb.append(" High:").append(high);
		sb.append(" Low:").append(low);
		sb.append(" Close:").append(close);
		sb.append(" Volume:").append(volume);
		return sb.toString();
	}

	public void update(double close) {
		// TODO Auto-generated method stub
		if (close>high) {
			high = close;
		}
		if(close<low) {
			low= close;
		}
		this.close = close;
		
	}
	
}
