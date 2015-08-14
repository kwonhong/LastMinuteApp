package trooperdesigns.lastminuteapp.DrawerPackage;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import trooperdesigns.lastminuteapp.EventListPackage.EventsFragment;
import trooperdesigns.lastminuteapp.GoogleCardsActivity;
import trooperdesigns.lastminuteapp.NewEventPackage.NewEventActivity;
import trooperdesigns.lastminuteapp.NewEventPackage.ViewContactsActivity;
import trooperdesigns.lastminuteapp.R;
import trooperdesigns.lastminuteapp.UtilPackage.ImageUtil;
import trooperdesigns.lastminuteapp.UtilPackage.ParseHandler;

public class DrawerActivity extends AppCompatActivity {

    private static int EVENT_LIST_FRAGMENT_INDEX = 0;

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private FragmentManager fragmentManager;
    private ParseHandler parseHandler;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initializeImageLoader();
        initializeVariables();
        setUpToolBar();
        setUpParseHandler();
        setupFloatingButton();
        setUpNavigationDrawerListView();

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

        EventsFragment eventsFragment = new EventsFragment();

        // Setup current Fragment as EventListFragment
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, eventsFragment)
                .commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.list_view);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        View headerView = getLayoutInflater().inflate(
                R.layout.header_navigation_drawer_social, mDrawerList, false);

        ImageView iv = (ImageView) headerView.findViewById(R.id.image);
        ImageUtil.displayRoundImage(iv,
                "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg", null);

        mDrawerList.addHeaderView(headerView);// Add header before adapter (for pre-KitKat)
        mDrawerList.setAdapter(new DrawerSocialAdapter(this));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        int color = getResources().getColor(R.color.material_grey_100);
        color = Color.argb(0xCD, Color.red(color), Color.green(color),
                Color.blue(color));
        mDrawerList.setBackgroundColor(color);
        mDrawerList.getLayoutParams().width = (int) getResources()
                .getDimension(R.dimen.drawer_width_social);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Toast.makeText(DrawerActivity.this,
                    "You selected position: " + position, Toast.LENGTH_SHORT)
                    .show();
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void initializeVariables() {
        fragmentManager = getSupportFragmentManager();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingBtn);
    }

    private void setUpToolBar() {
        // Setting title.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    private void initializeImageLoader() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
