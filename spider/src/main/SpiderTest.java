package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderTest {
	public static void main(String[] args) {
        SpiderTest t = new SpiderTest();
        List<Zhihu> questionList = new ArrayList<Zhihu>();
        String url = "http://www.zhihu.com/explore/recommendations";
        questionList = t.getRecommendations(url);
        for(Zhihu question : questionList ){
        	System.out.println(question.toString());
        }
//        String result = t.sendGet("http://www.zhihu.com/explore/recommendations");
//        String reg = "question_link.+?>(.+?)<";
//        List<Zhihu> questionList = new ArrayList<Zhihu>();
//        questionList = t.regexString(result, reg);
//        for(Zhihu question: questionList){
//        	System.out.println(question.toString());
//        }
	}
	public List<Zhihu> getRecommendations(String url){
		List<Zhihu> list = new ArrayList<Zhihu>();
		Spider spider = new Spider();
		String result = spider.sendGet(url);
		String reg = "question_link.+?href=\"(/question/.+?)\".+?>";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(result);
		while(m.find()){
			String questionUrl = m.group(1);
			if(questionUrl.contains("/answer/")){
				int i = questionUrl.indexOf("answer");
				questionUrl = questionUrl.substring(0,i);
			}
			questionUrl = "http://www.zhihu.com" + questionUrl;
			Zhihu zhihu = new Zhihu(questionUrl);
			list.add(zhihu);
		}
		return list;
	}
    
//    public List<Zhihu> getRecommendations(String targetStr, String patternStr) {  
//    	List<Zhihu> questionList = new ArrayList<Zhihu>();
//    	  Pattern p1 = Pattern.compile(patternStr);
//    	  // 定义一个matcher用来做匹配
//    	  Matcher m1 = p1.matcher(targetStr);
//    	  
////    	  Pattern p2 = Pattern.compile("question_link.+?>(.+?)<");
//    	  Pattern p3 = Pattern.compile("question_link.+?href=\"(.+?)\".+?>(.+?)<");
//    	  Matcher m3 = p3.matcher(targetStr);
//    	  while (m3.find()) {
//    		  Zhihu z = new Zhihu();
//    		  z.question = m3.group(2);
//    		  z.zhihuUrl = m3.group(1);
//    		  questionList.add(z);
//    	  }
//    	  return questionList;
//    }
    
}
