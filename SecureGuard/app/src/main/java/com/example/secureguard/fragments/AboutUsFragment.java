package com.example.secureguard.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.secureguard.R;


public class AboutUsFragment extends Fragment {

    ImageView imgLinkedln, imgInstagram, imgGithub;
    TextView txtEmail, txtGithub, txtGFG, txtLeetcode, txtCodechef;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        imgGithub = view.findViewById(R.id.imgGithub);
        imgInstagram = view.findViewById(R.id.imgInstagram);
        imgLinkedln = view.findViewById(R.id.imgLinkedln);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtGFG = view.findViewById(R.id.txtGeeksForGeeks);
        txtLeetcode = view.findViewById(R.id.txtLeetCode);
        txtGithub = view.findViewById(R.id.txtGithub);
        txtCodechef = view.findViewById(R.id.txtCodechef);


        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "ritscsajaysoni@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "From Safe Guard");
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {

                }
            }
        });

        imgLinkedln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.linkedin.com/in/ajaysoni123/"));
                startActivity(intent);
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com"));
                startActivity(intent);
            }
        });

        imgGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/ajaysoni12"));
                startActivity(intent);
            }
        });

        txtCodechef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.codechef.com/users/ajay_soni123"));
                startActivity(intent);
            }
        });

        txtGFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.geeksforgeeks.org/user/ajay_soni123/"));
                startActivity(intent);
            }
        });

        txtLeetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://leetcode.com/u/ajay_soni123/"));
                startActivity(intent);
            }
        });
        txtGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/ajaysoni12"));
                startActivity(intent);
            }
        });


        return view;
    }
}