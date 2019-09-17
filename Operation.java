import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Operation {
	public static ArrayList<Shares> ReadCSV(String path,StockDate startData,StockDate endData) {
		ArrayList<Shares> sharelist = new ArrayList();        
        BufferedReader br=null;
        try { 
            br = new BufferedReader(new FileReader(path));
            String line = ""; 
            int flag = 0;
            while ((line = br.readLine()) != null) {
            	if(flag==0) {
            		flag ++;
            	}else {
            		flag ++;
            		String []strings = line.split(",");
            		String daString[] = strings[0].split("/");
            		String year = "20"+daString[2];
    				StockDate data = new StockDate(Integer.parseInt(year), Integer.parseInt(daString[0]), Integer.parseInt(daString[1]));
    				if((data.BigerData(startData)) && (!data.BigerData(endData))) {
						double openPrice = StringToDouble(strings[1]);
						double highPrice = StringToDouble(strings[2]);
						double lowPrice = StringToDouble(strings[3]);
						double closePrice = StringToDouble(strings[4]);
						double volum = StringToDouble(strings[5]);
						
						Shares  mshare = new Shares();
						mshare.setData(data);
						mshare.setOpen(openPrice);
						mshare.setHigh(highPrice);
						mshare.setLow(lowPrice);
						mshare.setClose(closePrice);
						mshare.setVolume(volum);
						
						sharelist.add(mshare);
    				
    				}
            	}
            }
        }catch (Exception e) {
        	e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return sharelist;
	}
	
	
	
	
	public static double StringToDouble(String str) {
		return Double.parseDouble(str);
	}
	
	public static void SharePrint(ArrayList<Shares> shareLlist) {
		for(int i=0;i<shareLlist.size();i++) {
			System.out.println(shareLlist.get(i).toString());
		}
		System.out.println("*********************");
	}
	
}
