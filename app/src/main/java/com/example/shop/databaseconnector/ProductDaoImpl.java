package com.example.shop.databaseconnector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shop.main.ProductContract;
import com.example.shop.main.ProductItem;

public class ProductDaoImpl implements ProductDao {

  /** The singleton itemDao. */
  private static ProductDaoImpl itemDao;

  /** DBHandler object that manages the database. */
  private DBHandler dbHandler;

  /**
   * Construct this ProductDaoImpl.
   *
   * @param activity the activity that called this object.
   */
  private ProductDaoImpl(Activity activity) {
    this.dbHandler = DBHandler.getInstance(activity);
  }

  /**
   * Get the singleton instance of this ProductDaoImpl.
   *
   * @param activity the activity that called this object.
   * @return the singleton instance of this ProductDaoImpl.
   */
  public static ProductDaoImpl getInstance(Activity activity) {
    if (itemDao == null) {
      itemDao = new ProductDaoImpl(activity);
    }
    return itemDao;
  }

  /**
   * Add an item in the database.
   *
   * @param item the info details of the product being saved
   */
  @Override
  public void addItem(ProductItem item) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    ContentValues cv = new ContentValues();

    String price_txt = item.getPriceTag().substring(1);
    double price = Double.parseDouble(price_txt);

    cv.put(ProductContract.ProductEntry.COLUMN_NAME, item.getProductName());
    cv.put(ProductContract.ProductEntry.COLUMN_PRICE, price);
    cv.put(ProductContract.ProductEntry.COLUMN_IMAGE, item.getProductImageResource());

    String selection =
        ProductContract.ProductEntry.COLUMN_NAME + " = " + "'" + item.getProductName() + "'";
    int rows = database.update(ProductContract.ProductEntry.TABLE_NAME, cv, selection, null);
    if (rows == 0) {
      database.insert(ProductContract.ProductEntry.TABLE_NAME, null, cv);
    }
  }

  /**
   * Get the name of the selected item from the database by its corresponding id.
   *
   * @param id the id of the item in the database
   * @return the name of the selected item
   */
  @Override
  public String getName(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    String name = "";
    String query =
        " Select "
            + ProductContract.ProductEntry.COLUMN_NAME
            + " FROM "
            + ProductContract.ProductEntry.TABLE_NAME
            + " WHERE "
            + ProductContract.ProductEntry._ID
            + " = "
            + id;

    Cursor cursor = database.rawQuery(query, null);

    if (cursor.moveToFirst()) {
      name = cursor.getString(0);
      cursor.close();
    }
    return name;
  }

  /**
   * Get the price of the selected item from the database by its corresponding id.
   *
   * @param id the id of the item in the database
   * @return the price of the selected item
   */
  @Override
  public double getPrice(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    double price = 0;
    String query =
        " Select "
            + ProductContract.ProductEntry.COLUMN_PRICE
            + " FROM "
            + ProductContract.ProductEntry.TABLE_NAME
            + " WHERE "
            + ProductContract.ProductEntry._ID
            + " = "
            + id;

    Cursor cursor = database.rawQuery(query, null);

    if (cursor.moveToFirst()) {
      price = cursor.getDouble(0);
      cursor.close();
    }
    return price;
  }

  /**
   * Get the image source of the selected item from the database by its corresponding id.
   *
   * @param id the id of the item in the database
   * @return the image source of the selected item
   */
  @Override
  public int getImageSource(long id) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    int image = 0;
    String query =
        " Select "
            + ProductContract.ProductEntry.COLUMN_IMAGE
            + " FROM "
            + ProductContract.ProductEntry.TABLE_NAME
            + " WHERE "
            + ProductContract.ProductEntry._ID
            + " = "
            + id;

    Cursor cursor = database.rawQuery(query, null);

    if (cursor.moveToFirst()) {
      image = cursor.getInt(0);
      cursor.close();
    }
    return image;
  }

  /**
   * Get all items in the database.
   *
   * @return the cursor pointing to the data we want
   */
  @Override
  public Cursor getAllProductItems() {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    return database.query(
        ProductContract.ProductEntry.TABLE_NAME,
        null,
        null,
        null,
        null,
        null,
        ProductContract.ProductEntry.COLUMN_TIMESTAMP);
  }

  /**
   * Get all the items that their name matches the search.
   *
   * @param text the text user has inputted in the search bar
   * @return the cursor pointing to the data we want
   */
  @Override
  public Cursor getAllSearchItems(String text) {
    SQLiteDatabase database = dbHandler.getWritableDatabase();
    String query =
        " Select * FROM "
            + ProductContract.ProductEntry.TABLE_NAME
            + " WHERE "
            + ProductContract.ProductEntry.COLUMN_NAME
            + " LIKE "
            + "'%"
            + text
            + "%'"
            + " ORDER BY "
            + ProductContract.ProductEntry.COLUMN_TIMESTAMP;

    return database.rawQuery(query, null);
  }
}
