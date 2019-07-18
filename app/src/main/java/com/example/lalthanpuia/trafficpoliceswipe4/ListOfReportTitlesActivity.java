package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.google.firebase.database.core.Repo;

public class ListOfReportTitlesActivity extends AppCompatActivity {


    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_report_titles);

        final String[] listOfTitles= {"No parking","Minor Accident","Motor Chhia"};
        listView = findViewById(R.id.listViewRecyclerView);

     //   listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false));

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listOfTitles );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ReportSubmitActivity.reportTitle = listOfTitles[position];
                startActivity(new Intent(getApplicationContext(), ReportSubmitActivity.class));
            }
        });


    }
}
