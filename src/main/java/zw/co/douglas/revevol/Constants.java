package zw.co.douglas.revevol;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming the helloworld API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "451843649108-91gsefdbo9eqetk82j9anr5q0rasau87.apps.googleusercontent.com";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
  public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;
  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
  
  
  public static final int NO_OF_BATCHES = 100;
  
  public static final int NO_OF_RANDOM_NUMBERS = 1000;
  public static final int START_NUMBER = 0;
  public static final int LAST_NUMBER = 50;
  
}
