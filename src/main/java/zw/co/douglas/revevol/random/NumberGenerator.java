package zw.co.douglas.revevol.random;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import zw.co.douglas.revevol.Constants;
import zw.co.douglas.revevol.simulation.CoreResult;
import zw.co.douglas.revevol.simulation.Result;

public class NumberGenerator {
	
	private long actorNumber;
	public static final int no_of_results = 1000;
	private int counter;
	private Result result;
	private  Map<Integer,Integer> resultMap = new TreeMap<Integer,Integer>();
	public NumberGenerator(){
		//System.out.println("\nInitialized Actor");
		
	}
	
	public NumberGenerator(long number){
		this();
		this.actorNumber =number;
		
	}
	
	private void initialize() {
		for(int i =0; i<=50 ; i++ ){
			resultMap.put(i, 0);
		}
		
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	public Result generateRandomNumber() {
		Random r = new Random();
		//random.nextInt(max - min + 1) + min
		int answer = r.nextInt(51) + 0;
		Result result = new Result(answer);
		
		return result;
		
		
	
		
	}
	

	public void run() {
		System.out.println("Running thread");
		for(int i=1; i<=Constants.NO_OF_RANDOM_NUMBERS; i++){
		Result result =generateRandomNumber();
		updateMap(result);
		//System.out.println("Actor Number " +actorNumber+"      result "+result.getNumber());
		}
		
		//System.out.println("final Map entry for Actor "+actorNumber);
		//for(Integer value : resultMap.keySet()){
		//	System.out.println("Value "+value  +"Count "+resultMap.get(value));
		//}
	}

	private Result updateMap(Result result) {
		//Integer value =resultMap.get(result.getNumber());
		//value += 1;
		//resultMap.put(result.getNumber(),value );
		CoreResult.updateMap(result);
		return result;
	}

	public Map<Integer, Integer> getResultMap() {
		
		if(resultMap== null){
			initialize();
		}
		return resultMap;
	}

	public void setResultMap(Map<Integer, Integer> resultMap) {
		this.resultMap = resultMap;
	}

	public long getActorNumber() {
		return actorNumber;
	}

	public void setActorNumber(long actorNumber) {
		this.actorNumber = actorNumber;
	}
	
	

}
