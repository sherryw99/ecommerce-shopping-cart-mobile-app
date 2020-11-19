package com.example.shop.databaseconnector;

import android.database.Cursor;

public interface CartDao {

  void addItem(String name, double price, int amount, int image);

  void increaseItem(long id);

  void decreaseItem(long id);

  void deleteItem(long id);

  int getAmountInMain(String name);

  int getAmountInCheckout(long id);

  double calculateSubtotal();

  Cursor getAllCartItems();
}
