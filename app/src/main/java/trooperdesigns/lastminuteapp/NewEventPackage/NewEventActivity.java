package trooperdesigns.lastminuteapp.NewEventPackage;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import trooperdesigns.lastminuteapp.R;

public class NewEventActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    CardView cardView1, cardView2, cardView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        linearLayout = (LinearLayout) findViewById(R.id.layout);
        cardView1 = (CardView) findViewById(R.id.cardview1);
        cardView2 = (CardView) findViewById(R.id.cardview2);
        cardView3 = (CardView) findViewById(R.id.cardview3);

        TextView additionalButton = (TextView) findViewById(R.id.additionalButton);
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator cardViewMover1 = ObjectAnimator.ofFloat(cardView1, "translationX", 0, -cardView1.getWidth());
                cardViewMover1.setDuration(100);
                cardViewMover1.start();

                ObjectAnimator cardViewMover2 = ObjectAnimator.ofFloat(cardView2, "translationX", cardView1.getWidth(), 0);
                cardViewMover2.setDuration(100);
                cardViewMover2.start();
                cardView2.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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
