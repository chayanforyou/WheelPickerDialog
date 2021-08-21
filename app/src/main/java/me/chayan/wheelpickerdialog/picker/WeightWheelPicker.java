package me.chayan.wheelpickerdialog.picker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
public class WeightWheelPicker extends WheelPicker {

    private static final int MIN_WEIGHT = 2;
    private static final int MAX_WEIGHT = 180;
    private static final int DEFAULT_WEIGHT = 65;

    private int mDefaultWeight = DEFAULT_WEIGHT;

    public WeightWheelPicker(Context context) {
        this(context, DEFAULT_WEIGHT);
    }

    public WeightWheelPicker(Context context, int defaultWeight) {
        super(context);
        this.mDefaultWeight = defaultWeight;
        init();
    }

    private void init() {

        final List<Integer> mList = fillRange(MIN_WEIGHT, MAX_WEIGHT);

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
                return new StringItemView(String.valueOf(mList.get(row))).onCreateView(parent);
            }

            @Override
            public void onBindView(ViewGroup parent, View convertView, int row, int component) {
                new StringItemView(String.valueOf(mList.get(row))).onBindView(parent, convertView, row);
            }

            @Override
            public String labelOfComponent(int component) {
                // https://stackoverflow.com/a/5249678
                return String.format("%16s", "kg");
            }
        };

        setOptions(new PicketOptions.Builder()
                .dividerColor(Color.TRANSPARENT)
                .dividedEqually(false)
                .cyclic(false)
                .build());
        setAdapter(adapter);

        setDefaultWeight();

        /*setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] position) {
                if (mOnWeightSelectListener != null) {
                    mOnWeightSelectListener.OnWeightSelected(mList.get(position[0]));
                }
            }
        });*/

        DialogUtil.setOnWeightSelectListener(new DialogUtil.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                if (mOnWeightSelectListener != null) {
                    int[] position = getSelectedPositions();
                    mOnWeightSelectListener.OnWeightSelected(mList.get(position[0]));
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

    private void setDefaultWeight() {
        if (mDefaultWeight <= MIN_WEIGHT || mDefaultWeight >= MAX_WEIGHT) {
            mDefaultWeight = 0;
        } else {
            mDefaultWeight = mDefaultWeight - MIN_WEIGHT;
        }
        setSelection(0, mDefaultWeight);
    }

    private OnWeightSelectListener mOnWeightSelectListener;

    public void setOnWeightSelectListener(OnWeightSelectListener onHeightSelectListener) {
        mOnWeightSelectListener = onHeightSelectListener;
    }

    public interface OnWeightSelectListener {
        void OnWeightSelected(int weight);
    }
}

