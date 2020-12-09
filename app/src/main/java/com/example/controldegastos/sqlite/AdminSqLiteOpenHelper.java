package com.example.controldegastos.sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.navigation.ui.NavigationUI;

public class AdminSqLiteOpenHelper extends SQLiteOpenHelper{

    private static final String ABONOS_TABLE_CREATE = "CREATE TABLE abonos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, monto REAL, fecha TEXT, fechaCorte TEXT)";
    private static final String TRANSACCIONES_TABLE_CREATE = "CREATE TABLE transacciones(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, monto REAL,descripcion TEXT, fecha TEXT, fechaCorte TEXT)";
    private static final String DB_NAME = "controlGastos.sqlite";
    private static final int DB_VERSION = 1;

    public AdminSqLiteOpenHelper(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL(ABONOS_TABLE_CREATE);
        BaseDeDatos.execSQL(TRANSACCIONES_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int i, int i1) {
        BaseDeDatos.execSQL("drop table if exists transacciones");
    }
}
