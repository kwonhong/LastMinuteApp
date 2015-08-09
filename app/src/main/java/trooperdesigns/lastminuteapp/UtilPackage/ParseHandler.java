package trooperdesigns.lastminuteapp.UtilPackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import trooperdesigns.lastminuteapp.R;

import static trooperdesigns.lastminuteapp.UtilPackage.Utils.getString;

/**
 * Created by james on 15-08-09.
 */
public class ParseHandler {

    private static final int PARSE_APPLICATION_ID = R.string.parse_application_id;
    private static final int PARSE_CLIENT_KEY = R.string.parse_client_key;
    private static final int PARSE_USERNAME_KEY = R.string.parse_username;
    private static final int PARSE_PASSWORD_KEY = R.string.parse_password;
    private Context context;

    public ParseHandler(Context context) {
        this.context = context;
        parseInitialize();
    }

    // Parse Initialization
    private void parseInitialize() {

        Parse.initialize(context, getString(context, PARSE_APPLICATION_ID),
                getString(context, PARSE_CLIENT_KEY));
        ParseInstallation.getCurrentInstallation().saveEventually();
        ParseUser.enableAutomaticUser();

    }

    public void parseLogin() {

//        ParseACL defaultACL = new ParseACL();
        ParseUser.logInInBackground(getString(context, PARSE_USERNAME_KEY),
                getString(context, PARSE_PASSWORD_KEY), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        // TODO Auto-generated method stub
                        if (user != null) {
                            // if user exists and is authenticated
                            // send them to main menu gui

                            // show login successful toast
                            Toast.makeText(context, "Login successful!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context,
                                    e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("login", e.getLocalizedMessage());
                        }
                    }
                });

    }
}
