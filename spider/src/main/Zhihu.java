package main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Zhihu {
	 public String question;// 问题
	 public String questionDescription;// 问题描述
	 public String zhihuUrl;// 网页链接
	 public ArrayList<String> answers;// 存储所有回答的数组
	 // 构造方法初始化数据
	 public Zhihu(String zhihuUrl) {
		 this.zhihuUrl = zhihuUrl;
		 Spider s = new Spider();
		 String result = s.sendGet(zhihuUrl);
//		 System.out.println(result);
		 question = getQuestion(result);
		 questionDescription = getQuestionDescription(result);
		 answers = getAnswers(result);
	 }
	 
	 static String getQuestion(String result){
		 String question = "";
		 String regex = "zh-question-title.+?zm-editable-content\">(.+?)</";
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(result);
		 if(m.find()){
			 question = m.group(1);
		 }
		 return question;
	 }
	 static String getQuestionDescription(String result){
		 String description = "";
		 String regex = "zh-question-detail.+?zm-editable-content\">(.+?)</";
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(result);
		 if(m.find()){
			 description = m.group(1);
		 }
		 return description;
	 }
	 static ArrayList<String> getAnswers(String result){
		 ArrayList<String> answers = new ArrayList<String>();
		 String regex = "data-entry-url.*?zm-editable-content.*?>(.*?</div>)";
		 Pattern p = Pattern.compile(regex);
		 Matcher m = p.matcher(result);
		 while(m.find()){
			 answers.add(m.group(1));
		 }
		 return answers;
	 }
	 @Override
	 public String toString() {
		 String result = "";
		 result += "问题：" + question + "\n" + "描述：" + questionDescription + "\n"
	    + "链接：" + zhihuUrl + "\n回答：";
		 for (int i = 0; i < answers.size(); i++) {  
		        result += "回答" + i + "：" + answers.get(i) + "\r\n\r\n";  
		 }
		 return result;
	 }
	 public String writeString() {  
	        String result = "";  
	        result += "问题：" + question + "\r\n";  
	        result += "描述：" + questionDescription + "\r\n";  
	        result += "链接：" + zhihuUrl + "\r\n";  
	        for (int i = 0; i < answers.size(); i++) {  
	            result += "回答" + i + "：" + answers.get(i) + "\r\n";  
	        }  
	        result += "\r\n\r\n";
	        result = result.replace("<br>", "\r\n");
	        result = result.replaceAll("<.*?>", "");
	        return result;  
	}  
}