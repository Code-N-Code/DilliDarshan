package com.codencode.dillidarshan;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codencode.dillidarshan.MyAdapter.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    RecyclerView recyclerView;

    FirebaseDatabase db;
    ArrayList<DataPacket> mlist;
    RecyclerAdapter adapter;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar mToolbar;
    ConnectivityManager manager;
    HashMap<String , Boolean> mp = new HashMap<>();
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.my_toolbar);
        button = findViewById(R.id.refresh_button_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromFirebase(getSelectedItem());
            }
        });

        mp.put("parks" , false);
        mp.put("monuments" , false);
        mp.put("malls" , false);
        setSupportActionBar(mToolbar);
        setNavigationView();
        setRecyclerView();
        setFireBase();
    }

    private String getSelectedItem()
    {
        Menu menu = navigationView.getMenu().getItem(0).getSubMenu();
        for(int i=0;i<menu.size();i++)
            if(menu.getItem(i).isChecked()){
                if(menu.getItem(i).getItemId() == R.id.malls_id)
                {
                    return "malls";
                }
                else if(menu.getItem(i).getItemId() == R.id.parks_id)
                {
                    return "parks";
                }
                else if(menu.getItem(i).getItemId() == R.id.monuments_id)
                {
                    return "monuments";
                }
            }
        return "parks";
    }


    private void setNavigationView()
    {
        navigationView = findViewById(R.id.navigation_view_id);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.malls_id)
                    getDataFromFirebase("malls");
                else if(item.getItemId() == R.id.monuments_id)
                    getDataFromFirebase("monuments");
                else if(item.getItemId() == R.id.parks_id)
                    getDataFromFirebase("parks");
                else if(item.getItemId() == R.id.share_id || item.getItemId() == R.id.rate_us_id || item.getItemId() == R.id.contact_us_id)
                {
                    Intent i;
                    i = new Intent(MainActivity.this , Monitor.class);
                    i.putExtra("uid" , "01001");
                    startActivity(i);
                }
                closeDrawer();
                return true;
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout_id);
        drawerToggle = new ActionBarDrawerToggle(this , drawerLayout , mToolbar, R.string.open , R.string.closed);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
    

    private void setRecyclerView()
    {
        recyclerView = findViewById(R.id.recycler_view_id);
        mlist = new ArrayList<>();

        adapter = new RecyclerAdapter(MainActivity.this , mlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void setFireBase()
    {
        db = FirebaseDatabase.getInstance();
        getDataFromFirebase("parks");
    }

    private void getDataFromFirebase(String src)
    {
        manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(src != null && mp.get(src) == true)
            ;
        else if(info == null || info.isConnected() == false) {
            handleNetworkError();
            return;
        }
        mp.put(src , true);
        button.setVisibility(View.GONE);
        drawerLayout.closeDrawer(GravityCompat.START);
        DatabaseReference ref = db.getReference(src);
        final ArrayList<DataPacket> packets = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    DataPacket dp = ds.getValue(DataPacket.class);
                    packets.add(dp);
                }
                if(packets.isEmpty() == false)
                {
                    mlist.clear();
                    for(int i=0;i<packets.size();i++)
                        mlist.add(packets.get(i));
                    packets.clear();
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(MainActivity.this, "Data Could not be loaded , try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something went wrong , try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleNetworkError()
    {
        Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        mlist.clear();
        adapter.notifyDataSetChanged();
        button.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            closeDrawer();
        }
        else
        {
            super.onBackPressed();
        }
    }
    void closeDrawer()
    {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}