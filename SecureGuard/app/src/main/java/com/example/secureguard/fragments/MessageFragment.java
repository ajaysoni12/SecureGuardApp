package com.example.secureguard.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.secureguard.Adapter.MessageAdapter;
import com.example.secureguard.Database.RoomDB;
import com.example.secureguard.Model.Message;
import com.example.secureguard.R;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    RecyclerView rvMessages;
    MessageAdapter messageAdapter;
    RelativeLayout relativeLayout;
    RoomDB database;

    List<Message> messageList = new ArrayList<>();
    List<Message> tempList = null;

    TextView txtSearch;


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        rvMessages = view.findViewById(R.id.rvMessage);
        relativeLayout = view.findViewById(R.id.layout_message);
        txtSearch = view.findViewById(R.id.txtSearch);
        database = RoomDB.getInstance(getActivity());
        messageList = database.mainDao().getAllMessage();

        rvMessages.setHasFixedSize(true);
        rvMessages.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        messageAdapter = new MessageAdapter(getContext(), messageList, database);
        rvMessages.setAdapter(messageAdapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tempList = new ArrayList<>();
                for (int data = 0; data < messageList.size(); data++) {
                    if (messageList.get(data).getPlanText().toLowerCase().startsWith(txtSearch.getText().toString().toLowerCase())) {
                        tempList.add(messageList.get(data));
                    }
                }

                rvMessages.setHasFixedSize(true);
                rvMessages.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
                messageAdapter = new MessageAdapter(getActivity(), tempList, database);
                rvMessages.setAdapter(messageAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}