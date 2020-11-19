package com.example.shop.main;

import android.database.Cursor;

public interface MainActivityView {

  void showList(Cursor cursor);

  void showToast();

  void refreshList(Cursor cursor);

  void openCheckoutActivity();
}
