package com.xelits.filepicker;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.xelits.filepicker.capacitorfilepicker.R;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

@CapacitorPlugin()
public class FilePicker extends Plugin {

    @PluginMethod()
    public void showFilePicker(PluginCall call) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        try {
            if (call.getArray("fileTypes").toString().contains("iv") || call.getArray("fileTypes").toString().contains("i") || call.getArray("fileTypes").toString().contains("v")) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"video/*", "image/*"});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(call, intent, "filePickerResult");
    }

    @ActivityCallback
    private void filePickerResult(PluginCall call, Instrumentation.ActivityResult result) {

        switch (result.getResultCode()) {
            case Activity.RESULT_OK:
                if (result.getResultData() != null && result.getResultData().getData() != null) {
                    Intent data = result.getResultData();

                    String mimeType = getContext().getContentResolver().getType(data.getData());
                    String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

                    Cursor c = getContext().getContentResolver().query(data.getData(), null, null, null, null);
                    c.moveToFirst();
                    String name = c.getString(c.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    long size = c.getLong(c.getColumnIndex(OpenableColumns.SIZE));

                    JSObject ret = new JSObject();
                    try {
                        String path = copyFileToInternalStorage(data.getData(), getContext().getString(R.string.app_name));
                        path = path.startsWith("file://") ? path : "file://" + path;
                        ret.put("uri", path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ret.put("uri", "");
                    }
                    ret.put("name", name);
                    ret.put("mimeType", mimeType);
                    ret.put("extension", extension);
                    ret.put("size", size);
                    call.resolve(ret);
                }
                break;
            case Activity.RESULT_CANCELED:
                call.reject("File picking was cancelled.");
                break;
            default:
                call.reject("An unknown error occurred.");
                break;
        }
    }

    private String copyFileToInternalStorage(Uri uri, String newDirName) {
        Uri returnUri = uri;

        Cursor returnCursor = getContext().getContentResolver().query(returnUri, new String[]{
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
        }, null, null, null);


        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));

        File output;
        if (!newDirName.equals("")) {
            File dir = new File(getContext().getFilesDir() + "/" + newDirName);
            if (!dir.exists()) {
                dir.mkdir();
            }
            output = new File(getContext().getFilesDir() + "/" + newDirName + "/" + name);
        } else {
            output = new File(getContext().getFilesDir() + "/" + name);
        }
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(output);
            int read = 0;
            int bufferSize = 1024;
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return output.getPath();
    }
}
