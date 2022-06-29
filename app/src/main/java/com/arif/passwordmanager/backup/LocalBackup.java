
package com.arif.passwordmanager.backup;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getDataDirectory;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.arif.passwordmanager.Database.DatabaseOpenHelper;
import com.arif.passwordmanager.Fragments.SettingFragment;
import com.arif.passwordmanager.R;

import java.io.File;
import java.io.IOException;


public class LocalBackup {

    private Context activity;

    public LocalBackup(Context activity) {
        this.activity = activity;
    }

    //ask to the user a name for the backup and perform it. The backup will be saved to a custom folder.
    public void performBackup(final DatabaseOpenHelper db) {

        File folder = new File(activity.getFilesDir() + "/Backup");
        String outFileName = activity.getFilesDir() + "/Backup/";

        boolean success = true;
        if (!folder.exists())
            success = folder.mkdirs();
        if (success) {

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final EditText input = new EditText(activity);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setMessage(R.string.enter_local_database_backup_name)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            String mText = input.getText().toString();
                            String out = outFileName + mText;
                            try {
                                db.backup(out);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Perform Your Task Here--When No is pressed
                            dialog.cancel();
                        }
                    }).show();

        } else
            Toast.makeText(activity, R.string.unable_to_create_directory_retry, Toast.LENGTH_SHORT).show();
    }

    //    ask to the user what backup to restore
    public void performRestore(final DatabaseOpenHelper db) {


        File folder = new File(activity.getFilesDir() + "/Backup/");

        if (folder.exists()) {

            final File[] files = folder.listFiles();

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.select_dialog_item);
            for (File file : files)
                arrayAdapter.add(file.getName());

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
            builderSingle.setTitle("Database Restore:");
            builderSingle.setNegativeButton(
                    "Cancel",
                    (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(
                    arrayAdapter,
                    (dialog, which) -> {
                        try {
                            db.importDB(files[which].getPath());
                        } catch (Exception e) {
                            Toast.makeText(activity, "Unable to restore. Retry", Toast.LENGTH_SHORT).show();
                        }
                    });
            builderSingle.show();
        } else
            Toast.makeText(activity, R.string.backup_folder_not_present, Toast.LENGTH_SHORT).show();
    }

}
