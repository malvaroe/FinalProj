package com.example.finalproj;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SearchFragment extends Fragment {
    EditText searchText, locText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        Button locButton = view.findViewById(R.id.locButton);
        searchText = view.findViewById(R.id.nameText);
        locText = view.findViewById(R.id.locText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.onSearchClick(searchText.getText().toString(), locText.getText().toString());
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultsFragment()).commit();
            }
        });
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.onLocClick(searchText.getText().toString());
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultsFragment()).commit();
            }
        });



        return view;
    }
}
