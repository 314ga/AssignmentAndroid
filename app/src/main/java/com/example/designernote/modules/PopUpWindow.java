package com.example.designernote.modules;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.designernote.R;
import com.example.designernote.interfaces.OnPopupButtonAction;

public class PopUpWindow {

    private OnPopupButtonAction onPopupButtonAction;
    private PopupWindow POPUP_WINDOW_SCORE = null;
    public void setPopupButtonListener(OnPopupButtonAction onPopupButtonAction) {
        this.onPopupButtonAction = onPopupButtonAction;
    }
    public void setPopup(String message, Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_window, null);

        // Creating the PopupWindow
        POPUP_WINDOW_SCORE = new PopupWindow(context);
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);

        // prevent clickable background
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);

        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView txtMessage = (TextView) layout.findViewById(R.id.layout_popup_txtMessage);
        txtMessage.setText(message);

        Button butOne = (Button) layout.findViewById(R.id.layout_popup_butYes);
        butOne.setOnClickListener(v -> {
            if(onPopupButtonAction != null)
                onPopupButtonAction.onConfirmPopupBtn();

            POPUP_WINDOW_SCORE.dismiss();
        });

        Button butDismiss = (Button) layout.findViewById(R.id.layout_popup_butNo);
        butDismiss.setOnClickListener(v -> POPUP_WINDOW_SCORE.dismiss());
    }

    public PopupWindow getPOPUP_WINDOW_SCORE() {
        return POPUP_WINDOW_SCORE;
    }
}
