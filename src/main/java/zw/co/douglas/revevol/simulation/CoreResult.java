package zw.co.douglas.revevol.simulation;

import java.util.Map;
import java.util.TreeMap;

public class CoreResult {
	
	private static Integer myValue = 5; 
	private static Map<Integer,Integer> resultMap;
	
	private CoreResult(){
		
	}
	private static void initialize() {
		resultMap = new TreeMap<Integer,Integer>();
		for(int i =0; i<=50 ; i++ ){
			resultMap.put(i, 0);
		}
		
	}
	public static Map<Integer, Integer> getResultMap() {
		if(CoreResult.resultMap ==null){
			initialize();
		}
		return resultMap;
	}
	public static void setResultMap(Map<Integer, Integer> resultMap) {
		
		CoreResult.resultMap = resultMap;
	}
	
	
	public static void updateMap(Result result){
		synchronized(myValue){
		Integer value =getResultMap().get(result.getNumber());
		value += 1;
		resultMap.put(result.getNumber(),value );
		}
		
	}
	
	
	
	
}
