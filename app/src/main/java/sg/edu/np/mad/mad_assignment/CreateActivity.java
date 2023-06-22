package sg.edu.np.mad.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDateTime;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {
    private TextView createText;
    private TextView activitySport;
    private Spinner sportSpinner;
    private TextView activityDesc;
    private EditText descriptionEditText;
    private TextView activityArea;
    private Spinner areaSpinner;
    private TextView activityLocation;
    private EditText locationEditText;
    private TextView activityDate;
    private Button dateButton;
    private TextView dateTextView;
    private TextView activityTime;
    private Spinner timeSpinner;
    private TextView activitySkillLevel;
    private Spinner skillLevelSpinner;
    private TextView activityAgeGroup;
    private Spinner ageGroupSpinner;
    private TextView activityGender;
    private Spinner genderSpinner;
    private TextView activityPax;
    private Spinner paxSpinner;
    private Button createActivityButton;

    private String userId;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ActivityInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        createText = findViewById(R.id.createText);
        activitySport = findViewById(R.id.activitySport);
        sportSpinner = findViewById(R.id.sportSpinner);
        activityDesc = findViewById(R.id.activityDesc);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        activityArea = findViewById(R.id.activityArea);
        areaSpinner = findViewById(R.id.areaSpinner);
        activityLocation = findViewById(R.id.activityLocation);
        locationEditText = findViewById(R.id.locationEditText);
        activityDate = findViewById(R.id.activityDate);
        dateButton = findViewById(R.id.dateButton);
        dateTextView = findViewById(R.id.dateTextView);
        activityTime = findViewById(R.id.activityTime);
        timeSpinner = findViewById(R.id.timeSpinner);
        activitySkillLevel = findViewById(R.id.activitySkillLevel);
        skillLevelSpinner = findViewById(R.id.skillLevelSpinner);
        activityAgeGroup = findViewById(R.id.activityAgeGroup);
        ageGroupSpinner = findViewById(R.id.ageGroupSpinner);
        activityGender = findViewById(R.id.activityGender);
        genderSpinner = findViewById(R.id.genderSpinner);
        activityPax = findViewById(R.id.activityPax);
        paxSpinner = findViewById(R.id.paxSpinner);
        createActivityButton = findViewById(R.id.createActivityBtn);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        createActivityButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (validateFields()) {
                   int sportId;
                   if(currentUser != null) {
                       userId = currentUser.getUid();
                   }
                   String sport = sportSpinner.getSelectedItem().toString();
                   if(sport.equals("Badminton")) sportId = 1;
                   else if(sport.equals("Netball")) sportId = 2;
                   else if(sport.equals("Soccer")) sportId = 3;
                   else if(sport.equals("Tennis")) sportId = 4;
                   else sportId = 5;
                   String description = descriptionEditText.getText().toString();
                   String area = areaSpinner.getSelectedItem().toString();
                   String location = locationEditText.getText().toString();
                   String date = dateTextView.getText().toString();
                   String time = timeSpinner.getSelectedItem().toString();
                   String skillLevel = skillLevelSpinner.getSelectedItem().toString();
                   String ageGroup = ageGroupSpinner.getSelectedItem().toString();
                   String gender = genderSpinner.getSelectedItem().toString();
                   int pax = Integer.parseInt(paxSpinner.getSelectedItem().toString());
                   String key = databaseReference.push().getKey();
                   Activity newActivity = new Activity(key, sportId, description, area, location, date, time, skillLevel, ageGroup, gender, pax, userId);
                   databaseReference.child(key).setValue(newActivity);
                   clearFields();
                   Toast.makeText(getApplicationContext(), "Activity Created", Toast.LENGTH_SHORT).show();
               }
           }
        });
    }

    private void clearFields() {
        descriptionEditText.setText("");
        locationEditText.setText("");
        dateTextView.setText("");
        sportSpinner.setSelection(0);
        skillLevelSpinner.setSelection(0);
        ageGroupSpinner.setSelection(0);
        genderSpinner.setSelection(0);
        paxSpinner.setSelection(0);
        areaSpinner.setSelection(0);
        timeSpinner.setSelection(0);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateTextView.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private boolean validateFields() {
        // Validate each field and display appropriate error messages if any field is empty
        if (descriptionEditText.getText().toString().isEmpty()) {
            descriptionEditText.setError("Please enter a description");
            return false;
        }

        if (locationEditText.getText().toString().isEmpty()) {
            locationEditText.setError("Please enter a location");
            return false;
        }

        String date = dateTextView.getText().toString();
        if (date.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select a date", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate skill level
        String skillLevel = skillLevelSpinner.getSelectedItem().toString();
        if (skillLevel.isEmpty() || skillLevel.equals("Select Skill Level")) {
            Toast.makeText(getApplicationContext(), "Please select a skill level", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate age group
        String ageGroup = ageGroupSpinner.getSelectedItem().toString();
        if (ageGroup.isEmpty() || ageGroup.equals("Select Age Group")) {
            Toast.makeText(getApplicationContext(), "Please select an age group", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate gender
        String gender = genderSpinner.getSelectedItem().toString();
        if (gender.isEmpty() || gender.equals("Select Gender")) {
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate pax
        String paxString = paxSpinner.getSelectedItem().toString();
        if (paxString.isEmpty() || paxString.equals("Select Pax")) {
            Toast.makeText(getApplicationContext(), "Please select the number of participants", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int pax = Integer.parseInt(paxString);
            if (pax <= 0) {
                Toast.makeText(getApplicationContext(), "Please select a valid number of participants", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid number of participants", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // All fields are valid
    }
}