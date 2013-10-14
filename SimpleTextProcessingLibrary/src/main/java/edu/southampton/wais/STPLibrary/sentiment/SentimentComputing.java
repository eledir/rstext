package edu.southampton.wais.STPLibrary.sentiment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import edu.southampton.wais.utility.Logger;




public class SentimentComputing {

	
	
	private  static HashMap<String, String> _dict;

	
	
	
	public static HashMap<String, String> get_dict() {
		return _dict;
	}



	private SentimentComputing(File fname){
		
		_dict = new HashMap<String, String>();
		HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
		try{
			BufferedReader csv =  new BufferedReader(new FileReader(fname));
			String line = "";			
			while((line = csv.readLine()) != null)
			{
				String[] data = line.split("\t");
				
				
				Double score = combineScore(Double.parseDouble(data[2]),Double.parseDouble(data[3]));
				
				String[] words = data[4].split(" ");
				for(String w:words)
				{
					String[] w_n = w.split("#");
					w_n[0] += "#"+data[0];
					int index = Integer.parseInt(w_n[1])-1;
					if(_temp.containsKey(w_n[0]))
					{
						Vector<Double> v = _temp.get(w_n[0]);
						if(index>v.size())
							for(int i = v.size();i<index; i++)
								v.add(0.0);
						v.add(index, score);
						_temp.put(w_n[0], v);
					}
					else
					{
						Vector<Double> v = new Vector<Double>();
						for(int i = 0;i<index; i++)
							v.add(0.0);
						v.add(index, score);
						_temp.put(w_n[0], v);
					}
				}
			}
			Set<String> temp = _temp.keySet();
			for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
				String word = (String) iterator.next();
				Vector<Double> v = _temp.get(word);
				
				
				double score=computeScoreMedian(v);
				
				
				String sent = "";				
				if(score>=0.6){
					sent = "5";}
				else if(score > 0.25 && score<0.6){
					sent = "3";}
				else if(score > 0 && score<=0.25){
					sent = "2";}
				else if(score < 0 && score>=-0.25){
					sent = "-2";}
				else if(score < -0.25 && score>-0.6){
					sent = "-3";}
				else if(score<=-0.6){
					sent = "-5";}
				else if(score==0){
					sent = "0";
					
				}
				_dict.put(word, sent);
			}
		}
		catch(Exception e){e.printStackTrace();}		
	
	}
	
	
	
private Double combineScore(double parseDouble, double parseDouble2) {
		// TODO Auto-generated method stub
	 if(parseDouble==parseDouble2){
		 return parseDouble;
	 }  
	 else{
	return parseDouble>parseDouble2 ? parseDouble :-parseDouble2;
	 }
		
	}



public static SentimentComputing loadSentiWordnet(File fdic){
		
	
			
return 		new SentimentComputing(fdic);
	
			
	
		
}

private static double computeScoreMIx(Vector<Double> v){
	
	
	DescriptiveStatistics staPositive= new DescriptiveStatistics();
	DescriptiveStatistics staNegative= new DescriptiveStatistics();
	List<Double> staZ= Lists.newArrayList();
	
	
	for(Double d: v){
		if(d>0){
		  staPositive.addValue(d);
			
		}
		else if (d<0){
		
		 staNegative.addValue(d);
		
		}
		else{
			
			staZ.add(d);
		}
	   
	}
	
	double score;
	if(staZ.size()>staNegative.getN()+staPositive.getN()){
		score=0;
	}
	else {
		
	if(Math.abs(staNegative.getMean())==staNegative.getMean()){
		
		score =0;
	}
		
	else if(Math.abs(staNegative.getMean())>staNegative.getMean()){
	
			score=staNegative.getMean();
			
			
		}
		else{
			
			score=staPositive.getMean();
			
		}
	}
	
	return score;
	
}



private static double computeScoreMean(Vector<Double> v){
	
	
	DescriptiveStatistics sta= new DescriptiveStatistics();
	
	
	for(Double d: v){
			
			sta.addValue(d);
	   
	}
	
		
	return sta.getMean();
	
}

private static double computeScoreMedian(Vector<Double> v){
	
	
	DescriptiveStatistics sta= new DescriptiveStatistics();
	
	
	for(Double d: v){
			
			sta.addValue(d);
	   
	}
	
		
	return sta.getPercentile(50);
	
}






	public String extract(String word, String posNormalized) {
		
		   if(_dict.containsKey(word+"#"+posNormalized))
		
		   return _dict.get(word+"#"+posNormalized);
		   else{
			   
			   return "0";
		   }
	}
	

public static void main(String args[]){
	
		File f = new File("/home/antoniodesktop/Documents/data/nlpdata/SentiWordNet_3.0.0_20130122.txt");

		String word = "leave";

		
		Stopwatch stopwatch = new Stopwatch().start();
		SentimentComputing scomputeirng = SentimentComputing
				.loadSentiWordnet(f);

		  stopwatch.stop(); // optional

		   long minute = stopwatch.elapsedTime(TimeUnit.MINUTES);
		   
		   Logger.logFine("that minute: " + minute);
		   
		   Logger.logFine("that took: " + stopwatch); // formatted string like "12.3 ms"
		
		
    String p = scomputeirng.extract(word, "v");

	System.out.println(p);


}



}
	
	
	
	
	
	

