package me.chayan.wheelpickerdialog.picker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.chayan.wheelpickerdialog.utils.DialogUtil;
import me.chayan.widget.adapter.PickerAdapter;
import me.chayan.widget.model.StringItemView;
import me.chayan.widget.picker.PicketOptions;
import me.chayan.widget.picker.WheelPicker;

/**
 * Created by Chayan on 2021/8/17.
 */
public class HeightWheelPicker extends WheelPicker {

    private static final int MIN_FEET = 1;
    private static final int MAX_FEET = 7;


    public HeightWheelPicker(Context context) {
        super(context);
        init();
    }

    private void init() {

        final List<Integer> mList = fillRange(MIN_FEET, MAX_FEET);

        PickerAdapter adapter = new PickerAdapter() {
            @Override
            public int numberOfComponentsInWheelPicker(WheelPicker wheelPicker) {
                return 2;
            }

            @Override
            public int numberOfRowsInComponent(int component) {
                switch (component) {
                    case 0:
                        return mList.size();
                    case 1:
                        return 12;
                }
                return 0;
            }

            @Override
            public View onCreateView(ViewGroup parent, int row, int component) {
                switch (component) {
                    case 0:
                        return new StringItemView(String.valueOf(mList.get(row))).onCreateView(parent);
                    case 1:
                        return new StringItemView(String.valueOf(row)).onCreateView(parent);
                }
                return null;
            }

            @Override
            public void onBindView(ViewGroup parent, View convertView, int row, int component) {
                switch (component) {
                    case 0:
                        new StringItemView(String.valueOf(mList.get(row))).onBindView(parent, convertView, row);
                        break;
                    case 1:
                        new StringItemView(String.valueOf(row)).onBindView(parent, convertView, row);
                        break;
                }
            }

            @Override
            public String labelOfComponent(int component) {
                switch (component) {
                    case 0:
                        return String.format("%3s", "feet");
                    case 1:
                        return String.format("%3s", "inch");
                }
                return "";
            }
        };

        setOptions(new PicketOptions.Builder()
                .dividerColor(Color.TRANSPARENT)
                .dividedEqually(false)
                .linkage(false)
                .build());
        setAdapter(adapter);

        // default select 5 feet 6 inch
        setSelection(0, 4);
        setSelection(1, 6);

        /*setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] position) {
                if (mOnHeightSelectListener != null) {
                    mOnHeightSelectListener.OnHeightSelected(mList.get(position[0]), position[1]);
                }
            }
        });*/

        DialogUtil.setOnWeightSelectListener(new DialogUtil.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                if (mOnHeightSelectListener != null) {
                    int[] position = getSelectedPositions();
                    mOnHeightSelectListener.OnHeightSelected(mList.get(position[0]), position[1]);
                }
            }
        });
    }

    private List<Integer> fillRange(int min, int max) {
        List<Integer> a_lst = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            a_lst.add(i);
        }
        return a_lst;
    }

    private OnHeightSelectListener mOnHeightSelectListener;

    public void setOnHeightSelectListener(OnHeightSelectListener onHeightSelectListener) {
        mOnHeightSelectListener = onHeightSelectListener;
    }

    public interface OnHeightSelectListener {
        void OnHeightSelected(int feet, int inch);
    }
}

