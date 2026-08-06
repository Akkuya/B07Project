package com.example.s26g5.item_viewing;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.s26g5.Item;
import com.example.s26g5.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterDialogFragment extends DialogFragment {

    public interface OnFiltersAppliedListener {
        void onFiltersApplied(String category, String dynasty, String materials,
                              String culturalOrigin, String currentLocation,
                              String acquisitionMethod, String conditionReport);
    }

    private static final String ARG_ITEMS = "items";

    private List<Item> items;
    private OnFiltersAppliedListener listener;

    private Spinner categorySpinner;
    private Spinner dynastySpinner;
    private Spinner materialsSpinner;
    private Spinner culturalOriginSpinner;
    private Spinner currentLocationSpinner;
    private Spinner acquisitionMethodSpinner;
    private Spinner conditionReportSpinner;

    public static FilterDialogFragment newInstance(List<Item> items) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEMS, new ArrayList<>(items));
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnFiltersAppliedListener(OnFiltersAppliedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = (List<Item>) getArguments().getSerializable(ARG_ITEMS);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_filter, null);

        categorySpinner = view.findViewById(R.id.filter_category);
        dynastySpinner = view.findViewById(R.id.filter_dynasty);
        materialsSpinner = view.findViewById(R.id.filter_materials);
        culturalOriginSpinner = view.findViewById(R.id.filter_cultural_origin);
        currentLocationSpinner = view.findViewById(R.id.filter_current_location);
        acquisitionMethodSpinner = view.findViewById(R.id.filter_acquisition_method);
        conditionReportSpinner = view.findViewById(R.id.filter_condition_report);

        setSpinnerItems(categorySpinner, "All Categories");
        setSpinnerItems(dynastySpinner, "All Dynasties");
        setSpinnerItems(materialsSpinner, "All Materials");
        setSpinnerItems(culturalOriginSpinner, "All Origins");
        setSpinnerItems(currentLocationSpinner, "All Locations");
        setSpinnerItems(acquisitionMethodSpinner, "All Methods");
        setSpinnerItems(conditionReportSpinner, "All Conditions");

        Button clearButton = view.findViewById(R.id.button_clear_filters);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllToDefault();
            }
        });

        Button applyButton = view.findViewById(R.id.button_apply_filters);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onFiltersApplied(
                            selectedOrNull(categorySpinner),
                            selectedOrNull(dynastySpinner),
                            selectedOrNull(materialsSpinner),
                            selectedOrNull(culturalOriginSpinner),
                            selectedOrNull(currentLocationSpinner),
                            selectedOrNull(acquisitionMethodSpinner),
                            selectedOrNull(conditionReportSpinner));
                }
                dismiss();
            }
        });

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .create();
    }

    private void setSpinnerItems(Spinner spinner, String allLabel) {
        List<String> options = new ArrayList<>();
        options.add(allLabel);
        options.addAll(distinctValues(spinner));
        spinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, options));
    }

    private List<String> distinctValues(Spinner spinner) {
        Set<String> values = new HashSet<>();
        int id = spinner.getId();
        for (Item item : items) {
            String value;
            if (id == R.id.filter_category) {
                value = item.getCategory();
            } else if (id == R.id.filter_dynasty) {
                value = item.getDynasty();
            } else if (id == R.id.filter_materials) {
                value = item.getMaterials();
            } else if (id == R.id.filter_cultural_origin) {
                value = item.getCulturalOrigin();
            } else if (id == R.id.filter_current_location) {
                value = item.getCurrentLocation();
            } else if (id == R.id.filter_acquisition_method) {
                value = item.getAcquisitionMethod();
            } else {
                value = item.getConditionReport();
            }
            if (value != null && !value.isEmpty()) {
                values.add(value);
            }
        }
        return new ArrayList<>(values);
    }

    private void resetAllToDefault() {
        categorySpinner.setSelection(0);
        dynastySpinner.setSelection(0);
        materialsSpinner.setSelection(0);
        culturalOriginSpinner.setSelection(0);
        currentLocationSpinner.setSelection(0);
        acquisitionMethodSpinner.setSelection(0);
        conditionReportSpinner.setSelection(0);
    }

    private String selectedOrNull(Spinner spinner) {
        String selected = (String) spinner.getSelectedItem();
        if (selected != null && selected.startsWith("All ")) {
            return null;
        }
        return selected;
    }
}
