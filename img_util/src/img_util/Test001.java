package img_util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test001 {
	private static final Logger logger = LoggerFactory.getLogger(Test001.class);
	
	public void method00(String directoryPath,String filePath) throws IOException {
		
		FileReader fr = new FileReader(filePath);
		BufferedReader br = new BufferedReader(fr);
		String line="";
		List<String> list=new ArrayList<String>();
		while ((line=br.readLine())!=null) {
			//logger.info("line == "+line);
			list.add(line);
		}
		br.close();
		fr.close();
		
		method01(list,directoryPath);
		
	}
	
	public void method01(List<String> list,String path){
		File directory=new File(path);
		File photo=null;
		if(directory.isDirectory()){
			String filePath="";
			for(String data:list){
				filePath=path+"/"+data;
				photo=new File(filePath);
				if(photo.isFile()){
					photo.delete();
				}
			}
		}
	}
}
