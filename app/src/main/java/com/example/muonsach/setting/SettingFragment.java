package com.example.muonsach.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.muonsach.MainActivity;
import com.example.muonsach.R;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.FragmentSettingBinding;
import com.example.muonsach.login.LoginActivity;
import com.example.muonsach.notification.NotifiAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {
    FragmentSettingBinding binding;
    private FirebaseAuth firebaseAuth;
    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.relLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(SettingFragment.this.getContext(),"Logged Out!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SettingFragment.this.getContext(), MainActivity.class);
                startActivity(intent );
            }
        });

        return binding.getRoot();
    }
}