package me.chayan.wheelpickerdialog.picker;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.chayan.wheelpickerdialog.utils.DialogUtil;
import me.chayan.widget.adapter.PickerAdapter;
import me.chayan.widget.model.StringItemView;
import me.chayan.widget.picker.PicketOptions;
import me.chayan.widget.picker.WheelPicker;

/**
 * reated by Chayan on 2021/8/17.
 */
public class DateWheelPicker extends WheelPicker {

    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;

    protected Calendar mCurrentDate;
    protected Calendar mMinDate;
    protected Calendar mMaxDate;
    private Calendar mTempDate;

    private OnDateChangedListener mOnDateChangedListener;

    public DateWheelPicker(Context context) {
        this(context, null);
    }

    public DateWheelPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // initialization based on locale
        setCurrentLocale(Locale.getDefault());

        // set the min date giving priority of the minDate over startYear
        mTempDate.clear();
        mTempDate.set(DEFAULT_START_YEAR, 0, 1);
        setMinDate(mTempDate.getTimeInMillis());

        // set the max date giving priority of the maxDate over endYear
        mTempDate.clear();
        mTempDate.set(DEFAULT_END_YEAR, 11, 31);
        setMaxDate(mTempDate.getTimeInMillis());

        init(mCurrentDate.get(Calendar.YEAR), mCurrentDate.get(Calendar.MONTH),
                mCurrentDate.get(Calendar.DAY_OF_MONTH), null);
    }

    /**
     * Initialize the state. If the provided values designate an inconsistent
     * date the values are normalized before updating the spinners.
     *
     * @param year                  The initial year.
     * @param monthOfYear           The initial month <strong>starting from zero</strong>.
     * @param dayOfMonth            The initial day of the month.
     * @param onDateChangedListener How user is notified date is changed by
     *                              user, can be null.
     */
    public void init(int year, int monthOfYear, int dayOfMonth, OnDateChangedListener onDateChangedListener) {
        setDate(year, monthOfYear, dayOfMonth);
        mOnDateChangedListener = onDateChangedListener;

        init0();
    }

    public void setMinDate(long minDate) {
        mTempDate.setTimeInMillis(minDate);
        if (mTempDate.get(Calendar.YEAR) == mMinDate.get(Calendar.YEAR)
                && mTempDate.get(Calendar.DAY_OF_YEAR) == mMinDate.get(Calendar.DAY_OF_YEAR)) {
            // Same day, no-op.
            return;
        }
        mMinDate.setTimeInMillis(minDate);
    }

    public void setMaxDate(long maxDate) {
        mTempDate.setTimeInMillis(maxDate);
        if (mTempDate.get(Calendar.YEAR) == mMaxDate.get(Calendar.YEAR)
                && mTempDate.get(Calendar.DAY_OF_YEAR) == mMaxDate.get(Calendar.DAY_OF_YEAR)) {
            // Same day, no-op.
            return;
        }
        mMaxDate.setTimeInMillis(maxDate);
    }

    /**
     * Sets the current locale.
     *
     * @param locale The current locale.
     */
    protected void setCurrentLocale(Locale locale) {
        mTempDate = getCalendarForLocale(mTempDate, locale);
        mMinDate = getCalendarForLocale(mMinDate, locale);
        mMaxDate = getCalendarForLocale(mMaxDate, locale);
        mCurrentDate = getCalendarForLocale(mCurrentDate, locale);
    }

    /**
     * Gets a calendar for locale bootstrapped with the value of a given calendar.
     *
     * @param oldCalendar The old calendar.
     * @param locale      The locale.
     */
    private Calendar getCalendarForLocale(Calendar oldCalendar, Locale locale) {
        if (oldCalendar == null) {
            return Calendar.getInstance(locale);
        } else {
            final long currentTimeMillis = oldCalendar.getTimeInMillis();
            Calendar newCalendar = Calendar.getInstance(locale);
            newCalendar.setTimeInMillis(currentTimeMillis);
            return newCalendar;
        }
    }

    private void init0() {
        PickerAdapter adapter = new PickerAdapter() {

            @Override
            public int numberOfComponentsInWheelPicker(WheelPicker wheelPicker) {
                return 3;
            }

            @Override
            public int numberOfRowsInComponent(int component) {
                switch (component) {
                    case 0:
                        return mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                    case 1:
                        return mCurrentDate.getActualMaximum(Calendar.MONTH) + 1;
                    case 2:
                        return mMaxDate.get(Calendar.YEAR) - mMinDate.get(Calendar.YEAR);
                }
                return 0;
            }

            @Override
            public View onCreateView(ViewGroup parent, int row, int component) {
                switch (component) {
                    case 0:
                    case 1:
                        return new StringItemView(String.valueOf(row + 1)).onCreateView(parent);
                    case 2:
                        return new StringItemView(String.valueOf(mMinDate.get(Calendar.YEAR) + row)).onCreateView(parent);
                }
                return null;
            }

            @Override
            public void onBindView(ViewGroup parent, View convertView, int row, int component) {
                switch (component) {
                    case 0:
                    case 1:
                        new StringItemView(String.valueOf(row + 1)).onBindView(parent, convertView, row);
                        break;
                    case 2:
                        new StringItemView(String.valueOf(mMinDate.get(Calendar.YEAR) + row)).onBindView(parent, convertView, row);
                        break;
                }
            }

            @Override
            public String labelOfComponent(int component) {
                /*switch (component) {
                    case 0:
                        return "day";
                    case 1:
                        return "month";
                    case 2:
                        return "year";
                }*/
                return "";
            }
        };

        setOptions(new PicketOptions.Builder()
                .dividerColor(Color.TRANSPARENT)
                .dividedEqually(false)
                .linkage(false)
                .build());
        setAdapter(adapter);

        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH) - 1;
        int month = mCurrentDate.get(Calendar.MONTH);
        int year = mCurrentDate.get(Calendar.YEAR) - mMinDate.get(Calendar.YEAR);

        // default select 1/1/2001
        setSelection(0, 0);
        setSelection(1, 0);
        setSelection(2, 101);

        /*setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker parentView, int[] position) {
                if (position == null || position.length <= 0) return;

                // take care of wrapping of days and months to update greater fields
                int newDay = position[0];
                mTempDate.set(Calendar.DAY_OF_MONTH, newDay + 1);

                // month
                int newMonth = position[1];
                mTempDate.set(Calendar.MONTH, newMonth);

                // year
                int newYear = position[2];
                mTempDate.set(Calendar.YEAR, mMinDate.get(Calendar.YEAR) + newYear);

                // now set the date to the adjusted one
                if (mTempDate.before(mCurrentDate)) {
                    if (mOnDateChangedListener != null) {
                        mOnDateChangedListener.onDateChanged(mTempDate.getTime());
                    }
                }
            }
        });*/

        DialogUtil.setOnWeightSelectListener(new DialogUtil.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                int[] position = getSelectedPositions();

                // take care of wrapping of days and months to update greater fields
                int newDay = position[0];
                mTempDate.set(Calendar.DAY_OF_MONTH, newDay + 1);

                // month
                int newMonth = position[1];
                mTempDate.set(Calendar.MONTH, newMonth);

                // year
                int newYear = position[2];
                mTempDate.set(Calendar.YEAR, mMinDate.get(Calendar.YEAR) + newYear);

                if (mOnDateChangedListener != null) {
                    mOnDateChangedListener.onDateChanged(mTempDate.getTime());
                }
            }
        });
    }

    private void setDate(int year, int month, int dayOfMonth) {
        mCurrentDate.set(year, month, dayOfMonth);
        if (mCurrentDate.before(mMinDate)) {
            mCurrentDate.setTimeInMillis(mMinDate.getTimeInMillis());
        } else if (mCurrentDate.after(mMaxDate)) {
            mCurrentDate.setTimeInMillis(mMaxDate.getTimeInMillis());
        }
    }

    /**
     * Set the callback that indicates the date has been adjusted by the user.
     *
     * @param onDateChangedListener How user is notified date is changed by
     *                              user, can be null.
     */
    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        mOnDateChangedListener = onDateChangedListener;
    }

    /**
     * The callback used to indicate the user changed the date.
     */
    public interface OnDateChangedListener {

        /**
         * Called upon a date change.
         *
         * @param date The date that was set.
         */
        void onDateChanged(Date date);
    }
}
