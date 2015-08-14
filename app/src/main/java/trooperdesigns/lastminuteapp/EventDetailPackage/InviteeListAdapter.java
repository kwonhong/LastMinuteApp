package trooperdesigns.lastminuteapp.EventDetailPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import trooperdesigns.lastminuteapp.EventListPackage.Invitation;
import trooperdesigns.lastminuteapp.R;

/**
 * Created by james on 15-08-12.
 */
public class InviteeListAdapter extends BaseAdapter {

    private List<Invitee> inviteeList;
    private LayoutInflater mInflater;
    private Context context;

    public InviteeListAdapter(Context context, List<Invitee> inviteeList) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inviteeList = inviteeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return inviteeList.size();
    }

    @Override
    public Object getItem(int position) {
        return inviteeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {

        convertView =  (convertView == null) ? setUpConvertView(convertView, parentView)
                : convertView;

        Invitee invitee = inviteeList.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.text.setText(invitee.getName());
        setStatusCircle(viewHolder.image, invitee.getStatus());

        return convertView;
    }

    private void setStatusCircle(ImageView imageView, Invitation.Status status) {
        switch (status) {
            case PENDING:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circle));
                break;
            case ACCEPT:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circle));
                break;
            case DECLINE:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circle));
                break;
            case ON_MY_WAY:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.green_circle));
                break;
            default:
                //TODO REPORT ERROR
        }

    }

    private View setUpConvertView(View convertView, ViewGroup parentView) {

        // Inflating Custom View
        convertView = mInflater.inflate(
                R.layout.friend_item,
                parentView,
                false);

        // Setting the view holder
        ViewHolder holder = new ViewHolder();
        holder.image = (ImageView) convertView.findViewById(R.id.image);
        holder.text = (TextView) convertView.findViewById(R.id.text);

        // Returning the holder
        convertView.setTag(holder);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView text;
    }
}
