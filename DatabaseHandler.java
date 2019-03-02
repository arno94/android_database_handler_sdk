package com.arno.database_handler_library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.arno.database_handler_library.entity.BaseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler{

    private static final String TAG = "DatabaseHandler";

    private Context context;

    private String databaseName;
    private String pathToSaveDatabaseFile;
    private static final String databaseFileExtension = ".db";

    public DatabaseHandler(Context context, String databaseName, int databaseVersion, String pathToSaveDatabaseFile){
        super(context, databaseName, null, databaseVersion);
        this.context = context;
        this.databaseName = databaseName;
        this.pathToSaveDatabaseFile = pathToSaveDatabaseFile;
    }

    public void loadDatabase() throws IOException {
        File f = new File(pathToSaveDatabaseFile + databaseFileExtension);
        if(!f.exists()) f.mkdirs();
        OutputStream os = new FileOutputStream(pathToSaveDatabaseFile + databaseFileExtension);
        InputStream is = context.getAssets().open("sqlite/" + databaseName + databaseFileExtension);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.flush();
        os.close();
        Log.d(TAG, "Database loaded.");
    }

    public boolean isDatabaseExists(){
        return new File(pathToSaveDatabaseFile + databaseFileExtension).exists();
    }

    public void deleteDatabase() {
        File file = new File(pathToSaveDatabaseFile + databaseFileExtension);
        if(file.exists()) {
            file.delete();
            Log.d(TAG, "Database deleted.");
        }
    }

    public Cursor createQuery(List<String> params, String tableName){
        String query = "SELECT ";
        for(int i = 0;i < params.size(); i++){
            query += params.get(i);
            if(i+1 < params.size()) query += ", ";
        }
        query += " FROM " + tableName;
        Log.d(TAG,"Database query executed.");
        return getReadableDatabase().rawQuery(query, null);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(pathToSaveDatabaseFile, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
