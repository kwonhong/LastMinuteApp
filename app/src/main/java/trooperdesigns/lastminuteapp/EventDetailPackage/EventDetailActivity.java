package trooperdesigns.lastminuteapp.EventDetailPackage;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import trooperdesigns.lastminuteapp.EventListPackage.Invitation;
import trooperdesigns.lastminuteapp.R;

public class EventDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        initializeVariables();
        setUpToolBar();
        setUpInviteeListView();
    }

    private void setUpInviteeListView() {
        List<Invitee> invitees = getInvitees();
        InviteeListAdapter adapter = new InviteeListAdapter(this, invitees);

        listView.setClipToPadding(false);
        listView.setDivider(null);

        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());

        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);

        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setOnItemClickListener(null);

        listView.setAdapter(adapter);
    }

    private List<Invitee> getInvitees() {
        return Arrays.asList(new Invitee(Invitation.Status.ACCEPT, "name1"), new Invitee(Invitation.Status.ACCEPT, "name2"));
    }

    private void setUpToolBar() {

    }

    private void initializeVariables() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
