package trooperdesigns.lastminuteapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import trooperdesigns.lastminuteapp.Adapter.SubcategoryAdapter;

public class CategoriesListViewActivity extends ActionBarActivity implements
		OnItemClickListener {

	public static final String LIST_VIEW_CATEGORY_EXPANDABLES = "trooperdesigns.lastminuteapp.Expandables";
	public static final String LIST_VIEW_CATEGORY_DRAG_AND_DROP = "trooperdesigns.lastminuteapp.DragAndDrop";
	public static final String LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS = "trooperdesigns.lastminuteapp.SwipeToDissmiss";
	public static final String LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS = "trooperdesigns.lastminuteapp.AppearanceAnimations";
	public static final String LIST_VIEW_CATEGORY_STICKY_HEADER = "trooperdesigns.lastminuteapp.StickyHeader";
	public static final String LIST_VIEW_CATEGORY_GOOGLE_CARDS = "trooperdesigns.lastminuteapp.GoogleCards";

	public static final String APPEARANCE_ANIMATIONS_LIST_VIEW = "Appearance animations";

	public static final String LIST_VIEW_TRAVEL = "Travel";
	public static final String LIST_VIEW_SOCIAL = "Social";
	public static final String LIST_VIEW_MEDIA = "Media";
	public static final String LIST_VIEW_SHOP = "Shop";
	public static final String LIST_VIEW_UNIVERSAL = "Universal";

	public static final String LIST_VIEW_OPTION_EXPANDABLE = "Expandable";
	public static final String LIST_VIEW_OPTION_DRAG_AND_DROP = "Drag&Drop";
	public static final String LIST_VIEW_OPTION_SWIPE_TO_DISSMISS = "Swipe-to-dissmiss";
	public static final String LIST_VIEW_OPTION_APPERANCE_ALPHA = "Appearance animation (alpha)";
	public static final String LIST_VIEW_OPTION_APPERANCE_RANDOM = "Appearance animation (random)";
	public static final String LIST_VIEW_OPTION_STICKI_LIST_HEADERS = "Sticky list headers"; // Coming
																								// soon
	public static final String LIST_VIEW_OPTION_GOOGLE_CARDS = "Google Cards";
	public static final String LIST_VIEW_OPTION_APPEARANCE_ANIMIATIONS = "Appearance animations";

	private ListView mListView;
	private String listViewCategory;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);

		mListView = (ListView) findViewById(R.id.list_view);
		// String category = GOOGLE_CARDS_LIST_VIEW;
		String category = "";
		Bundle extras = getIntent().getExtras();
		if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_EXPANDABLES)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(LIST_VIEW_CATEGORY_EXPANDABLES,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras.getString(LIST_VIEW_CATEGORY_EXPANDABLES);
			}
		} else if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_DRAG_AND_DROP)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(LIST_VIEW_CATEGORY_DRAG_AND_DROP,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras.getString(LIST_VIEW_CATEGORY_DRAG_AND_DROP);
			}
		} else if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(
						LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras
						.getString(LIST_VIEW_CATEGORY_SWIPE_TO_DISSMISS);
			}
		} else if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(
						LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras
						.getString(LIST_VIEW_CATEGORY_APPEARANCE_ANIMATIONS);
			}
		} else if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_STICKY_HEADER)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(LIST_VIEW_CATEGORY_STICKY_HEADER,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras.getString(LIST_VIEW_CATEGORY_STICKY_HEADER);
			}
		} else if (extras != null
				&& extras.containsKey(LIST_VIEW_CATEGORY_GOOGLE_CARDS)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
				category = extras.getString(LIST_VIEW_CATEGORY_GOOGLE_CARDS,
						LIST_VIEW_UNIVERSAL);
			} else {
				category = extras.getString(LIST_VIEW_CATEGORY_GOOGLE_CARDS);
			}
		}
		if (!category.equals(LIST_VIEW_OPTION_APPEARANCE_ANIMIATIONS))
			setUpListView();
		else
			setUpApperanceListView();

		listViewCategory = category;

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(category);
	}

	private void setUpListView() {
		List<String> subcategories = new ArrayList<>();
		subcategories.add(LIST_VIEW_MEDIA);
		subcategories.add(LIST_VIEW_SHOP);
		subcategories.add(LIST_VIEW_SOCIAL);
		subcategories.add(LIST_VIEW_TRAVEL);
		subcategories.add(LIST_VIEW_UNIVERSAL);
		mListView.setAdapter(new SubcategoryAdapter(this, subcategories));
		mListView.setOnItemClickListener(this);
	}

	private void setUpApperanceListView() {
		List<String> subcategories = new ArrayList<>();
		// subcategories.add(LIST_VIEW_TRAVEL);
		// subcategories.add(LIST_VIEW_SOCIAL);
		subcategories.add(LIST_VIEW_OPTION_APPERANCE_ALPHA);
		subcategories.add(LIST_VIEW_OPTION_APPERANCE_RANDOM);
		mListView.setAdapter(new SubcategoryAdapter(this, subcategories));
		mListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		String category = parent.getAdapter().getItem(position).toString();
		if (category.equals(LIST_VIEW_SOCIAL)) {
			intent = getSocialCategoryIntent(listViewCategory);
		} else if (category.equals(LIST_VIEW_TRAVEL)) {
			intent = getTravelCategoryIntent(listViewCategory);
		} else {
			if (!listViewCategory
					.equals(LIST_VIEW_OPTION_APPEARANCE_ANIMIATIONS))
				intent = getCategoryIntent(listViewCategory);
			else
				intent = getCategoryIntent(category);
		}
		startActivity(intent);
	}

	private Intent getTravelCategoryIntent(String category) {
		Intent intent = null;
		if (category.equals(LIST_VIEW_OPTION_SWIPE_TO_DISSMISS)) {
			intent = new Intent(this, SwipToDissmissListViewActivity.class);
			intent.putExtra(LIST_VIEW_OPTION_SWIPE_TO_DISSMISS,
					SwipToDissmissListViewActivity.SWIPE_TO_DISSMISS_TRAVEL);
		} else if (category.equals(LIST_VIEW_OPTION_GOOGLE_CARDS)) {
			intent = new Intent(this, GoogleCardsTravelActivity.class);
		} else {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_APPERANCE_RANDOM);
		}
		return intent;
	}

	private Intent getSocialCategoryIntent(String category) {
		Intent intent = null;
		if (category.equals(LIST_VIEW_OPTION_SWIPE_TO_DISSMISS)) {
			intent = new Intent(this, SwipToDissmissListViewActivity.class);
			intent.putExtra(LIST_VIEW_OPTION_SWIPE_TO_DISSMISS,
					SwipToDissmissListViewActivity.SWIPE_TO_DISSMISS_SOCIAL);
		} else {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_APPERANCE_RANDOM);
		}
		return intent;
	}

	private Intent getCategoryIntent(String category) {
		Intent intent = null;
		 if (category.equals(LIST_VIEW_OPTION_DRAG_AND_DROP)) {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_DRAG_AND_DROP);
		} else if (category.equals(LIST_VIEW_OPTION_SWIPE_TO_DISSMISS)) {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_SWIPE_TO_DISSMISS);
		} else if (category.equals(LIST_VIEW_OPTION_GOOGLE_CARDS)) {
			intent = new Intent(this, GoogleCardsActivity.class);
		} else if (category.equals(LIST_VIEW_OPTION_APPERANCE_ALPHA)) {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_APPERANCE_ALPHA);
		} else if (category.equals(LIST_VIEW_OPTION_APPERANCE_RANDOM)) {
			intent = new Intent(this, ListViewsActivity.class);
			intent.putExtra(ListViewsActivity.LIST_VIEW_OPTION,
					LIST_VIEW_OPTION_APPERANCE_RANDOM);
		}
		return intent;
	}
}