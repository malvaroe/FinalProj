package com.example.finalproj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsFragment extends Fragment {
    SearchData searchData = MainActivity.getSearchData();
    JSONArray jsonArray = searchData.businessArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_results, container, false);
        ArrayList<Result> arrayOfResults = new ArrayList<Result>();
        ResultsAdapter adapter = new ResultsAdapter(getContext(), arrayOfResults);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        for(int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject currentResult = jsonArray.getJSONObject(i);
                JSONObject currentLocation = currentResult.getJSONObject("location");
                String address2 = currentLocation.getString("city") + ", " + currentLocation.getString("state") + " " + currentLocation.getString("zip_code");
                Result result = new Result(currentResult.getString("name"), currentResult.getString("display_phone"), currentLocation.getString("address1"), address2);
                adapter.add(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
