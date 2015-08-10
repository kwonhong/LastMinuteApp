package trooperdesigns.lastminuteapp.EventListPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import trooperdesigns.lastminuteapp.R;

public class EventListAdapter extends ParseQueryAdapter implements Filterable, View.OnClickListener {

	public static ParseObject event;
	private LayoutInflater mInflater;

	@SuppressWarnings("unchecked")
	public EventListAdapter(Context context) {
		
		// Use the QueryFactory to construct a PQA that will only show
		// Todos marked as high-pri
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Event");
				// First try to find from the cache and only then go to network
				//query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE); // or CACHE_ONLY
				//query.whereEqualTo("highPri", true);
				return query;
			}
		});

		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(ParseObject event, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_google_cards_travel, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.list_item_google_cards_travel_image);
			holder.categoryName = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_category_name);
			holder.title = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_title);
			holder.text = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_text);
			holder.explore = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_explore);
			holder.share = (TextView) convertView
					.findViewById(R.id.list_item_google_cards_travel_share);
			holder.explore.setOnClickListener(this);
			holder.share.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		DummyModel item = getItem(position);
//		ImageUtil.displayImage(holder.image, item.getImageURL(), null);
//		holder.title.setText(item.getText());
//		holder.explore.setTag(position);
//		holder.share.setTag(position);

		holder.title.setText(event.getString("title"));
		holder.text.setText(event.getString("details"));

		return convertView;


//
//		if (v == null) {
//			v = View.inflate(getContext(), R.layout.row, null);
//		}
//
//		super.getItemView(event, v, parent);
//
//		this.event = event;
//
//		// Add and download the image
//		ParseImageView eventImage = (ParseImageView) v.findViewById(R.id.icon);
//		eventImage.setFocusable(false);
//		ParseFile imageFile = event.getParseFile("image");
//		if (imageFile != null) {
//			eventImage.setParseFile(imageFile);
//			eventImage.loadInBackground();
//		}
//
//		// Add the title view
//		TextView titleTextView = (TextView) v.findViewById(R.id.eventTitle);
//		titleTextView.setFocusable(false);
//		titleTextView.setText(event.getString("title").toUpperCase());
//
//		// TextView for Location (using details for dummy)
//		TextView locationTextView = (TextView) v.findViewById(R.id.eventLocation);
//		locationTextView.setFocusable(false);
//		locationTextView.setText(event.getString("details").toUpperCase());
//
//		// TODO: Check that dates exist, otherwise parsing error
//
//		// get Date object and use for formatting
//		Date startDate = event.getDate("startTime");
//		Date endDate = event.getDate("endTime");
//
//		SimpleDateFormat dateFormat;
//
//		dateFormat = new SimpleDateFormat("MM");
//		String month = getMonthName(Integer.parseInt(dateFormat.format(startDate))).toUpperCase();
//
//		// textView for Date
//		dateFormat = new SimpleDateFormat(" dd yyyy");
//		TextView dateTextView = (TextView) v.findViewById(R.id.eventDate);
//		dateTextView.setFocusable(false);
//		dateTextView.setText(month + dateFormat.format(startDate));
//
//		// textView for start time
//		dateFormat = new SimpleDateFormat("hh:mma");
//		TextView timeTextView = (TextView) v.findViewById(R.id.eventTime);
//		timeTextView.setFocusable(false);
//		timeTextView.setText(dateFormat.format(startDate) + " - " + dateFormat.format(endDate));
//
//		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int possition = (Integer) v.getTag();
		switch (v.getId()) {
			case R.id.list_item_google_cards_travel_explore:
				// click on explore button
				break;
			case R.id.list_item_google_cards_travel_share:
				// click on share button
				break;
		}
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView categoryName;
		public TextView title;
		public TextView text;
		public TextView explore;
		public TextView share;
	}

	String getMonthName(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11 ) {
			month = months[num];
		}
		return month;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
	    Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                //arrayListNames = (List<String>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<String> FilteredArrayNames = new ArrayList<String>();

                // perform your search here using the searchConstraint String.

                constraint = constraint.toString().toLowerCase();
/*                for (int i = 0; i < mDatabaseOfNames.size(); i++) {
                    String dataNames = mDatabaseOfNames.get(i);
                    if (dataNames.toLowerCase().startsWith(constraint.toString()))  {
                        FilteredArrayNames.add(dataNames);
                    }
                }*/

                results.count = FilteredArrayNames.size();
                results.values = FilteredArrayNames;
                Log.e("VALUES", results.values.toString());

                return results;
            }
        };

        return filter;
	}

}
