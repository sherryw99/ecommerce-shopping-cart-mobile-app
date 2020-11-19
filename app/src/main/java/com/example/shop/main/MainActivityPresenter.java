package com.example.shop.main;

import com.example.shop.checkout.CheckoutActivity;
import com.example.shop.databaseconnector.CartDaoImpl;
import com.example.shop.databaseconnector.ProductDaoImpl;

public class MainActivityPresenter {
  private MainActivity activity;
  private MainActivityView view;

  protected MainActivityPresenter(MainActivity mainActivity, MainActivityView mainActivityView) {
    this.activity = mainActivity;
    this.view = mainActivityView;
  }

  void insertProductData(ProductItem item) {
    ProductDaoImpl.getInstance(activity).addItem(item);
  }

  void addItemToCart(long id, boolean checkoutActivityCreated) {
    ProductDaoImpl productDao = ProductDaoImpl.getInstance(activity);
    CartDaoImpl cartDao = CartDaoImpl.getInstance(activity);
    String name = productDao.getName(id);
    double price = productDao.getPrice(id);
    int image = productDao.getImageSource(id);
    int amount = cartDao.getAmountInMain(name);
    amount++;
    cartDao.addItem(name, price, amount, image);

    if (checkoutActivityCreated) {
      CheckoutActivity.sum = CartDaoImpl.getInstance(activity).calculateSubtotal();
      CheckoutActivity.adapterCart.swapCursor(cartDao.getAllCartItems());
    }
    view.showToast();
  }

  void setList() {
    view.showList(ProductDaoImpl.getInstance(activity).getAllProductItems());
  }

  void setNewSearchItems(String text) {
    view.refreshList(ProductDaoImpl.getInstance(activity).getAllSearchItems(text));
  }
}
