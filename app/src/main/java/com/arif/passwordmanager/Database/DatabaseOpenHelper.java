package com.arif.passwordmanager.Database;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.arif.passwordmanager.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import es.dmoral.toasty.Toasty;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public static final String DATABASE_NAME = "password.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    public void backup(String outFileName) throws IOException {


        //database path
        final String inFileName = context.getDatabasePath(DATABASE_NAME).toString();

        try {



            FileOutputStream fop = null;
            File file;


            file = new File(outFileName);
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            
            File dbFile = new File(inFileName);
//            if(!dbFile.exists())
//                dbFile.mkdir();
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
//            FileOutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fop.write(buffer, 0, length);
            }

            // Close the streams
            fop.flush();
            fop.close();
            fis.close();

            Toasty.success(context, context.getString(R.string.backup_completed_successfully), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toasty.error(context, R.string.unable_to_backup_database_retry, Toast.LENGTH_SHORT).show();
            Log.d("error",e.toString());
        }
    }


    public void importDB(String inFileName) {

        final String outFileName = context.getDatabasePath(DATABASE_NAME).toString();


        try {

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toasty.success(context, "Database Import Completed").show();

        } catch (Exception e) {
            Toasty.error(context, "Unable to import database. Retry").show();
            Log.d("Error", e.toString());
        }
    }

}
