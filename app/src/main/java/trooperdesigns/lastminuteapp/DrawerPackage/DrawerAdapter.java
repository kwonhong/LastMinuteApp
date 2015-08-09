package trooperdesigns.lastminuteapp.DrawerPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import trooperdesigns.lastminuteapp.R;

/**
 * Created by james on 15-06-13.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private NavigationDrawerItem[] items;

    public DrawerAdapter(Context context, NavigationDrawerItem[] items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.drawer_item, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
        TextView txtView = (TextView) convertView.findViewById(R.id.txtView);

        NavigationDrawerItem item = (NavigationDrawerItem) getItem(position);
        imageView.setImageDrawable(item.getIcon());
        txtView.setText(item.getMenuItemText());

        return convertView;
    }
}
