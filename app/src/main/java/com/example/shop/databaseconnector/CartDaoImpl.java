package com.example.shop.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shop.checkout.CartContract;

public class CartDaoImpl implements CartDao {
  /** The singleton itemDao. */
  private static CartDaoImpl itemDao;

  /** DBHandler object that manages the database. */
  private DBHandler dbHandler;

  /**
   * Construct this CartDaoImpl.
   *
   * @param activity the activity that called this object.
   */
  private CartDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
  }

  /**
   * Get the singleton instance of this CartDaoImpl.
   *
   * @param activity the activity that called this object.
   * @return the singleton instance of this CartDaoImpl.
   */
  public static CartDaoImpl getInstance(Activity activity) {
    if (itemDao == null) {
      itemDao = new CartDaoImpl(activity);
    }
    return itemDao;
  }

  /**
   * Add an item to the shopping cart/database.
   *
   * @param name the name of the item
   * @param price the price of the item
   * @param amount the amount of the item
   * @param image the image source of the item
   */
  @Override
  public void addItem(String name, double price, int amount, int image) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    ContentValues cv = new ContentValues();

    cv.put(CartContract.CartEntry.COLUMN_NAME, name);
    cv.put(CartContract.CartEntry.COLUMN_PRICE, price);
    cv.put(CartContract.CartEntry.COLUMN_AMOUNT, amount);
    cv.put(CartContract.CartEntry.COLUMN_IMAGE, image);

    String selection = CartContract.CartEntry.COLUMN_NAME + " = " + "'" + name + "'";
    int rows = database.update(CartContract.CartEntry.TABLE_NAME, cv, selection, null);
    if (rows == 0) {
      database.insert(CartContract.CartEntry.TABLE_NAME, null, cv);
    }
  }

  /**
   * Increase the amount of the item in the shopping cart by one.
   *
   * @param id the id of the item
   */
  @Override
  public void increaseItem(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    int amount = getAmountInCheckout(id);
    amount++;

    ContentValues cv = new ContentValues();

    cv.put(CartContract.CartEntry.COLUMN_AMOUNT, amount);

    String selection = CartContract.CartEntry._ID + " = " + id;
    database.update(CartContract.CartEntry.TABLE_NAME, cv, selection, null);
  }

  /**
   * Decrease the amount of the item in the shopping cart by one.
   *
   * @param id the id of the item
   */
  @Override
  public void decreaseItem(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    int amount = getAmountInCheckout(id);
    if (amount > 1) {
      amount--;
    }

    ContentValues cv = new ContentValues();

    cv.put(CartContract.CartEntry.COLUMN_AMOUNT, amount);

    String selection = CartContract.CartEntry._ID + " = " + id;
    database.update(CartContract.CartEntry.TABLE_NAME, cv, selection, null);
  }

  /**
   * Delete the item from the shopping cart by its id.
   *
   * @param id the id of the item
   */
  @Override
  public void deleteItem(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    database.delete(
        CartContract.CartEntry.TABLE_NAME, CartContract.CartEntry._ID + " = " + id, null);
  }

  /**
   * Get the amount of the selected item in the shopping cart by its name.
   *
   * @param name the name of the selected product
   * @return the amount of the selected product in the shopping cart
   */
  @Override
  public int getAmountInMain(String name) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    int amount = 0;
    String query =
        " Select "
            + CartContract.CartEntry.COLUMN_AMOUNT
            + " FROM "
            + CartContract.CartEntry.TABLE_NAME
            + " WHERE "
            + CartContract.CartEntry.COLUMN_NAME
            + " = "
            + "'"
            + name
            + "'";

    Cursor cursor = database.rawQuery(query, null);

    if (cursor.moveToFirst()) {
      amount = cursor.getInt(0);
      cursor.close();
    }
    return amount;
  }

  /**
   * Get the amount of the selected item in the shopping cart by its id.
   *
   * @param id the id of the selected item in the database
   * @return the amount of the selected item in the shopping cart
   */
  @Override
  public int getAmountInCheckout(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    int amount = 0;
    String query =
        " Select "
            + CartContract.CartEntry.COLUMN_AMOUNT
            + " FROM "
            + CartContract.CartEntry.TABLE_NAME
            + " WHERE "
            + CartContract.CartEntry._ID
            + " = "
            + id;

    Cursor cursor = database.rawQuery(query, null);

    if (cursor.moveToFirst()) {
      amount = cursor.getInt(0);
      cursor.close();
    }
    return amount;
  }

  /**
   * Calculate the cart subtotal.
   *
   * @return the cart subtotal
   */
  @Override
  public double calculateSubtotal() {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    double sum = 0;
    Cursor cursor =
        database.query(
            CartContract.CartEntry.TABLE_NAME,
            new String[] {
              CartContract.CartEntry._ID,
              CartContract.CartEntry.COLUMN_PRICE,
              CartContract.CartEntry.COLUMN_AMOUNT
            },
            null,
            null,
            null,
            null,
            null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      double product = cursor.getDouble(1) * cursor.getInt(2);
      sum += product;
      cursor.moveToNext();
    }
    cursor.close();
    return sum;
  }

  /**
   * Get all items in the database.
   *
   * @return the cursor pointing to the data we want
   */
  @Override
  public Cursor getAllCartItems() {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    return database.query(
        CartContract.CartEntry.TABLE_NAME,
        null,
        null,
        null,
        null,
        null,
        CartContract.CartEntry.COLUMN_TIMESTAMP + " DESC");
  }
}
