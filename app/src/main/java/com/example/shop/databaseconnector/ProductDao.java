package com.example.shop.databaseconnector;

import android.database.Cursor;

import com.example.shop.main.ProductItem;

public interface ProductDao {

  void addItem(ProductItem item);

  String getName(long id);

  double getPrice(long id);

  int getImageSource(long id);

  Cursor getAllProductItems();

  Cursor getAllSearchItems(String text);
}
