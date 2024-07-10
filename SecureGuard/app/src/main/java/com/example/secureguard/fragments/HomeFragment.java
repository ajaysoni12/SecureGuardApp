package com.example.secureguard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.secureguard.Database.RoomDB;
import com.example.secureguard.EncryptDecrypt;
import com.example.secureguard.Model.Key;
import com.example.secureguard.Model.Message;
import com.example.secureguard.R;
import com.example.secureguard.Utility.BDUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;

public class HomeFragment extends Fragment {

    private MaterialButton btnEncrypt, btnDecrypt;
    private TextInputEditText txtEncrypt, txtDecrypt;
    RoomDB database;
    String key;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        database = RoomDB.getInstance(getActivity());
        init(view);

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = txtEncrypt.getText().toString();
                if (!value.isEmpty()) {
                    key = database.keyDao().getAllKey().get(0).getKey();
                    String encryptedText = EncryptDecrypt.encrypt(value, key);
                    txtDecrypt.setText(encryptedText);

                    // save message backup
                    List<Key> keyList = database.keyDao().getAllKey();
                    boolean saveMessage = keyList.get(0).getMessageBackup();
                    if (saveMessage)
                        saveEncryptedMessage(encryptedText, value);
                    BDUtility.setClipboard(getContext(), txtDecrypt.getText().toString());
                    txtEncrypt.getText().clear();

                    Toast.makeText(getContext(), "Encrypted text copied to clipboard", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getContext(), "field empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String value = txtDecrypt.getText().toString();
                    if (!value.isEmpty()) {
                        key = database.keyDao().getAllKey().get(0).getKey();
                        String decryptedText = EncryptDecrypt.decrypt(value, key);
                        txtEncrypt.setText(decryptedText);
                        txtDecrypt.getText().clear();
                        BDUtility.setClipboard(getContext(), txtEncrypt.getText().toString());
                        Toast.makeText(getContext(), "Decrypted text copied to clipboard", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "field empty", Toast.LENGTH_SHORT).show();
                    }
                } catch (BadPaddingException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "key changed or invalid encrypted data", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "Invalid encrypted data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void saveEncryptedMessage(String eText, String pText) {
        Message message = new Message();
        message.setEncryptedText(eText);
        message.setPlanText(pText);
        message.setCreationTime(new Date());
        database.mainDao().saveItem(message);
    }

    private void init(View view) {
        btnDecrypt = view.findViewById(R.id.btnDecrypt);
        btnEncrypt = view.findViewById(R.id.btnEncrypt);
        txtEncrypt = view.findViewById(R.id.txtEncrypt);
        txtDecrypt = view.findViewById(R.id.txtDecrypt);
    }
}