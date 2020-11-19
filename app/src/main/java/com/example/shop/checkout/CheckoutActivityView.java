package com.example.shop.checkout;

import android.database.Cursor;

public interface CheckoutActivityView {

  void showList(Cursor cursor);

  void refreshList(Cursor cursor);

  void setSubtotal(String text);

  void setDiscount(String text);

  void setTax(String text);

  void setTotal(String text);
}
