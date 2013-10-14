package edu.southampton.wais.utility;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Observable;
import java.util.Observer;


public class TimeMeasureUtility  {

	
 private String name;
 private long timeStart;
 
 private boolean start;

 
 private long timeEnd;
 
 
 private ThreadMXBean bean;
 
 
 
public TimeMeasureUtility() {
    bean = ManagementFactory.getThreadMXBean( );	
	this.start=true;
	this.timeStart=0L;
	this.timeEnd=0L;
	
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getTimestart() {
	return timeStart;
}
private void setTimestart() {
	 if(bean.isCurrentThreadCpuTimeSupported( )) 
			 timeStart=bean.getCurrentThreadUserTime();
			 
	
	
}
public long getTimeend() {
	return timeEnd;
}
private void setTimeend() {
	if(bean.isCurrentThreadCpuTimeSupported( )) {
		timeEnd=bean.getCurrentThreadUserTime()-timeStart;
		
	}
	
	
}

public  void update( Object arg1) {
	
	

	
	if(start){
		
		this.setTimestart();
	    start=false;
	    
		if(arg1 instanceof String){
		   String n=(String)arg1;  
		
		System.out.println("Started... "+n);
		}
		
		
	}
	else{
		this.setTimeend();
	
		if(arg1 instanceof String){
			   String n=(String)arg1;  
			
			System.out.println("Finish... "+n+ " with time  "+ ((double)getTimeend()/(double)Math.pow(10, 9))+" s");
			}
	    
		//this.cleanTime();
		
	}	
		
	}
	


public void cleanTime(){
	
	this.timeEnd=0L;
	this.timeStart=0L;
	this.start=true;
	
	
	
} 
 
	
	
	
}
