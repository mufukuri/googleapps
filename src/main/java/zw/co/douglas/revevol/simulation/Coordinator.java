package zw.co.douglas.revevol.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zw.co.douglas.revevol.Constants;
import zw.co.douglas.revevol.domain.SimulationResult;
import zw.co.douglas.revevol.simulation.Crunchify.MyRunnable;

public class Coordinator {
	
	private  int no_of_actors = Constants.NO_OF_BATCHES;
	private long startTime;
	private long endTime;
	private Map<Integer,Integer> results = new TreeMap<Integer,Integer>();
	private List<Actor> actors = new ArrayList<Actor>(no_of_actors);
	public Coordinator(){
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
	
	
	public SimulationResult runSimulation(){
		startTime = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(no_of_actors);
		
		for(int i = 1; i <= no_of_actors; i++){
			Actor actor = new Actor(i);
			executor.execute(actor);
		}
		executor.shutdown();
	// Wait until all threads are finish
		while (!executor.isTerminated()) {
			}
	System.out.println("\nFinished all threads");
	//retrive results from database;
	//Record Simulation Run in database;
	long totalTime = endTime - startTime;
	endTime = System.currentTimeMillis();

	System.out.println("Total running time "+totalTime);
	SimulationResult result = new SimulationResult(CoreResult.getResultMap(),startTime,endTime);
	return result;
	
	
	}
	private Result updateMap(Result result) {
		//Integer value =resultMap.get(result.getNumber());
		//value += 1;
		//resultMap.put(result.getNumber(),value );
		CoreResult.updateMap(result);
		return result;
	}

	
	public static void main(String [] args){
		Coordinator coordinator = new Coordinator();
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
	
	// http://crunchify.com/how-to-run-multiple-threads-concurrently-in-java-executorservice-approach/
}
