package me.chayan.wheelpickerdialog.picker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import me.chayan.wheelpickerdialog.utils.DialogUtil;
import me.chayan.widget.picker.WheelPicker;
import me.chayan.widget.adapter.PickerAdapter;
import me.chayan.widget.model.StringItemView;
import me.chayan.widget.picker.PicketOptions;


/**
 * Created by Chayan on 2019/3/20.
 */
public class GenderWheelPicker extends WheelPicker {

    private OnGenderSelectListener mOnGenderSelectListener;

    public GenderWheelPicker(Context context) {
        super(context);
        init();
    }

    private void init() {

        final List<String> mList = Arrays.asList("Male", "Female");

        PickerAdapter adapter = new PickerAdapter() {
            @Override
            public int numberOfComponentsInWheelPicker(WheelPicker wheelPicker) {
                return 1;
            }

            @Override
            public int numberOfRowsInComponent(int component) {
                return mList.size();
            }

            @Override
            public View onCreateView(ViewGroup parent, int row, int component) {
                return new StringItemView(mList.get(row)).onCreateView(parent);
            }

            @Override
            public void onBindView(ViewGroup parent, View convertView, int row, int component) {
                new StringItemView(mList.get(row)).onBindView(parent, convertView, row);
            }
        };

        setOptions(new PicketOptions.Builder()
                .dividerColor(Color.TRANSPARENT)
                .cyclic(false)
                .build());
        setAdapter(adapter);

        /*setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] position) {
                if (mOnGenderSelectListener != null) {
                    mOnGenderSelectListener.OnGenderSelected(GenderWheelPicker.this, mList.get(position[0]));
                }
            }
        });*/

        DialogUtil.setOnWeightSelectListener(new DialogUtil.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                if (mOnGenderSelectListener != null) {
                    int[] position = getSelectedPositions();
                    mOnGenderSelectListener.OnGenderSelected(GenderWheelPicker.this, mList.get(position[0]));
                }
            }
        });
    }


    public void setOnGenderSelectListener(OnGenderSelectListener onHeightSelectListener) {
        mOnGenderSelectListener = onHeightSelectListener;
    }

    public interface OnGenderSelectListener {
        void OnGenderSelected(GenderWheelPicker view, String genderSelected);
    }
}

