package zw.co.douglas.revevol.api;

import zw.co.douglas.revevol.Constants;
import zw.co.douglas.revevol.domain.SimulationResult;
import zw.co.douglas.revevol.domain.Student;
import zw.co.douglas.revevol.domain.UserProfile;
import zw.co.douglas.revevol.form.ProfileForm;
import zw.co.douglas.revevol.form.ProfileForm.TeeShirtSize;
import zw.co.douglas.revevol.form.SimulatorFrom;
import zw.co.douglas.revevol.form.StudentForm;
import zw.co.douglas.revevol.random.NumberGenerator;
import zw.co.douglas.revevol.random.RunSimulation;
import zw.co.douglas.revevol.service.OfyService;
import zw.co.douglas.revevol.simulation.Coordinator;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;


@Api(name = "simulation", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = {
        Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Simulation Backend application.")
public class SimulatorApi {
	/*
     * Get the display name from the user's email. For example, if the email is
     * dutch@example.com, then the display name becomes "dutch."
     */
    private static String extractDefaultDisplayNameFromEmail(String email) {
        return email == null ? null : email.substring(0, email.indexOf("@"));
    }

    /**
     * Creates or updates a Profile object associated with the given user
     * object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @param profileForm
     *            A ProfileForm object sent from the client form.
     * @return Profile object just created.
     * @throws UnauthorizedException
     *             when the User object is null.
     */

    // Declare this method as a method available externally through Endpoints
    @ApiMethod(name = "saveProfile", path = "profile", httpMethod = HttpMethod.POST)
    // The request that invokes this method should provide data that
    // conforms to the fields defined in ProfileForm

    // TODO 1 Pass the ProfileForm parameter
    // TODO 2 Pass the User parameter
    public UserProfile saveProfile(final User user,ProfileForm profileFrom) throws UnauthorizedException {

    	 // If the user is not logged in, throw an UnauthorizedException
        if(user == null){
        	throw new UnauthorizedException("User not authenticated");
        }
        
        String userId = user.getUserId();
        String mainEmail = user.getEmail();
        
        // Set the displayName to the value sent by the ProfileForm, if sent
        // otherwise set it to null
        String displayName = profileFrom.getDisplayName();
        String firstName = profileFrom.getFirstName();
        String lastName = profileFrom.getLastName();
        TeeShirtSize teeShirtSize = profileFrom.getTeeShirtSize();
        Key key = Key.create(UserProfile.class, userId);
        UserProfile profile = (UserProfile) OfyService.ofy().load().key(key).now();
        
       
        if(profile == null){
        	if(firstName == null){
        		firstName = extractDefaultDisplayNameFromEmail(mainEmail);
        	}
        	
        	if(lastName == null){
        		lastName = extractDefaultDisplayNameFromEmail(mainEmail);
        	}
        	if(displayName == null){
            	displayName = extractDefaultDisplayNameFromEmail(mainEmail);
            }
        	
        	if(teeShirtSize == null){
        		teeShirtSize =TeeShirtSize.NOT_SPECIFIED;
        	}
        	
        	profile = new UserProfile(firstName, lastName,userId, displayName, mainEmail, teeShirtSize);
        }else{
        	
        	profile.update(firstName,lastName,displayName,teeShirtSize);
        }
        
 
        // TODO 2
        // If the displayName is null, set it to default value based on the user's email
        // by calling extractDefaultDisplayNameFromEmail(...)
        
        // Create a new Profile entity from the
        // userId, displayName, mainEmail and teeShirtSize
         

        // TODO 3 (In Lesson 3)
        // Save the Profile entity in the datastore
        OfyService.ofy().save().entity(profile).now();
        // Return the profile
        return profile;
    }

    /**
     * Returns a Profile object associated with the given user object. The cloud
     * endpoints system automatically inject the User object.
     *
     * @param user
     *            A User object injected by the cloud endpoints.
     * @return Profile object.
     * @throws UnauthorizedException
     *             when the User object is null.
     */
    @ApiMethod(name = "getProfile", path = "profile", httpMethod = HttpMethod.GET)
    public UserProfile getProfile(final User user) throws UnauthorizedException {
    	if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        // TODO
        // load the Profile Entity
        String userId = user.getUserId(); // TODO
        Key key = Key.create(UserProfile.class, userId); // TODO
        UserProfile profile = (UserProfile) OfyService.ofy().load().key(key).now(); // TODO load the Profile entiy
        return profile;
    }
    
    // Declare this method as a method available externally through Endpoints
    @ApiMethod(name = "runSimulation", path = "simulation", httpMethod = HttpMethod.POST)
    // The request that invokes this method should provide data that
    // conforms to the fields defined in ProfileForm

    // TODO 1 Pass the ProfileForm parameter
    // TODO 2 Pass the User parameter
    public SimulationResult runSimulation(final User user,SimulatorFrom form){
    	
    	//Coordinator supervisor = new Coordinator();
    	//SimResult result = new SimResult();
    	//SimulationResult result = supervisor.runSimulation();
    	//SimulationResult result = new SimulationResult(new TreeMap<Integer,Integer>(), 1819292l, 36373773l);
    	RunSimulation coordinator = new RunSimulation();
    	SimulationResult result = coordinator.runSimulation();
    	return result;
    }
    
    // Declare this method as a method available externally through Endpoints
    @ApiMethod(name = "getSimulation", path = "simulation", httpMethod = HttpMethod.GET)
    // The request that invokes this method should provide data that
    // conforms to the fields defined in ProfileForm

    // TODO 1 Pass the ProfileForm parameter
    // TODO 2 Pass the User parameter
    public SimulationResult getSimulation(final User user,SimulatorFrom form){
    	
    	Coordinator supervisor = new Coordinator();
    	
    	SimulationResult result = supervisor.runSimulation();
    	//SimulationResult result = new SimulationResult(new TreeMap<Integer,Integer>(), 1819292l, 36373773l);
    	
    	return result;
    }
    
    
    
    
    
    
    
    
    
 // Declare this method as a method available externally through Endpoints
    @ApiMethod(name = "saveStudent", path = "student", httpMethod = HttpMethod.POST)
    // The request that invokes this method should provide data that
    // conforms to the fields defined in ProfileForm

    // TODO 1 Pass the ProfileForm parameter
    // TODO 2 Pass the User parameter
    public Student saveStudent(final User user,StudentForm studentFrom) throws UnauthorizedException {
    	 if(user == null){
         	throw new UnauthorizedException("User not authenticated");
         }
        String id = user.getUserId();
        String email = user.getEmail();
        String firstName = studentFrom.getFirstName();
        String lastName = studentFrom.getLastName();

        
       
        
        
        // TODO 3 (In Lesson 3)
        // Save the Profile entity in the datastore
        Student student = new Student(id, firstName, lastName, email);
        OfyService.ofy().save().entity(student).now();
        // Return the profile
        return student;
    }
    
    
    
    
    
    
}
