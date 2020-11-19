package com.example.shop.main;

import android.provider.BaseColumns;

public class ProductContract {

  private ProductContract() {}

  public static final class ProductEntry implements BaseColumns {
    public static final String TABLE_NAME = "productList";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TIMESTAMP = "timestamp";
  }
}
