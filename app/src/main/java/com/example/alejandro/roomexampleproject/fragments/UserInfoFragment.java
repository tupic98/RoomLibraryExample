package com.example.alejandro.roomexampleproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.models.User;

public class UserInfoFragment extends Fragment{
    User user;
    TextView firstName, lastName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_info, container, false);

        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        return v;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
