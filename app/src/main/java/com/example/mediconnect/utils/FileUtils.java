package com.example.mediconnect.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static File getFileFromUri(Context context, Uri uri) {
        try {
            InputStream inputStream =
                    context.getContentResolver().openInputStream(uri);

            File file = new File(
                    context.getCacheDir(),
                    "doctor_" + System.currentTimeMillis() + ".jpg"
            );

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            outputStream.close();
            inputStream.close();
            return file;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
