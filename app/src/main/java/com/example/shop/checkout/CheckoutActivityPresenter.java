package com.example.shop.checkout;

import com.example.shop.databaseconnector.CartDaoImpl;

public class CheckoutActivityPresenter {
  public static final double discountPercentage = 0.1;
  public static final double taxPercentage = 0.13;

  private CheckoutActivity activity;
  private CheckoutActivityView view;

  protected CheckoutActivityPresenter(
      CheckoutActivity checkoutActivity, CheckoutActivityView checkoutActivityView) {
    this.activity = checkoutActivity;
    this.view = checkoutActivityView;
  }

  void updateCartTotal(boolean checked, boolean applied) {
    CheckoutActivity.sum = CartDaoImpl.getInstance(activity).calculateSubtotal();
    calculate(CheckoutActivity.sum, checked, applied);
  }

  void calculate(double subtotal, boolean checked, boolean applied) {
    double discount;
    double tax;
    double total;

    view.setSubtotal(String.format("%.2f", subtotal));

    if (applied) {
      discount = subtotal * discountPercentage;
      String txt = "-" + String.format("%.2f", discount);
      view.setDiscount(txt);
    } else {
      discount = 0;
      view.setDiscount("-");
    }

    if (checked) {
      tax = (subtotal - discount) * taxPercentage;
      view.setTax(String.format("%.2f", tax));
    } else {
      tax = 0;
      view.setTax("-");
    }

    total = subtotal - discount + tax;
    view.setTotal(String.format("%.2f", total));
  }

  void deleteItem(long id, Boolean checked, Boolean applied) {
    CartDaoImpl cartDao = CartDaoImpl.getInstance(activity);
    cartDao.deleteItem(id);
    updateCartTotal(checked, applied);
    view.refreshList(cartDao.getAllCartItems());
  }

  void increaseItem(long id, Boolean checked, Boolean applied) {
    CartDaoImpl cartDao = CartDaoImpl.getInstance(activity);
    cartDao.increaseItem(id);
    updateCartTotal(checked, applied);
    view.refreshList(cartDao.getAllCartItems());
  }

  void decreaseItem(long id, Boolean checked, Boolean applied) {
    CartDaoImpl cartDao = CartDaoImpl.getInstance(activity);
    cartDao.decreaseItem(id);
    updateCartTotal(checked, applied);
    view.refreshList(cartDao.getAllCartItems());
  }

  void setList() {
    view.showList(CartDaoImpl.getInstance(activity).getAllCartItems());
  }
}
