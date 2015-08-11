package trooperdesigns.lastminuteapp.EventListPackage;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	public View getItemView(ParseObject parseObject, View convertView, ViewGroup parentView) {

		convertView =  (convertView == null) ? setUpConvertView(event, convertView, parentView)
				: convertView;
		ViewHolder holder = (ViewHolder) convertView.getTag();

		// Setting Other TextVies
		holder.title.setText(parseObject.getString("title"));
//		holder.title.setText("Basketball");
		holder.text.setText("Basketball 4 vs 4. Please come join." +
				" It will be really fun!! See you guys all there");
		holder.categoryName.setText("Sports");
		setStatusColor(holder.image, Invitation.Status.values()[0]);

		return convertView;
	}

	private View setUpConvertView(ParseObject parseObject, View convertView, ViewGroup parentView) {

		// Inflating Custom View
		convertView = mInflater.inflate(
				R.layout.list_item_google_cards_travel,
				parentView,
				false);

		// Setting the view holder
		ViewHolder holder = new ViewHolder();
		holder.image = (ImageView) convertView.findViewById(R.id.list_item_google_cards_travel_image);
		holder.categoryName = (TextView) convertView.findViewById(R.id.list_item_google_cards_travel_category_name);
		holder.title = (TextView) convertView.findViewById(R.id.list_item_google_cards_travel_title);
		holder.text = (TextView) convertView.findViewById(R.id.list_item_google_cards_travel_text);

		// Configuring Buttons
		convertView.findViewById(R.id.btnAccept).setOnClickListener(this);
		convertView.findViewById(R.id.btnDecline).setOnClickListener(this);
		convertView.findViewById(R.id.btnOnMyWay).setOnClickListener(this);
		convertView.findViewById(R.id.btnAccept).setTag(holder.image);
		convertView.findViewById(R.id.btnDecline).setTag(holder.image);
		convertView.findViewById(R.id.btnOnMyWay).setTag(holder.image);

		// Returning the holder
		convertView.setTag(holder);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btnAccept:
				setStatusColor((ImageView) v.getTag(), Invitation.Status.ACCEPT);
				Toast.makeText(getContext(), "Accept", Toast.LENGTH_SHORT).show();
				break;
			case R.id.btnDecline:
				setStatusColor((ImageView) v.getTag(), Invitation.Status.DECLINE);
				Toast.makeText(getContext(), "Decline", Toast.LENGTH_SHORT).show();
				break;
			case R.id.btnOnMyWay:
				setStatusColor((ImageView) v.getTag(), Invitation.Status.ON_MY_WAY);
				Toast.makeText(getContext(), "On my Way", Toast.LENGTH_SHORT).show();
				break;

			default:
				//TODO REPORT ERROR

		}
	}

	private void setStatusColor(ImageView imageView, Invitation.Status status) {

		switch (status) {
			case PENDING:
				imageView.setBackgroundColor(Color.parseColor("#99ffff00"));
				break;
			case ACCEPT:
				imageView.setBackgroundColor(Color.parseColor("#99669999"));
				break;
			case DECLINE:
				imageView.setBackgroundColor(Color.parseColor("#99ff0000"));
				break;
			case ON_MY_WAY:
				imageView.setBackgroundColor(Color.parseColor("#99FF9900"));
				break;
			default:
				//TODO REPORT ERROR
		}

	}

	private static class ViewHolder {
		public ImageView image;
		public TextView categoryName;
		public TextView title;
		public TextView text;
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