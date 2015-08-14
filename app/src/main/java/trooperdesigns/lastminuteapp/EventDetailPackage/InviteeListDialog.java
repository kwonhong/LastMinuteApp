package trooperdesigns.lastminuteapp.EventDetailPackage;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import trooperdesigns.lastminuteapp.R;

public class InviteeListDialog extends Dialog {

    ListView listView;
    TextView backBtn;
    Context context;

    public InviteeListDialog(Context context, List<Invitee> invitees) {
        super(context);
        this.context = context;
        setDialogContent(invitees);
    }

    private void setDialogContent(List<Invitee> invitees) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.custom_invitee_list_dialog);
        this.setTitle(null);

        backBtn = (TextView) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new InviteeListAdapter(getContext(), invitees));
        listView.setFitsSystemWindows(true);
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);

        listView.setDivider(null);
    }

}