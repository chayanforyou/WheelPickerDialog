package me.chayan.wheelpickerdialog.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import me.chayan.wheelpickerdialog.R;
import me.chayan.wheelpickerdialog.databinding.DialogContentBinding;
import me.chayan.wheelpickerdialog.picker.WeightWheelPicker;

/**
 * Created by Chayan on 2019/4/21.
 */

public class DialogUtil {

    public static void showDialog(Context context, View v) {
        showDialog(context, " ", v);
    }

    public static void showDialog(Context context, String title, View v) {
        v.setPadding(20, 20, 20, 20);

        final Dialog bottomDialog = new Dialog(context, R.style.BottomDialog);
        DialogContentBinding binding = DialogContentBinding.inflate(LayoutInflater.from(context));

        binding.title.setText(title);

        binding.buttonConfirm.setOnClickListener(view -> {
            bottomDialog.dismiss();
            if (mOnConfirmClickListener != null) {
                mOnConfirmClickListener.onConfirmClick();
            }
        });

        binding.buttonCancel.setOnClickListener(view -> bottomDialog.dismiss());

        binding.content.removeAllViews();
        binding.content.addView(v);
        bottomDialog.setContentView(binding.getRoot());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(bottomDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.BottomDialog_Animation;
        bottomDialog.getWindow().setAttributes(lp);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    private static OnConfirmClickListener mOnConfirmClickListener;

    public static void setOnWeightSelectListener(OnConfirmClickListener onConfirmClickListener) {
        mOnConfirmClickListener = onConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }
}
