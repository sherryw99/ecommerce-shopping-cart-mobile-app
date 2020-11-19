# Checkout Price Calculator

This is an Android application that simulates the functionalities as that of the system that is used during checkout.

## Getting Started

The following instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Installing

Clone this repository and import it into **Android Studio**.
```
git clone https://github.com/sherryw99/ecommerce-shopping-cart-mobile-app.git
```

You might need the latest version of Android Studio to run this.

## Running the tests

You can find the test file here.

```
/app/src/test/java/com/example/shop/checkout/CheckoutActivityPresenterTest.java
```

To run it, open the Project window in Android Studio, and then right-click the test and click **Run**.

## Generating APK

From Android Studio:

1. Go to **Build** > **Build Bundle(s) / Build APK(s)** > **Build APK(s)**.
2. Once done, there will be a pop-up on the bottom right. Click the **locate** button in this dialogue. It should open the debug folder that contains a file called “app-debug.apk.”

An APK file called **app-shop.apk** has already been generated and placed at the root directory for you to load on an Android device.

## Running the app

### Creating an Android Virtual Device

From Android Studio:

1. Open the AVD Manager by clicking **Tools** > **AVD Manager**.
2. Click **Create Virtual Device**, at the bottom of the AVD Manager dialogue, to open the **Select Hardware** page.
3. Select a hardware profile and then click **Next**.
4. Select the system image for a particular API level, and then click **Next**.
5. Change AVD properties as needed, and then click **Finish**.

**Pixel 3 API 28** is recommended for this application.

See [Create an AVD](https://developer.android.com/studio/run/managing-avds#createavd) for more detailed instructions.

### Loading an APK on the emulator

From Android Studio:

1. To start the emulator, open the AVD Manager by clicking **Tools** > **AVD Manager**.
2. Double-click an AVD or click **Run**.
3. Once loaded, drag an APK file onto the emulator screen, and an APK installer dialogue will then appear.
4. When the installation completes, you can view the app in your apps list.

See [Launch the Android Emulator without first running an app](https://developer.android.com/studio/run/emulator#runningemulator) and [Install and add files](https://developer.android.com/studio/run/emulator#installadd) for more detailed instructions.

## Features

Steps for using the features in this app. A video in the root directory called **Demo**  demonstrates how each feature works and where they are located.

### Search

The search bar is in the middle of the welcome page. You can search your desired items here.

### Add item

There is an add-to-cart button for each product. The button looks like a shopping cart with a plus sign on it. A success message will pop up when you have successfully added an item to the cart.

### View cart

You can view what items are in your shopping cart by clicking the black shopping cart icon at the top-right corner. It will take you to a new page where you can see all the items you have added to the cart and an order summary at the bottom of the screen.

### Update cart

There are three buttons for each item in the cart that update your order. You can delete an item from the cart by clicking the **X** on the right. You can edit the item amount by clicking the **<** or **>** buttons next to the number under the product name.

### Include tax

You can choose to include tax by checking the checkbox next to the cart total.

### Apply discount

You can apply a discount to your order by entering a discount code in the box under cart total, where it says 'Enter Discount Code,' and click the **Apply** button. To remove it, click the **Remove** button on the right. A success message will pop up when you have successfully applied a discount to your order.

## Acknowledgments

[Coding in Flow](https://codinginflow.com) - Android Tutorials

