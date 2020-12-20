package com.example.finalproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ResultsAdapter extends ArrayAdapter<Result> {
    public ResultsAdapter(@NonNull Context context, ArrayList<Result> results) {
        super(context, 0, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_layout, parent, false);
        }
        TextView nameText = (TextView) convertView.findViewById(R.id.nameText);
        TextView phoneNumberText = (TextView) convertView.findViewById(R.id.phoneNumberText);
        TextView addressText1 = (TextView) convertView.findViewById(R.id.addressText1);
        TextView addressText2 = (TextView) convertView.findViewById(R.id.addressText2);

        nameText.setText(result.name);
        phoneNumberText.setText(result.phone);
        addressText1.setText(result.address1);
        addressText2.setText(result.address2);

        return convertView;
    }
}
