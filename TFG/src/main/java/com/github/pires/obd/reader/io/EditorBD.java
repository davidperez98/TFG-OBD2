package com.github.pires.obd.reader.io;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EditorBD extends SQLiteOpenHelper {
    private static final String COCHES_TABLE_CREATE = "CREATE TABLE coches(matricula INTEGER PRIMARY KEY, marcaModelo TEXT, km INTEGER, anio INTEGER)";
    private static final String PIEZAS_TABLE_CREATE = "CREATE TABLE piezas(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, cost INTEGER, km INTEGER, kmsiguiente INTEGER, anio INTEGER, aniosiguiente INTEGER, FOREIGN KEY(matricula) REFERENCES coches(matricula))";
    private static final String DB_NAME = "cochespiezas.sqlite";
    private static final int DB_VERSION = 1;

    public EditorBD(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COCHES_TABLE_CREATE);
        db.execSQL(PIEZAS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean hayCoches(){
        SQLiteDatabase db = getReadableDatabase();
        if(DatabaseUtils.queryNumEntries(db, "coches") > 0){
            return true;
        }

        return false;
    }
}
