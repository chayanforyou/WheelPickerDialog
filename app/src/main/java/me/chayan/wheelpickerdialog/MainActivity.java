package me.chayan.wheelpickerdialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.chayan.wheelpickerdialog.databinding.ActivityMainBinding;
import me.chayan.wheelpickerdialog.picker.DateWheelPicker;
import me.chayan.wheelpickerdialog.picker.GenderWheelPicker;
import me.chayan.wheelpickerdialog.picker.HeightWheelPicker;
import me.chayan.wheelpickerdialog.picker.WeightWheelPicker;
import me.chayan.wheelpickerdialog.utils.DialogUtil;

public class MainActivity extends AppCompatActivity {

    private final Context mContext = MainActivity.this;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.gender.setOnClickListener(view -> {
            GenderWheelPicker picker = new GenderWheelPicker(mContext);
            picker.setOnGenderSelectListener((view1, genderSelected) -> binding.genderTv.setText(genderSelected));
            DialogUtil.showDialog(mContext, picker);
        });

        binding.birthday.setOnClickListener(view -> {
            DateWheelPicker picker = new DateWheelPicker(mContext);
            picker.setOnDateChangedListener(date -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());
                binding.birthdayTv.setText(sdf.format(date));
            });
            DialogUtil.showDialog(mContext, picker);
        });

        binding.height.setOnClickListener(view -> {
            HeightWheelPicker picker = new HeightWheelPicker(mContext);
            picker.setOnHeightSelectListener((feet, inch) -> {
                String height = String.format(Locale.getDefault(), "%dft%din", feet, inch);
                binding.heightTv.setText(height);
            });
            DialogUtil.showDialog(mContext, picker);
        });

        binding.weight.setOnClickListener(view -> {
            WeightWheelPicker picker = new WeightWheelPicker(mContext);
            picker.setOnWeightSelectListener(new WeightWheelPicker.OnWeightSelectListener() {
                @Override
                public void OnWeightSelected(int weight) {
                    String mWeight = String.format(Locale.getDefault(), "%dkg", weight);
                    binding.weightTv.setText(mWeight);
                }
            });
            DialogUtil.showDialog(mContext, picker);
        });

    }
}