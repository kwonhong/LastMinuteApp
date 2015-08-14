package trooperdesigns.lastminuteapp.NewEventPackage;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import trooperdesigns.lastminuteapp.EventListPackage.EventsFragment;
import trooperdesigns.lastminuteapp.R;
import trooperdesigns.lastminuteapp.UtilPackage.ImageUtil;
import trooperdesigns.lastminuteapp.UtilPackage.StringMatcher;
import trooperdesigns.lastminuteapp.widget.IndexableListView;

public class NewEventActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private CardView cardView1, cardView2, cardView3, confirmCardView;
    private ObjectAnimator cardViewMover1, cardViewMover2, cardViewMover3, confirmCardViewMover;
    private int currentCard;

    private TextView socialCheck1;
    private TextView socialCheck2;
    private TextView socialCheck3;
    private ImageView socialImage1;
    private ImageView socialImage2;
    private ImageView socialImage3;

    // TODO: empty allContacts list when event is created
    static List<Contact> allContacts = new ArrayList<>();
    List<Contact> selectedContacts = new ArrayList<>();

    ContactsAdapter contactsAdapter;

    private List<Contact> originalContacts = new ArrayList<>();
    private List<Contact> filteredContacts = new ArrayList<>();
    private ArrayList<Contact> contactsResult;
    // used to remove contacts who were unchecked
    private ArrayList<String> uncheckedContacts;

    private TextView select;
    private EditText search;
    IndexableListView lv;
    public static boolean isKeyboardOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        FragmentManager fragmentManager = getSupportFragmentManager();
        EventsFragment eventsFragment = new EventsFragment();

        // Setup current Fragment as EventListFragment
//        fragmentManager.beginTransaction()
//                .replace(R.id.frameContainer, eventsFragment)
//                .commit();

        cardView1 = (CardView) findViewById(R.id.cardview1);
        cardView2 = (CardView) findViewById(R.id.cardview2);
        cardView3 = (CardView) findViewById(R.id.cardview3);
        confirmCardView = (CardView) findViewById(R.id.confirm_card);

        currentCard = 1;

        TextView nextButton = (TextView) findViewById(R.id.next_button);
        TextView nextButton2 = (TextView) findViewById(R.id.next_button_2);
//        TextView nextButton3 = (TextView) findViewById(R.id.next_button_3);
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

//        nextButton3.setOnClickListener(nextClickHandler);
        backButton3.setOnClickListener(backClickHandler);

//        socialCheck1 = (TextView) findViewById(R.id.fragment_check_boxes_social_check1);
//        socialCheck2 = (TextView) findViewById(R.id.fragment_check_boxes_social_check2);
//        socialCheck3 = (TextView) findViewById(R.id.fragment_check_boxes_social_check3);
//        socialImage1 = (ImageView) findViewById(R.id.fragment_check_boxes_social_image1);
//        socialImage2 = (ImageView) findViewById(R.id.fragment_check_boxes_social_image2);
//        socialImage3 = (ImageView) findViewById(R.id.fragment_check_boxes_social_image3);
//
//
//        socialCheck1.setOnClickListener(this);
//        socialCheck2.setOnClickListener(this);
//        socialCheck3.setOnClickListener(this);
//
//        ImageUtil.displayRoundImage(socialImage1, "http://pengaja.com/uiapptemplate/newphotos/profileimages/1.jpg", null);
//        ImageUtil.displayRoundImage(socialImage2, "http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
//        ImageUtil.displayRoundImage(socialImage3, "http://pengaja.com/uiapptemplate/newphotos/profileimages/3.jpg", null);

        // TODO: setting to full screen makes checkboxes not work correctly for some reason..
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isKeyboardOpen = false;
        originalContacts = NewEventActivity.allContacts;

        search = (EditText) findViewById(R.id.search);

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

        getAllContacts(this.getContentResolver());
        lv  = (IndexableListView) findViewById(R.id.lv);
        contactsAdapter = new ContactsAdapter(getApplicationContext(), NewEventActivity.allContacts);
        lv.setClickable(true);
        lv.setAdapter(contactsAdapter);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);

        // adding
        select = (TextView) findViewById(R.id.confirm_button);
        select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Boolean noneSelected = true;
                // check all contacts if any are selected
                for(int i = 0; i < NewEventActivity.allContacts.size(); i++){
                    if(NewEventActivity.allContacts.get(i).getIsChecked() == true){
                        noneSelected = false;
                    }
                }
                // if at least one selected, return
                if(!noneSelected){
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

                confirmCardViewMover = ObjectAnimator.ofFloat(confirmCardView, "translationY", 0,
                        confirmCardView.getHeight());
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.fragment_check_boxes_social_check1:
//                // click on explore button
//                if (socialCheck1.getText() == getString(R.string.material_icon_check_empty)) {
//                    socialCheck1
//                            .setText(getString(R.string.material_icon_check_full));
//                } else {
//                    socialCheck1
//                            .setText(getString(R.string.material_icon_check_empty));
//                }
//                break;
//            case R.id.fragment_check_boxes_social_check2:
//                // click on explore button
//                if (socialCheck2.getText() == getString(R.string.material_icon_check_empty)) {
//                    socialCheck2
//                            .setText(getString(R.string.material_icon_check_full));
//                } else {
//                    socialCheck2
//                            .setText(getString(R.string.material_icon_check_empty));
//                }
//                break;
//            case R.id.fragment_check_boxes_social_check3:
//                // click on explore button
//                if (socialCheck3.getText() == getString(R.string.material_icon_check_empty)) {
//                    socialCheck3
//                            .setText(getString(R.string.material_icon_check_full));
//                } else {
//                    socialCheck3
//                            .setText(getString(R.string.material_icon_check_empty));
//                }
//                break;
//        }
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

//    @Override
//    public void onBackPressed() {
//        // if sliding panel is open, close it
//        InputMethodManager imm = (InputMethodManager) this
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        if (imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0) == true) {
//            super.onBackPressed();
//        } else if (imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0) == false && !search.getText().toString().matches("")) {
//            search.setText(null);
//        } else {
//            NewEventActivity.allContacts = originalContacts;
//            super.onBackPressed();
//        }
//        isKeyboardOpen = false;
//        return;
//    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
        filteredContacts.get(position).toggle();
    }

    public void getAllContacts(ContentResolver cr) {

        String prevName = "";

        if(NewEventActivity.allContacts.isEmpty()){
            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            // initialize Contact object for each contact in phonebook
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(name.equalsIgnoreCase(prevName)){
                    NewEventActivity.allContacts.add(new Contact(name + " (" +
                            getType(phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))) + ")",
                            phoneNumber, false));
                } else {
                    NewEventActivity.allContacts.add(new Contact(name,
                            phoneNumber, false));
                }
                prevName = name;

            }

            phones.close();

        } else {
            // contacts list already created
        }

    }

    class ContactsAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener, SectionIndexer, Filterable {
        TextView cb;
        private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Canvas canvas;

        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        View vi;

        ContactsAdapter(Context context, List<Contact> contacts) {

            // set originalContacts and filteredContacts as original Contacts list
            NewEventActivity.allContacts = contacts;
            filteredContacts = contacts;

            mInflater = LayoutInflater.from(context);
            mInflater = (LayoutInflater) NewEventActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            vi = convertView;
            if (convertView == null) vi = mInflater.inflate(R.layout.all_contacts_row, null);

            TextView tv = (TextView) vi.findViewById(R.id.contact_name);
            cb = (trooperdesigns.lastminuteapp.font.MaterialDesignIconsTextView) vi.findViewById(R.id.contact_is_checked);
            tv.setText("Name: " + filteredContacts.get(position).getName());

            // TODO: Uncomment next line when using phone number as username to query
            //tv1.setText("Phone No:" + phone.get(position));
            cb.setTag(position);
            //cb.setChecked(filteredContacts.get(position).getIsChecked());
            //cb.setOnCheckedChangeListener(this);

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
                //holder.phone = (TextView) convertView.findViewById(R.id.textView2);

                // Bind the data efficiently with the holder.
                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // If weren't re-ordering this you could rely on what you set last time
            holder.name.setText(filteredContacts.get(position).getName());
            //holder.checked.setChecked(filteredContacts.get(position).getIsChecked());

            return convertView;
        }

        public class ViewHolder {
            TextView name;
            TextView checked;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            Contact c = filteredContacts.get((Integer) buttonView.getTag());
            c.setIsChecked(isChecked);

        }

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

        public View getView(){
            return vi;
        }
    }

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
}
