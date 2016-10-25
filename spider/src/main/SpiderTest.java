package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
//        for(Zhihu question : questionList ){
//        	System.out.println(question.writeString());
//        }
        String filePath = "E:/java/spider.txt";
        t.printTxt(filePath, questionList);
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
	public void printTxt(String filePath,List<Zhihu> questionList){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath))));
			for(Zhihu zhihu: questionList){
				bw.write(zhihu.writeString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
