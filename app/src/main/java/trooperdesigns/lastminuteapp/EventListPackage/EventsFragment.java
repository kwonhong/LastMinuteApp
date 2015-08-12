package trooperdesigns.lastminuteapp.EventListPackage;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import trooperdesigns.lastminuteapp.R;

public class EventsFragment extends ListFragment implements OnDismissCallback {

	private static final int INITIAL_DELAY_MILLIS = 300;
	private EventListAdapter eventListAdapter;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_events, container,
				false);

		listView = (ListView) rootView.findViewById(R.id.list_view);

		return rootView;
	}

	@Override
	public void onDismiss(@NonNull final ViewGroup listView,
						  @NonNull final int[] reverseSortedPositions) {
		for (int position : reverseSortedPositions) {
			// TODO: Bug: no 'remove' function in ParseQueryAdapter, need to implement
			// currently can't swipe away a card
			//eventListAdapter.remove(eventListAdapter.getItem(position));
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		// set up listview in onActivityCreated, otherwise won't have activity context which is
		// necessary for swingBottomInAnimationAdapter
		ListView listView = getListView();

		eventListAdapter = new EventListAdapter(getActivity());
		SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(
				new SwipeDismissAdapter(eventListAdapter, this));
		swingBottomInAnimationAdapter.setAbsListView(listView);

		assert swingBottomInAnimationAdapter.getViewAnimator() != null;
		swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(
				INITIAL_DELAY_MILLIS);

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
		listView.setAdapter(swingBottomInAnimationAdapter);

//		listView.setOnItemLongClickListener(new OnItemLongClickListener(){
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> list, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				DummyModel object = (DummyModel) list.getItemAtPosition(position);
//				Toast.makeText(getActivity(), "Long click on: " + position + ": " + object.getText(),
//						Toast.LENGTH_SHORT).show();
//				return true;
//			}
//
//		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	}
}