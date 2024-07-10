package com.example.secureguard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.secureguard.Database.RoomDB;
import com.example.secureguard.Model.Key;
import com.example.secureguard.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolsFragment extends Fragment {

    private Button btnSaveKey, btnEnableLock, btnEnableDisableMessage, btnDeleteAllMessage;
    private EditText txtKey;
    private TextView txtWarning;

    RoomDB database;

    public ToolsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        database = RoomDB.getInstance(getActivity());
        txtKey = view.findViewById(R.id.txtKey);
        txtWarning = view.findViewById(R.id.txtWarning);
        btnSaveKey = view.findViewById(R.id.btnSaveKey);
        btnDeleteAllMessage = view.findViewById(R.id.btnDeleteAllMessage);
        btnEnableDisableMessage = view.findViewById(R.id.btnEnableDisableMessage);
        btnEnableLock = view.findViewById(R.id.btnEnableLock);

        btnSaveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyText = txtKey.getText().toString();
                if (isValidPassword(keyText)) {
                    saveKey(keyText);
                    txtWarning.setVisibility(View.GONE);
                    txtKey.getText().clear();
                    Toast.makeText(getContext(), "Encryption key changed.", Toast.LENGTH_SHORT).show();
                } else {
                    txtWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        btnEnableDisableMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                boolean status;
                if (key.getMessageBackup()) {
                    status = false;
                    message = "Message backup disabled.";
                    dialogMessage = "Are you sure want to disable (Message history creation) ? \n" +
                            "No message history will be made further.";
                    dialogTitle = "Disable";

                } else {
                    status = true;
                    message = "Message backup enabled.";
                    dialogMessage = "Are you sure want to enable (Message history creation) ? \n" +
                            "By enabling you will have history of all message you encrypt.";
                    dialogTitle = "Enable";
                }

                new AlertDialog.Builder(getContext())
                        .setTitle(dialogTitle + " Confirmation?")
                        .setMessage(dialogMessage)
                        .setIcon(status ? R.drawable.ic_disable_enable : R.drawable.ic_disable_enable)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.keyDao().enableDisable(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        btnDeleteAllMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.mainDao().deleteAllMessage();
                                Toast.makeText(getContext(), "All message deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        btnEnableLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message, dialogMessage, dialogTitle;
                List<Key> keyList = database.keyDao().getAllKey();
                Key key = keyList.get(0);
                boolean status;
                if (key.getSecurityKey()) {
                    status = false;
                    message = "Screen lock disabled.";
                    dialogMessage = "Are you sure want to disable (Screen Lock) ?";
                    dialogTitle = "Disable";

                } else {
                    status = true;
                    message = "Screen lock enabled.";
                    dialogMessage = "Are you sure want to enable (Screen Lock) ? \n" +
                            "By enabling you have to use screen lock to open. ";
                    dialogTitle = "Enabled";
                }


                new AlertDialog.Builder(getContext())
                        .setTitle("Lock !!")
                        .setMessage(dialogMessage)
                        .setIcon(R.drawable.ic_disable_enable)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.keyDao().enableDisableSecurity(key.getID(), status);
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        return view;
    }

    public boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void saveKey(String keyToSave) {
        List<Key> keyList = database.keyDao().getAllKey();
        Key myKey = keyList.get(0);
        database.keyDao().changeKey(myKey.getID(), keyToSave);
    }

}