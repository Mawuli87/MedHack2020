package com.messieyawo.medhack2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.messieyawo.medhack2020.Slots.EventFragment;
import com.messieyawo.medhack2020.drawerFragments.AvailableSlots;
import com.messieyawo.medhack2020.drawerFragments.BlankFragmentCOV19;
import com.messieyawo.medhack2020.drawerFragments.ChatWithUsFragment;
import com.messieyawo.medhack2020.fragments.DonateFragment;
import com.messieyawo.medhack2020.fragments.HomeFragment;
import com.messieyawo.medhack2020.fragments.MedHackAppointMent;
import com.messieyawo.medhack2020.fragments.MedHackHome;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Button submit_btn = findViewById(R.id.login_btn);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_music);
        TextView profileName =  header.findViewById(R.id.user_name);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_iq) {
                    fragment = new HomeFragment();
                }else if(id == R.id.nav_home){
                    fragment = new  HomeFragment();
                }else if(id == R.id.available_slots){
                    fragment = new EventFragment();
                }else if(id == R.id.profile){
                    //fragment = new AvailableSlots();
                    Intent newInt = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(newInt);
                }else if(id == R.id.chatUs){
                    fragment = new DonateFragment();
                }else if(id == R.id.cov19){
                    fragment = new DonateFragment();
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment);
                transaction.commit();

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

      //  set onclick listener on submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submitIntent = new Intent(MainActivity.this, LoginActivity.class);
              startActivity(submitIntent);
            }
        });
    }

    public void goToCommentsFragment(View view) {
        // fragment = new CommentsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container_wrapper, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
