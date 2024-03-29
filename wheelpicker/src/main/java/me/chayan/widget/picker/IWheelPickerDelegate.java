package me.chayan.widget.picker;

import android.content.res.Configuration;
import android.os.Parcelable;

import me.chayan.widget.adapter.PickerAdapter;

/**
 * A delegate interface that defined the public API of the WheelPicker. Allows different
 * WheelPicker implementations. This would need to be implemented by the WheelPicker delegates
 * for the real behavior.
 */
interface IWheelPickerDelegate {

    void setOnDataChangedListener(WheelPicker.OnDataChangedListener onDataChangedListener);

    void setAdapter(PickerAdapter pickerAdapter);

    void setSelection(int component, int row);

    void setOnItemSelectedListener(me.chayan.widget.picker.WheelPicker.OnItemSelectedListener listener);

    int[] getSelectedPositions();

    void onMeasure();

    void onLayout();

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void onConfigurationChanged(Configuration newConfig);

    Parcelable onSaveInstanceState(Parcelable superState);

    void onRestoreInstanceState(Parcelable state);
}
