package trooperdesigns.lastminuteapp.EventListPackage;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import lombok.Setter;
import trooperdesigns.lastminuteapp.R;

/**
 * Created by james on 15-08-11.
 */
public class ConfirmDialog extends Dialog {

    TextView messageTxtView, confirmBtn, backBtn;
    @Setter CustomDialogListener customDialogListener;


    public ConfirmDialog(Context context, String confirmMsg) {
        super(context);
        setDialogContent(confirmMsg);
    }

    private void setDialogContent(String confirmMsg) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.custom_confirm_dialog);
        this.setTitle(null);

        // Setting Text
        messageTxtView = (TextView) findViewById(R.id.confirmMsgTxtView);
        messageTxtView.setText(confirmMsg);

        // Setting Buttons
        confirmBtn = (TextView) findViewById(R.id.btnConfirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogListener.handleConfirmAction();
            }
        });

        backBtn = (TextView) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogListener.handleBackAction();
            }
        });

    }



    public interface CustomDialogListener {
        void handleConfirmAction();
        void handleBackAction();
    }
}
