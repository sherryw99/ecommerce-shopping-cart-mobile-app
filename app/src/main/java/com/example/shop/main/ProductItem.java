package com.example.shop.main;

public class ProductItem {
  private int productImageResource;
  private String productName;
  private String priceTag;

  public ProductItem(int imageResource, String name, String price) {
    productImageResource = imageResource;
    productName = name;
    priceTag = price;
  }

  public int getProductImageResource() {
    return productImageResource;
  }

  public String getProductName() {
    return productName;
  }

  public String getPriceTag() {
    return priceTag;
  }
}
