package trooperdesigns.lastminuteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class DrawerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RelativeLayout mNavigationDrawerRelativeLayout;
    private FloatingActionButton floatingActionButton;
//    private ListView mDrawerListview;
//    private FragmentManager mFragmentManager;
//    private LinearLayout mUserLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Parse.initialize(this, "MPkD1KSw9UFLdb3KpG46SgGKnrvdjEeG6OvGX9xv",
                "TjirfnSYcOmD1kW90vvJtSyfqZuFlboXzWnRVSca");
        ParseInstallation.getCurrentInstallation().saveEventually();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // TODO Auto-generated method stub
        ParseUser.logInInBackground("justin", "password",  new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                // TODO Auto-generated method stub
                if (user != null) {
                    // if user exists and is authenticated
                    // send them to main menu gui

                    // show login successful toast
                    Toast.makeText(getApplicationContext(), "Login successful!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("login", e.getLocalizedMessage());
                }
            }
        });

        initializeVariables();
        setUpToolBar();

        setupFloatingButton();
    }

    private void setupFloatingButton() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewEventActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home]]=[/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpToolBar() {

        // Setting title.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        // Setting the Click Listener & Icon
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationDrawerRelativeLayout);
            }
        });
    }

    private void initializeVariables() {
//        mFragmentManager = getSupportFragmentManager();
//        mUserLayout = (LinearLayout) findViewById(R.id.userLayout);
//        mDrawerListview = (ListView) findViewById(R.id.drawerListview);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_dashboard);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.navigation_drawer_container);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingBtn);

    }
}
