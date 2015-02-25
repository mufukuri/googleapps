package zw.co.douglas.revevol.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import zw.co.douglas.revevol.Constants;
import zw.co.douglas.revevol.domain.SimulationResult;
import zw.co.douglas.revevol.simulation.CoreResult;
import zw.co.douglas.revevol.simulation.Result;

public class RunSimulation {
	
	
	

	private long startTime;
	private long endTime;
	private Map<Integer,Integer> results = new TreeMap<Integer,Integer>();
	private List<NumberGenerator> actors = new ArrayList<NumberGenerator>(Constants.NO_OF_BATCHES);
	public RunSimulation(){
		initialize();
	}
	private void initialize() {
		for(int i =0; i<=50 ; i++ ){
			results.put(i, 0);
		}
		
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
	public Map<Integer, Integer> getResults() {
		return results;
	}
	public void setResults(Map<Integer, Integer> results) {
		this.results = results;
	}
	
	private Result updateMap(Result result) {
		//Integer value =resultMap.get(result.getNumber());
		//value += 1;
		//resultMap.put(result.getNumber(),value );
		CoreResult.updateMap(result);
		return result;
	}

	public SimulationResult runSimulation(){
		startTime = System.currentTimeMillis();
		
		
		for(int i = 1; i <= Constants.NO_OF_BATCHES; i++){
			NumberGenerator actor = new NumberGenerator(i);
			for(int k =1; k<= Constants.NO_OF_RANDOM_NUMBERS; k++){
			Result result =actor.generateRandomNumber();
			updateMap(result);
			}
		}
		
	
	//System.out.println("\nFinished all threads");
	//retrive results from database;
	//Record Simulation Run in database;
	long totalTime = endTime - startTime;
	endTime = System.currentTimeMillis();

	System.out.println("Total running time "+totalTime);
	SimulationResult result = new SimulationResult(CoreResult.getResultMap(),startTime,endTime);
	CoreResult.setResultMap(null);
	return result;
	
	
	}
	
	
	public static void main(String [] args){
		RunSimulation coordinator = new RunSimulation();
		System.out.println("\nStart Simulation");
		SimulationResult simulation = coordinator.runSimulation();
		//Map<Integer,Integer> results =CoreResult.getResultMap();
		Map<Integer,Integer> results =simulation.getSummary();
		int counter =0;
		for(Integer index : results.keySet()){
			//if(results.get(index)>0){
				counter = counter +results.get(index);
			System.out.println("Key "+index +" Value "+results.get(index));
			//}
		}
		System.out.println("Total numbers generated "+counter);
		System.out.println(" Unique numbers "+results.size());
		System.out.println("Most frequesntly Generated "+simulation.getMostCommonNumber());
		System.out.println("Least frequesntly Generated "+results.size());
		System.out.println("Duration running sim "+simulation.getDuration());
	}
	
	

}
