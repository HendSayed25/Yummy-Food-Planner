package com.example.yummy_food_planner.presentation.shared.utils;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import com.example.yummy_food_planner.R;

public class CustomDialog {

    public static void showDialog(
            Context context,
            String title,
            String message,
            String signInText,
            String cancelText,
            Runnable confirmClick
    ) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);

        TextView titleTv = dialog.findViewById(R.id.titleTv);
        titleTv.setText(title);

        TextView messageTv = dialog.findViewById(R.id.messageTv);
        messageTv.setText(message);

        AppCompatButton confirmBtn = dialog.findViewById(R.id.signInBtnFromGuestMode);
        confirmBtn.setText(signInText);

        confirmBtn.setOnClickListener(v -> {
            confirmClick.run();
            dialog.dismiss();
        });

        AppCompatButton cancelBtn = dialog.findViewById(R.id.cancelBtn);
        cancelBtn.setText(cancelText);
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

        dialog.getWindow().setLayout(
                (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9),
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
