package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Spider {
	public String sendGet(String url){
		String host = "10.123.74.139";
        String port = "808"; 
        setProxy(host, port);
		String result = "";
		BufferedReader in = null;
		try{
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line = in.readLine())!=null){
				result += line ;
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
    public static void setProxy(String host, String port) {
    	System.setProperty("http.nonProxyHosts", "172.*.*.*|127.*.*.*|localhost.*");
        System.setProperty("proxyHost", host);  
        System.setProperty("proxyPort", port);  
    } 
}
