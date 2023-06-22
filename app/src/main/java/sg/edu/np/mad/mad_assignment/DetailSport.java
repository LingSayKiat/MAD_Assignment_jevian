package sg.edu.np.mad.mad_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetailSport extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DetailSportAdapter detailSportAdapter;

    List<Activity> activityList = new ArrayList<>();
    List<Activity> filteredActivityList = new ArrayList<>();
    DatabaseReference activityDatabaseReference = FirebaseDatabase.getInstance().getReference("ActivityInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_sport);

        recyclerView = findViewById(R.id.detailSportActivityView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Sport sport = (Sport) intent.getSerializableExtra("sport");

        TextView sportText = findViewById(R.id.detailSportTitle);
        sportText.setText(sport.getName());

        activityDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activityList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Activity tempActivity = dataSnapshot.getValue(Activity.class);
                    if(tempActivity.getCurrentPax()<tempActivity.getPax() && tempActivity.getSportId() == sport.getId()) activityList.add(tempActivity);
                }
                detailSportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detailSportAdapter = new DetailSportAdapter(filteredActivityList);
        recyclerView.setAdapter(detailSportAdapter);
    }
}