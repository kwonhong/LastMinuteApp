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

public class DrawerActivity extends AppCompatActivity {

    private static int EVENT_LIST_FRAGMENT_INDEX = 0;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private RelativeLayout mNavigationDrawerRelativeLayout;
    private FloatingActionButton floatingActionButton;
    private ListView mDrawerListview;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initializeVariables();
        setUpToolBar();
        setupFloatingButton();
        setUpNavigationDrawerListView();
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
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, navigationDrawerItems[EVENT_LIST_FRAGMENT_INDEX].getFragment())
                .commit();

        // Setup Drawer Fragment list click listener
        DrawerAdapter adapter = new DrawerAdapter(getApplicationContext(), navigationDrawerItems);
        mDrawerListview.setAdapter(adapter);
        mDrawerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = navigationDrawerItems[position].getFragment();
                mFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                mDrawerLayout.closeDrawer(mNavigationDrawerRelativeLayout);
            }
        });
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
        mFragmentManager = getSupportFragmentManager();
        mDrawerListview = (ListView) findViewById(R.id.drawerListview);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_dashboard);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawerRelativeLayout = (RelativeLayout) findViewById(R.id.navigation_drawer_container);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingBtn);

    }
}
