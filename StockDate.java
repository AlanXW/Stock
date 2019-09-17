public class StockDate {
	private int year;
	private int month;
	private int day;
	public StockDate() {
		
	}
	public StockDate(int year,int month,int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	public boolean BigerData(StockDate data) {
		if(this.year>data.year) {
			return true;
		}else if(this.year == data.year) {
			if(this.month>data.month) {
				return true;
			}else if(this.month == data.month) {
				if(this.day>=data.day) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getDate() {
		StringBuilder sb=new StringBuilder();
		sb.append("").append(year);
		sb.append("-").append(month);
		sb.append("-").append(day);
		return sb.toString();
	}
}
