package com.example.secureguard.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.secureguard.Database.RoomDB;
import com.example.secureguard.Model.Message;
import com.example.secureguard.R;
import com.example.secureguard.Utility.BDUtility;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    List<Message> messageList;
    RoomDB database;

    public MessageAdapter(Context context, List<Message> messageList, RoomDB database) {
        this.context = context;
        this.messageList = messageList;
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        holder.txtPlainText.setText(messageList.get(pos).getPlanText());
        holder.txtEncryptedText.setText(messageList.get(pos).getEncryptedText());
        holder.txtDate.setText(messageList.get(pos).getCreationTime().toString());

        holder.btnCopyToClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BDUtility.setClipboard(context, messageList.get(pos).getEncryptedText());
                Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete!")
                        .setMessage("Are you sure?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.mainDao().delete(messageList.get(pos));
                                messageList.remove(messageList.get(pos));
                                notifyDataSetChanged();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, messageList.get(pos).getEncryptedText());
                intent.setType("text/plain");

                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPlainText, txtEncryptedText, txtDate;
        ImageButton btnShare, btnCopyToClipboard, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPlainText = itemView.findViewById(R.id.txtPlainText);
            txtEncryptedText = itemView.findViewById(R.id.txtEncryptedText);
            txtDate = itemView.findViewById(R.id.txtCreationTime);

            btnShare = itemView.findViewById(R.id.btnShare);
            btnCopyToClipboard = itemView.findViewById(R.id.btnCopyToClipboard);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
