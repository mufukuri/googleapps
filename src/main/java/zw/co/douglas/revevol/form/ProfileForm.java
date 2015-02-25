package zw.co.douglas.revevol.form;



public class ProfileForm {
	/**
	 * Pojo representing a profile form on the client side.
	 */

	    /**
	     * Any string user wants us to display him/her on this system.
	     */
	    private String displayName;

	    /**
	     * T shirt size.
	     */
	    private TeeShirtSize teeShirtSize;
	    
	    private String firstName;
	    private String lastName;

	    private ProfileForm () {}

	    /**
	     * Constructor for ProfileForm, solely for unit test.
	     * @param displayName A String for displaying the user on this system.
	     * @param notificationEmail An e-mail address for getting notifications from this system.
	     */
	    public ProfileForm(String displayName, TeeShirtSize teeShirtSize) {
	        this.displayName = displayName;
	        this.teeShirtSize = teeShirtSize;
	    }

	    public String getDisplayName() {
	        return displayName;
	    }

	    public TeeShirtSize getTeeShirtSize() {
	        return teeShirtSize;
	    }
	    
	    public static enum TeeShirtSize {
	    	NOT_SPECIFIED,
	        XS,
	        S,
	        M,
	        L, 
	        XL, 
	        XXL,
	        XXXL
	    }

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public void setTeeShirtSize(TeeShirtSize teeShirtSize) {
			this.teeShirtSize = teeShirtSize;
		}
	    
	    
}
