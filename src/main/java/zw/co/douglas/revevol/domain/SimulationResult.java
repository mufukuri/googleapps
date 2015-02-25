package zw.co.douglas.revevol.domain;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SimulationResult {
	
	private Map<Integer, Integer> summary;
	private Date date;
	private long startTime;
	private long endTime;
	
	private long duration;
	private int commonNumber;
	public SimulationResult(Map<Integer, Integer> resultMap, long startTime,
			long endTime) {
		
		this.summary = formatResults(resultMap);
		
		this.startTime = startTime;
		this.endTime =endTime;
	
	}
	private Map<Integer, Integer> formatResults(Map<Integer, Integer> resultMap) {
		Map<Integer,Integer> newMap = new TreeMap<Integer,Integer>();
		for(Integer key : resultMap.keySet()){
			if(resultMap.get(key)>0){
			newMap.put(key, resultMap.get(key));
			}
		}
		return newMap;
	}
	public Map<Integer, Integer> getSummary() {
		return summary;
	}
	public void setSummary(Map<Integer, Integer> summary) {
		this.summary = summary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getDuration() {
		return getRunningTime();
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public int getCommonNumber() {
		this.commonNumber = getMostCommonNumber();
		return commonNumber;
	}
	public void setCommonNumber(int commonNumber) {
		this.commonNumber = commonNumber;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long getRunningTime(){
		
		return (endTime -startTime);
	}
	

	public Integer getMostCommonNumber(){
		Map.Entry<Integer, Integer> maxEntry = null;
		for(Map.Entry<Integer, Integer> entry :getSummary().entrySet()){
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
		    {
		        maxEntry = entry;
		    }
			
		}
		
		int maxValueInMap=(Collections.max(getSummary().values()));  // This will return max value in the Hashmap
        for (Entry<Integer, Integer> entry : getSummary().entrySet()) {  // Itrate through hashmap
            if (entry.getValue()==maxValueInMap) {
               // System.out.println(" The key is >>>>>>"+entry.getKey());     // Print the key with max value
            }
        }
		
		return maxEntry.getKey();
	}
	
}
