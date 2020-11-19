package com.example.shop.databaseconnector;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shop.checkout.CartContract.CartEntry;
import com.example.shop.main.ProductContract.ProductEntry;

public class DBHandler extends SQLiteOpenHelper {
  /** The singleton instance of this DBHandler. */
  private static DBHandler dbHandler;

  public static final String DATABASE_NAME = "shop.db";
  public static final int DATABASE_VERSION = 1;

  public DBHandler(@Nullable Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Getter for this DBHandler instance.
   *
   * @return the instance of this DBHandler.
   */
  public static DBHandler getInstance(Activity activity) {
    if (dbHandler == null) {
      dbHandler = new DBHandler(activity);
    }
    return dbHandler;
  }

  /**
   * Creates the tables and the initial population of the tables.
   *
   * @param db the database.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_CARTLIST_TABLE =
        "CREATE TABLE "
            + CartEntry.TABLE_NAME
            + " ("
            + CartEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CartEntry.COLUMN_NAME
            + " TEXT NOT NULL, "
            + CartEntry.COLUMN_PRICE
            + " REAL NOT NULL, "
            + CartEntry.COLUMN_AMOUNT
            + " INTEGER NOT NULL, "
            + CartEntry.COLUMN_IMAGE
            + " INTEGER NOT NULL, "
            + CartEntry.COLUMN_TIMESTAMP
            + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    final String SQL_CREATE_PRODUCTLIST_TABLE =
        "CREATE TABLE "
            + ProductEntry.TABLE_NAME
            + " ("
            + ProductEntry._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductEntry.COLUMN_NAME
            + " TEXT NOT NULL, "
            + ProductEntry.COLUMN_PRICE
            + " REAL NOT NULL, "
            + ProductEntry.COLUMN_IMAGE
            + " INTEGER NOT NULL, "
            + ProductEntry.COLUMN_TIMESTAMP
            + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
            + ");";

    db.execSQL(SQL_CREATE_CARTLIST_TABLE);
    db.execSQL(SQL_CREATE_PRODUCTLIST_TABLE);
  }

  /**
   * Drop tables, add tables, or do anything else the database needs to upgrade to the new schema
   * version.
   *
   * @param db the database.
   * @param oldVersion the old database version.
   * @param newVersion the new database version.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + CartEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
    onCreate(db);
  }
}
