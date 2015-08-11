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

    private CardView cardView1, cardView2, cardView3;
    private ObjectAnimator cardViewMover1, cardViewMover2, cardViewMover3;
    private int currentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        cardView1 = (CardView) findViewById(R.id.cardview1);
        cardView2 = (CardView) findViewById(R.id.cardview2);
        cardView3 = (CardView) findViewById(R.id.cardview3);

        currentCard = 1;

        TextView nextButton = (TextView) findViewById(R.id.next_button);
        TextView nextButton2 = (TextView) findViewById(R.id.next_button_2);
        TextView nextButton3 = (TextView) findViewById(R.id.next_button_3);
        TextView backButton = (TextView) findViewById(R.id.back_button);
        TextView backButton2 = (TextView) findViewById(R.id.back_button_2);
        TextView backButton3 = (TextView) findViewById(R.id.back_button_3);

        View.OnClickListener nextClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextPressed();
            }
        };
        View.OnClickListener backClickHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };

        nextButton.setOnClickListener(nextClickHandler);
        backButton.setOnClickListener(backClickHandler);

        nextButton2.setOnClickListener(nextClickHandler);
        backButton2.setOnClickListener(backClickHandler);

        nextButton3.setOnClickListener(nextClickHandler);
        backButton3.setOnClickListener(backClickHandler);

    }

    @Override
    public void onBackPressed() {
        switch(currentCard){
            case(1):
                super.onBackPressed();
                break;
            case(2):
                cardViewMover1 = ObjectAnimator.ofFloat(cardView1, "translationX", -cardView1.getWidth(), 0);
                cardViewMover1.setDuration(150);
                cardViewMover1.start();
                cardView1.setVisibility(View.VISIBLE);

                cardViewMover2 = ObjectAnimator.ofFloat(cardView2, "translationX", 0, cardView1.getWidth());
                cardViewMover2.setDuration(150);
                cardViewMover2.start();

                currentCard--;
                break;
            case(3):
                cardViewMover2 = ObjectAnimator.ofFloat(cardView2, "translationX", -cardView1.getWidth(), 0);
                cardViewMover2.setDuration(150);
                cardViewMover2.start();
                cardView2.setVisibility(View.VISIBLE);

                cardViewMover3 = ObjectAnimator.ofFloat(cardView3, "translationX", 0, cardView1.getWidth());
                cardViewMover3.setDuration(150);
                cardViewMover3.start();

                currentCard--;
                break;
            default:
                break;
        }
    }

    public void onNextPressed() {
        switch(currentCard) {
            case(1):
                cardViewMover1 = ObjectAnimator.ofFloat(cardView1, "translationX", 0, -cardView1.getWidth());
                cardViewMover1.setDuration(150);
                cardViewMover1.start();

                ObjectAnimator cardViewMover2 = ObjectAnimator.ofFloat(cardView2, "translationX", cardView1.getWidth(), 0);
                cardViewMover2.setDuration(150);
                cardViewMover2.start();
                cardView2.setVisibility(View.VISIBLE);
                currentCard++;
                break;
            case(2):
                cardViewMover2 = ObjectAnimator.ofFloat(cardView2, "translationX", 0, -cardView1.getWidth());
                cardViewMover2.setDuration(150);
                cardViewMover2.start();

                cardViewMover3 = ObjectAnimator.ofFloat(cardView3, "translationX", cardView1.getWidth(), 0);
                cardViewMover3.setDuration(150);
                cardViewMover3.start();
                cardView3.setVisibility(View.VISIBLE);
                currentCard++;
                break;
            case(3):
                break;
            default:
                break;
        }
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
