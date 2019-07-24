package com.codencode.dillidarshan;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.codencode.dillidarshan.MyAdapter.MyViewPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Monitor extends AppCompatActivity {
    ViewPager pager;
    MyViewPagerAdapter adapter;
    String uid , metro , busStop;
    ArrayList<String> mList = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        pager = findViewById(R.id.view_pager_id);
        adapter = new MyViewPagerAdapter(this ,mList);
        pager.setAdapter(adapter);

        uid = getIntent().getStringExtra("uid");
        metro = getIntent().getStringExtra("metro");
        busStop = getIntent().getStringExtra("busStop");
        setDisplayValues();
        getFirebaseData();
    }

    private void setDisplayValues()
    {
        TextView txt = findViewById(R.id.nearest_metro_id);
        txt.setText("Nearest Metro : " + metro);

        TextView bus = findViewById(R.id.nearest_busstop_id);
        bus.setText("Nearest BusStop : " + busStop);
    }
    private void getFirebaseData()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        String category_id = uid.substring(0 , 2);
        String item_number = uid.substring(2 , 5);
        ref = firebaseDatabase.getReference("image_url").child(category_id).child(item_number);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    mList.add(ds.getValue(String.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference descRef = firebaseDatabase.getReference("description").child(category_id).child(item_number);
        descRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView descriptionTxt = findViewById(R.id.description_id);
                descriptionTxt.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}