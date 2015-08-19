package trooperdesigns.lastminuteapp.NewEventPackage;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trooperdesigns.lastminuteapp.R;
import trooperdesigns.lastminuteapp.UtilPackage.StringMatcher;
import trooperdesigns.lastminuteapp.Views.FloatLabeledEditText;
import trooperdesigns.lastminuteapp.widget.IndexableListView;

public class NewEventActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private CardView cardView1, cardView2, cardView3, confirmCardView;
    private ObjectAnimator cardViewMover1, cardViewMover2, cardViewMover3, confirmCardViewMover;
    private TextView nextButton, nextButton2, backButton, backButton2, backButton3, timeDialogButton;
    private int currentCard;

    // TODO: empty allContacts list when event is created
    static List<Contact> allContacts = new ArrayList<>();
    List<Contact> selectedContacts = new ArrayList<>();

    ContactsAdapter contactsAdapter;

    private List<Contact> originalContacts = new ArrayList<>();
    private List<Contact> filteredContacts = new ArrayList<>();
    private ArrayList<Contact> contactsResult;
    // used to remove contacts who were unchecked
    private ArrayList<String> uncheckedContacts;
    private int numSelectedContacts;

    private TextView confirmButton;
    private EditText search;
    IndexableListView contactsListView;
    public static boolean isKeyboardOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        initializeVariables();
        setUpClickHandlers();
        getAllContacts(this.getContentResolver());
        setUpContactsList();
        getNumSelectedContacts();
    }

    private void initializeVariables() {

        // setup card views
        cardView1 = (CardView) findViewById(R.id.cardview1);
        cardView2 = (CardView) findViewById(R.id.cardview2);
        cardView3 = (CardView) findViewById(R.id.cardview3);
        confirmCardView = (CardView) findViewById(R.id.confirm_card);

        // button variables
        nextButton = (TextView) findViewById(R.id.next_button);
        nextButton2 = (TextView) findViewById(R.id.next_button_2);
        backButton = (TextView) findViewById(R.id.back_button);
        backButton2 = (TextView) findViewById(R.id.back_button_2);
        backButton3 = (TextView) findViewById(R.id.back_button_3);
        confirmButton = (TextView) findViewById(R.id.confirm_button);

        // dynamic search bar variable
        search = (EditText) findViewById(R.id.search);

        // time picker dialog
        timeDialogButton = (TextView) findViewById(R.id.time_dialog_button);

        // contact list variables
        contactsListView = (IndexableListView) findViewById(R.id.lv);
        contactsAdapter = new ContactsAdapter(getApplicationContext(), NewEventActivity.allContacts);

        // other variables
        originalContacts = NewEventActivity.allContacts;
        isKeyboardOpen = false;
        currentCard = 1;
    }

    private void setUpClickHandlers() {
        // generic next and back button click handlers
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

        // button handlers
        nextButton.setOnClickListener(nextClickHandler);
        backButton.setOnClickListener(backClickHandler);
        nextButton2.setOnClickListener(nextClickHandler);
        backButton2.setOnClickListener(backClickHandler);
        backButton3.setOnClickListener(backClickHandler);

        // time dialog button handler
        timeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
            }
        });

        // search bar handlers
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isKeyboardOpen = true;
                return false;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsAdapter.getFilter().filter(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // confirm new event
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean noneSelected = true;
                StringBuilder sb = new StringBuilder();
                // check all contacts if any are selected
                for (int i = 0; i < NewEventActivity.allContacts.size(); i++) {
                    if (NewEventActivity.allContacts.get(i).getIsChecked() == true) {
                        noneSelected = false;
                        sb.append(NewEventActivity.allContacts.get(i).getName() + ", ");
                    }
                }
                // if at least one selected, return
                if (!noneSelected) {
                    Toast.makeText(NewEventActivity.this,
                            sb.toString(), Toast.LENGTH_SHORT).show();
                    Intent data = new Intent();
                    if (getParent() == null) {
                        setResult(Activity.RESULT_OK, data);
                    } else {
                        getParent().setResult(Activity.RESULT_OK, data);
                    }
                    finish();
                } else {
                    // else notify user to select contacts
                    Toast.makeText(NewEventActivity.this, "Select at least one contact", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpContactsList() {
        contactsListView.setClickable(true);
        contactsListView.setAdapter(contactsAdapter);
        contactsListView.setOnItemClickListener(this);
        contactsListView.setItemsCanFocus(false);
        contactsListView.setTextFilterEnabled(true);
    }

    // for card animation logic
    @Override
    public void onBackPressed() {
        switch(currentCard){
            case(1):
                NewEventActivity.allContacts = originalContacts;
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
                hideConfirmCard();

                currentCard--;
                break;
            default:
                break;
        }
    }

    // for card animation logic
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

                showConfirmCard();

                currentCard++;
                break;
            case(3):
                break;
            default:
                break;
        }
    }

    // this displays the google card at the bottom of the new event page
    // and only displays when restrictions are met
    private void showConfirmCard() {
        if(numSelectedContacts > 0 && confirmCardView.getVisibility() == View.GONE) {
            confirmCardViewMover = ObjectAnimator.ofFloat(confirmCardView, View.ALPHA, 0,1);
            confirmCardViewMover.setDuration(200);
            confirmCardViewMover.start();
            confirmCardView.setVisibility(View.VISIBLE);
        }
    }

    // this function hides the google card at the bottom of the new event page
    private void hideConfirmCard() {
        if(numSelectedContacts <= 0 && confirmCardView.getVisibility() == View.VISIBLE) {
            confirmCardViewMover = ObjectAnimator.ofFloat(confirmCardView, View.ALPHA, 1, 0);
            confirmCardViewMover.setDuration(200);
            confirmCardViewMover.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    confirmCardView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            confirmCardViewMover.start();
        }
    }

    // count the total number of selected contacts
    private int getNumSelectedContacts() {
        int numSelectedContacts = 0;
        for(Contact contact : NewEventActivity.allContacts) {
            if(contact.getIsChecked()) {
                numSelectedContacts++;
            }
        }
        this.numSelectedContacts = numSelectedContacts;
        return numSelectedContacts;
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

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
        // toggle the actual contact isChecked (so it can be used later)
        Contact contact = filteredContacts.get(position);
        contact.toggle();

        // toggle the UI checkbox
        TextView checkbox = (TextView) view.findViewById(R.id.contact_is_checked);
        Log.d("checkbox", getChecked(checkbox) + ":" + numSelectedContacts);
        if(getChecked(checkbox)) {
            setChecked(checkbox, false);
            numSelectedContacts--;
            hideConfirmCard();
        } else {
            setChecked(checkbox, true);
            numSelectedContacts++;
            showConfirmCard();
        }
    }
    // set UI checkbox state
    private void setChecked(TextView checkBox, boolean checked) {
        if(checked) {
            checkBox.setText(getString(R.string.material_icon_check_full));
        } else {
            checkBox.setText(getString(R.string.material_icon_check_empty));
        }
    }

    // get UI checkbox state
    private boolean getChecked(TextView checkBox) {
        if(checkBox.getText() == getString(R.string.material_icon_check_full)) {
            return true;
        } else {
            return false;
        }
    }

    // this function gets all contacts from user's phonebook and sorts them from mobile, home, etc.
    public void getAllContacts(ContentResolver cr) {
        String prevName = "";
        if(NewEventActivity.allContacts.isEmpty()){
            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            // initialize Contact object for each contact in phonebook
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(name.equalsIgnoreCase(prevName)){
                    NewEventActivity.allContacts.add(new Contact(name + " (" +
                            getType(phones.getInt(phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.TYPE))) + ")",
                            phoneNumber, false));
                } else {
                    NewEventActivity.allContacts.add(new Contact(name,
                            phoneNumber, false));
                }
                prevName = name;
            }
            phones.close();
        }
    }

    // util function for getAllContacts that identifies duplicates and specifies the number type
    // if a duplicate exists
    public String getType(int value){
        switch(value){
            case(ContactsContract.CommonDataKinds.Phone.TYPE_HOME):
                return "home";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE):
                return "cell";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_WORK):
                return "work";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK):
                return "work fax";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME):
                return "home fax";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_PAGER):
                return "pager";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_OTHER):
                return "other";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK):
                return "callback";
            case(ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN):
                return "company main";
            default:
                return "";
        }
    }

    // contacts adapter, implements filtering and alphabetized indexing
    class ContactsAdapter extends BaseAdapter implements SectionIndexer, Filterable {
        TextView checkBox;
        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();
        View rootView;

        ContactsAdapter(Context context, List<Contact> contacts) {
            // set originalContacts and filteredContacts as original Contacts list
            NewEventActivity.allContacts = contacts;
            filteredContacts = contacts;
            mInflater = LayoutInflater.from(context);
            mInflater = (LayoutInflater) NewEventActivity.this.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }
        public class ViewHolder {
            TextView name;
            TextView checked;
        }
        @Override
        public int getCount() {
            return filteredContacts.size();
        }
        @Override
        public Object getItem(int position) {
            // return the name of the contact at that position (for listview)
            return filteredContacts.get(position).getName();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            rootView = convertView;
            if (convertView == null) rootView = mInflater.inflate(R.layout.all_contacts_row, null);

            TextView tv = (TextView) rootView.findViewById(R.id.contact_name);
            checkBox = (TextView) rootView.findViewById(R.id.contact_is_checked);
            tv.setText("Name: " + filteredContacts.get(position).getName());

            // TODO: Uncomment next line when using phone number as username to query
            //tv1.setText("Phone No:" + phone.get(position));
            checkBox.setTag(position);
            // check checkboxes that were previously already checked
            setChecked(checkBox, filteredContacts.get(position).getIsChecked());

            // A ViewHolder keeps references to children views to avoid unnecessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.all_contacts_row, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.contact_name);
                holder.checked = (TextView) convertView.findViewById(R.id.contact_is_checked);

                // Bind the data efficiently with the holder.
                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // If weren't re-ordering this you could rely on what you set last time
            holder.name.setText(filteredContacts.get(position).getName());
            setChecked(holder.checked, filteredContacts.get(position).getIsChecked());

            return convertView;
        }

        private void setChecked(TextView checkBox, boolean checked) {
            if(checked) {
                checkBox.setText(getString(R.string.material_icon_check_full));
            } else {
                checkBox.setText(getString(R.string.material_icon_check_empty));
            }
        }

        // for alphabetized sectioning
        @Override
        public int getPositionForSection(int section) {
            // If there is no item for current section, previous section will be selected
            for (int i = section; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(String.valueOf(getItem(j).toString().charAt(0)), String.valueOf(k)))
                                return j;
                        }
                    } else {
                        if (StringMatcher.match(String.valueOf(getItem(j).toString().charAt(0)), String.valueOf(mSections.charAt(i))))
                            return j;
                    }
                }
            }
            return 0;
        }
        @Override
        public int getSectionForPosition(int position) {
            return 0;
        }
        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }
        @Override
        public Filter getFilter() {
            return mFilter;
        }
        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                // remember original list
                final List<Contact> origList = NewEventActivity.allContacts;
                int count = origList.size();

                // create new list that will be used as filteredList
                ArrayList<Contact> newList = new ArrayList<Contact>(count);

                String filterableString;

                // populate new list based on filter criteria
                for (int i = 0; i < count; i++) {
                    filterableString = origList.get(i).getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        newList.add(origList.get(i));
                    }
                }
                results.values = newList;
                results.count = newList.size();
                return results;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // set result of filter as the filteredContacts
                filteredContacts = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
