package sg.edu.np.mad.mad_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewHorizontal;
    private RecyclerView recyclerViewVertical;

    private MainSportAdapter mainSportAdapter;
    private MainActivityAdapter mainActivityAdapter;

    List<Sport> sportList = new ArrayList<>();
    List<Activity> activityList = new ArrayList<>();
    List<Participant> participantList = new ArrayList<>();

    DatabaseReference sportDatabaseReference = FirebaseDatabase.getInstance().getReference("SportInfo");
    DatabaseReference activityDatabaseReference = FirebaseDatabase.getInstance().getReference("ActivityInfo");
    DatabaseReference participantDatabaseReference = FirebaseDatabase.getInstance().getReference("ParticipantInfo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewHorizontal = findViewById(R.id.sportsView);
        recyclerViewVertical = findViewById(R.id.activitiesView);

        // Set layout manager for horizontal RecyclerView
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHorizontal.setLayoutManager(horizontalLayoutManager);

        // Set layout manager for vertical RecyclerView
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this);
        recyclerViewVertical.setLayoutManager(verticalLayoutManager);

        // Create adapters for both RecyclerViews
        mainSportAdapter = new MainSportAdapter(sportList);
        mainActivityAdapter = new MainActivityAdapter(activityList);

        sportDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Sport tempSport = dataSnapshot.getValue(Sport.class);
                    sportList.add(tempSport);
                }
                mainSportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        activityDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activityList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Activity tempActivity = dataSnapshot.getValue(Activity.class);
                    if(tempActivity.getCurrentPax()<tempActivity.getPax()) activityList.add(tempActivity);
                }
                mainActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        participantDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participantList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Participant tempParticipant = dataSnapshot.getValue(Participant.class);
                    participantList.add(tempParticipant);
                }
                mainActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set adapters for both RecyclerViews
        recyclerViewHorizontal.setAdapter(mainSportAdapter);
        recyclerViewVertical.setAdapter(mainActivityAdapter);
    }
}