package trooperdesigns.lastminuteapp.DrawerPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.melnykov.fab.FloatingActionButton;

import trooperdesigns.lastminuteapp.EventListPackage.EventsFragment;
import trooperdesigns.lastminuteapp.NewEventActivity;
import trooperdesigns.lastminuteapp.R;
import trooperdesigns.lastminuteapp.UtilPackage.ParseHandler;

public class DrawerActivity extends AppCompatActivity {

    private static int EVENT_LIST_FRAGMENT_INDEX = 0;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RelativeLayout navigationDrawerRelativeLayout;
    private FloatingActionButton floatingActionButton;
    private ListView drawerListView;
    private FragmentManager fragmentManager;
    private ParseHandler parseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initializeVariables();
        setUpToolBar();
        setupFloatingButton();
        setUpNavigationDrawerListView();
        setUpParseHandler();
    }

    private void setUpParseHandler() {

        parseHandler = new ParseHandler(getApplicationContext());
        parseHandler.parseLogin();
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

    private void setUpNavigationDrawerListView() {

        // Initializing all the fragments appearing on drawer list view
        // Order matters!
        final NavigationDrawerItem[] navigationDrawerItems = {
                new NavigationDrawerItem(getResources().getDrawable(R.mipmap.ic_launcher),
                        "Events", new EventsFragment())
        };

        // Setup current Fragment as EventListFragment
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, navigationDrawerItems[EVENT_LIST_FRAGMENT_INDEX].getFragment())
                .commit();

        // Setup Drawer Fragment list click listener
        DrawerAdapter adapter = new DrawerAdapter(getApplicationContext(), navigationDrawerItems);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = navigationDrawerItems[position].getFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                drawerLayout.closeDrawer(navigationDrawerRelativeLayout);
            }
        });
    }

    private void setUpToolBar() {

        // Setting title.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        // Setting the Click Listener & Icon
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationDrawerRelativeLayout);
            }
        });
    }

    private void initializeVariables() {
        fragmentManager = getSupportFragmentManager();
        drawerListView = (ListView) findViewById(R.id.drawerListview);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.navigation_drawer_container);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingBtn);

    }
}
