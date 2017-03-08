package com.greco.caller.plugin.html;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class WebViewHtmlCode {
	
	public void viewHtml(String html){
		
		File file = new File("response_jenkins.html");
		open(html, file);
	 
	}

	
public void open(String text, File fileHtml){
		
		File newHtmlFile = fileHtml;
		try {
			FileUtils.writeStringToFile(newHtmlFile, text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			Desktop.getDesktop().open(fileHtml);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	

}
