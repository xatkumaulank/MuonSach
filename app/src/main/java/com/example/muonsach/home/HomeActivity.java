package com.example.muonsach.home;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityHomeBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.user.UpdateInfoUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private long backPressTime;
    private Toast toast;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        reference = FirebaseDatabase.getInstance().getReference().child("users");



        setUpViewPager();

        binding.bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        binding.viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_search:
                        binding.viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_avata:
                        binding.viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_bell:
                        binding.viewPager.setCurrentItem(3);
                        break;
                    case R.id.action_setting:
                        binding.viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });


    }





    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.bottomNavi.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavi.getMenu().findItem(R.id.action_search).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavi.getMenu().findItem(R.id.action_avata).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavi.getMenu().findItem(R.id.action_bell).setChecked(true);
                        break;
                    case 4:
                        binding.bottomNavi.getMenu().findItem(R.id.action_setting).setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressTime + 2000 > System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }else {
            toast = Toast.makeText(HomeActivity.this,"Press back to exit aplication!",Toast.LENGTH_LONG);
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}
