package trooperdesigns.lastminuteapp.EventDetailPackage;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import trooperdesigns.lastminuteapp.EventListPackage.Event;
import trooperdesigns.lastminuteapp.EventListPackage.Invitation;
import trooperdesigns.lastminuteapp.R;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    ListView listView;
    Event event;
    Invitation invitation;
    TextView descriptionTxtView;
    TextView timeRemainingTxtView;
    TextView acceptBtn, declineBtn, onMyWayBtn, moreInviteeBtn;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        initializeVariables();



        event = getSampleEvent();
        invitation = getSampleInvitation();
        setUpToolBarTitle(event.getTitle(), event.getStatus());
        setUpEventDescription(event.getEventDescription());
        setUpTimeRemaining(event.getEventDate());
        setUpInivitationStatusButtons(invitation.getStatus());
        setUpLocation();
        setUpInviteeListView(event.getInviteeList());
        setUpMoreInviteeButton(event.getInviteeList());
    }

    private Invitation getSampleInvitation() {
        return new Invitation(Invitation.Status.ACCEPT);
    }

    private Event getSampleEvent() {
        return new Event(
                "Joe Restaurant Dinner",
                "Guest it he tears aware as. Make my no cold of need. He been past in by my hard. Warmly thrown oh he common future. Otherwise concealed favourite frankness on be at dashwoods defective at. Sympathize interested simplicity at do projecting increasing terminated.",
                new Date(),
                Event.Status.HAPPENING,
                getInvitees());
    }

    private List<Invitee> getInvitees() {
        return Arrays.asList(new Invitee(Invitation.Status.ACCEPT, "name1"),
                new Invitee(Invitation.Status.ACCEPT, "name2"));
    }

    /**
     * Set up toolbar title as event title & Set up color of the toolbar corresponding to the status of the event.
     */
    private void setUpToolBarTitle(String title, Event.Status status) {
        // Setting up the toolbar as supportActionBar
        setSupportActionBar(toolbar);

        // Setting the title at the center.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

        // Setting the color of the toolbar corresponding to the event status
        actionBar.setBackgroundDrawable(new ColorDrawable(status.getColor()));
    }

    private void setUpEventDescription(String description) {
        descriptionTxtView.setText(description);
    }

    private void setUpTimeRemaining(Date eventDate) {

        DateTime dateTime = new DateTime();
        DateTime eventDateTime = new DateTime(eventDate);
        String remainingTimeTxt = String.format("Time Remaining: %d : %d",
                Hours.hoursBetween(eventDateTime, dateTime).getHours() % 24,
                Minutes.minutesBetween(eventDateTime, dateTime).getMinutes() % 60
        );
        timeRemainingTxtView.setText(remainingTimeTxt);
    }

    private void setUpInivitationStatusButtons(Invitation.Status status) {

        acceptBtn.setOnClickListener(this);
        declineBtn.setOnClickListener(this);
        onMyWayBtn.setOnClickListener(this);

        switch (status) {
            case PENDING:
                onMyWayBtn.setVisibility(View.GONE);
                break;
            case ACCEPT:
                acceptBtn.setVisibility(View.GONE);
                break;
            case DECLINE:
                declineBtn.setVisibility(View.GONE);
                onMyWayBtn.setVisibility(View.GONE);
                break;
            case ON_MY_WAY:
                declineBtn.setVisibility(View.GONE);
                break;
            default:
                //TODO REPORT ERROR
        }
    }

    private void setUpLocation() {
        LatLng DAVAO = new LatLng(7.0722, 125.6131);
        Marker davao = map.addMarker(new MarkerOptions().position(DAVAO).title("Davao City").snippet("Ateneo de Davao University"));

        // zoom in the camera to Davao city && animate the zoom process
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DAVAO, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        // Setting on Map Click Listener && Launch GoogleMap Application for more info.
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Uri gmmIntentUri = Uri.parse("geo:7.0722,125.6131(Google+Sydney)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });
    }

    private void setUpInviteeListView(List<Invitee> inviteeList) {
        // One Invitee

        // Two or more invitees
    }

    private void setUpMoreInviteeButton(List<Invitee> inviteeList) {

        final InviteeListDialog inviteeListDialog = new InviteeListDialog(this, inviteeList);
        moreInviteeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteeListDialog.show();
            }
        });
    }



    private void initializeVariables() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        descriptionTxtView = (TextView) findViewById(R.id.descriptionTxtView);
        timeRemainingTxtView = (TextView) findViewById(R.id.timeRemainingTxtView);

        acceptBtn = (TextView) findViewById(R.id.btnAccept);
        declineBtn = (TextView) findViewById(R.id.btnDecline);
        onMyWayBtn = (TextView) findViewById(R.id.btnOnMyWay);
        moreInviteeBtn = (TextView) findViewById(R.id.moreInviteeBtn);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnAccept:
//                handleStatusButton((String) v.getTag(), Invitation.Status.ACCEPT, "Accept Confirmation Message");
//                Toast.makeText(getContext(), (String) v.getTag(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnDecline:
//                handleStatusButton((String) v.getTag(), Invitation.Status.DECLINE, "Decline Confirmation Message");
//                Toast.makeText(getContext(), (String) v.getTag(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnOnMyWay:
//                handleStatusChangeAction((String) v.getTag(), Invitation.Status.ON_MY_WAY);
//                Toast.makeText(getContext(), (String) v.getTag(), Toast.LENGTH_SHORT).show();
                break;

            default:
                //TODO REPORT ERROR

        }
    }
}
