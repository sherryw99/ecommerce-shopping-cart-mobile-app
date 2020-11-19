package com.example.shop.checkout;

import android.provider.BaseColumns;

public class CartContract {

  private CartContract() {}

  public static final class CartEntry implements BaseColumns {
    public static final String TABLE_NAME = "cartList";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TIMESTAMP = "timestamp";
  }
}
