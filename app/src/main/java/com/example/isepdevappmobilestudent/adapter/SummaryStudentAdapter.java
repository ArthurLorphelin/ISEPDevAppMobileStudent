package com.example.isepdevappmobilestudent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Component;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentScore;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class SummaryStudentAdapter extends ArrayAdapter<ComponentScore> {
    private ArrayList<ComponentScore> componentScores;
    private DatabaseManager databaseManager;

    public SummaryStudentAdapter(Context context, ArrayList<ComponentScore> componentScores) {
        super(context, 0, componentScores);
        this.componentScores = componentScores;
        this.databaseManager = new DatabaseManager(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @SuppressLint("SetTextI18n")
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_two_items, parent, false);
        }

        // We display in the first part : the name of the Component
        TextView textViewFirstItem = convertView.findViewById(R.id.list_view_two_items_first_item);
        TextView textViewSecondItem = convertView.findViewById(R.id.list_view_two_items_second_item);
        ComponentScore currentComponentScore = getItem(position);
        String componentName = "";
        if (currentComponentScore != null) {
            ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
            for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                if (allComponentsInDB.get(componentIndex).getId() == currentComponentScore.getComponentId()) {
                    componentName = allComponentsInDB.get(componentIndex).getName();
                }
            }
        }
        textViewFirstItem.setText(componentName);

        // We display the second part : the componentScore value
        assert currentComponentScore != null;
        String score = currentComponentScore.getScore() + "/20";
        textViewSecondItem.setText(score);

        return convertView;
    }
}
