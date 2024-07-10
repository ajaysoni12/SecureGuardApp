package com.example.secureguard.Utility;

import android.content.Context;
import android.os.Build;

public class BDUtility {

    public static void setClipboard(Context context, String text) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clipData = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clipData);
        }
    }
}
