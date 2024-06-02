package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.adapter.SummaryStudentAdapter;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentScore;
import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class OtherStudentDetails extends AppCompatActivity {
    public static ComponentScore COMPONENT_SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.other_student_details);

        // We display the Student Name at the top
        TextView textViewStudentDetails = findViewById(R.id.student_name_for_tutor_in_students_scores_list);
        String studentName = TeamDetails.STUDENT_NAME;
        textViewStudentDetails.setText(studentName);

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_team_details);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), TeamDetails.class);
                startActivity(intentPreviousPage);
            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_in_other_student_detail);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We get the Student id
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        Student currentStudent = new Student();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (studentName.contains(allStudentsInDB.get(studentIndex).getFirstName())
                    && studentName.contains(allStudentsInDB.get(studentIndex).getLastName())) {
                currentStudent = allStudentsInDB.get(studentIndex);
            }
        }

        // We display the student information
        TextView studentNameTextView = findViewById(R.id.student_name_in_other_student_details);
        studentNameTextView.setText(studentName);
        TextView studentNumberTextView = findViewById(R.id.student_number_in_other_student_details);
        studentNumberTextView.setText(String.valueOf(currentStudent.getStudentNumber()));
        TextView studentEmailTextView = findViewById(R.id.student_mail_in_other_student_details);
        studentEmailTextView.setText(currentStudent.getEmail());

        // We get all ComponentScores for this Student
        ArrayList<ComponentScore> allComponentScoresInDB = databaseManager.getAllComponentScores();
        ArrayList<ComponentScore> componentScoresForThisStudent = new ArrayList<>();
        for (int index = 0; index < allComponentScoresInDB.size(); index++) {
            if (allComponentScoresInDB.get(index).getStudentId() == currentStudent.getId()) {
                componentScoresForThisStudent.add(allComponentScoresInDB.get(index));
            }
        }

        // We display the Summary for the Student
        ListView listViewSummary = findViewById(R.id.summary_list_view_in_other_student_details);
        SummaryStudentAdapter summaryStudentAdapter = new SummaryStudentAdapter(getApplicationContext(), componentScoresForThisStudent);
        listViewSummary.setAdapter(summaryStudentAdapter);

        // We create the activity for a selected item
        listViewSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMPONENT_SCORE = (ComponentScore) parent.getItemAtPosition(position);
                Intent intentComponentScoreDetails = new Intent(getApplicationContext(), ComponentScoreDetails.class);
                startActivity(intentComponentScoreDetails);
            }
        });
    }
}