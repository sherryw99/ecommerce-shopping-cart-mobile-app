package com.example.shop.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.checkout.CheckoutActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView {
  /** Responsible for aligning items in the list */
  private RecyclerView.LayoutManager layoutManager;
  /** Branch between data and RecyclerView. */
  private ProductAdapter adapter;

  private ArrayList<ProductItem> productItemArrayList;
  private MainActivityPresenter presenter;
  private RecyclerView recyclerView;
  private TextView toastAddText;

  private Boolean checkoutActivityCreated;
  private Button cartBtn;
  private Toast toast;
  private View layout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    presenter = new MainActivityPresenter(this, this);

    setToolBar();
    setCartBtn();
    setToast();
    createProductList();
    buildRecyclerView();
    setSearchBar();
    checkoutActivityCreated = false;
  }

  /** Set up the tool bar. */
  public void setToolBar() {
    Toolbar toolbar = findViewById(R.id.toolBar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
  }

  /** Set up the shopping cart button in the tool bar. */
  public void setCartBtn() {
    cartBtn = findViewById(R.id.cartBtn);
    cartBtn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            openCheckoutActivity();
          }
        });
  }

  /** Set up toast. */
  public void setToast() {
    LayoutInflater inflater = getLayoutInflater();
    layout = inflater.inflate(R.layout.toast_add, (ViewGroup) findViewById(R.id.toastAdd));
    toastAddText = layout.findViewById(R.id.toastAddTxt);
  }

  /** Create products that will show in the RecyclerView. */
  public void createProductList() {
    productItemArrayList = new ArrayList<>();
    productItemArrayList.add(
        new ProductItem(R.drawable.plant_1, "Sansevieria Laurentii", "$25.00"));
    productItemArrayList.add(
        new ProductItem(R.drawable.plant_2, "Zamioculcas zamiifolia", "$30.00"));
    productItemArrayList.add(new ProductItem(R.drawable.plant_3, "Fiddle Leaf Fig", "$30.00"));
    productItemArrayList.add(new ProductItem(R.drawable.plant_4, "Peace Lily", "$30.00"));
    productItemArrayList.add(new ProductItem(R.drawable.plant_5, "Monstera Deliciosa", "$80.00"));
    productItemArrayList.add(
        new ProductItem(R.drawable.plant_6, "Sansevieria Zeylanica", "$25.00"));

    for (ProductItem item : productItemArrayList) {
      presenter.insertProductData(item);
    }
  }

  /** Build the product list. */
  public void buildRecyclerView() {
    recyclerView = findViewById(R.id.productList);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    presenter.setList();

    adapter.setOnItemClickListener(
        new ProductAdapter.OnItemClickListener() {
          @Override
          public void onAddClick(View itemView) {
            presenter.addItemToCart((long) itemView.getTag(), checkoutActivityCreated);
          }
        });
  }

  public void setSearchBar() {
    EditText search = findViewById(R.id.searchBar);
    search.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

          @Override
          public void afterTextChanged(Editable editable) {
            presenter.setNewSearchItems(editable.toString());
          }
        });
  }

  /**
   * Show the content in the list.
   *
   * @param cursor the cursor pointing to the data we want
   */
  @Override
  public void showList(Cursor cursor) {
    adapter = new ProductAdapter(this, cursor);
    recyclerView.setAdapter(adapter);
  }

  /** Show a message when you have successfully added an item to your cart. */
  @Override
  public void showToast() {
    toast = new Toast(getApplicationContext());
    toastAddText.setText(R.string.item_added);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setView(layout);
    toast.show();
  }

  /** Update the RecyclerView for every letter inputted or deleted in the search bar. */
  @Override
  public void refreshList(Cursor cursor) {
    adapter.swapCursor(cursor);
  }

  /**
   * Start the CheckoutActivity when shopping cart icon is clicked; Force toast to cancel when
   * starting a new activity.
   */
  @Override
  public void openCheckoutActivity() {
    checkoutActivityCreated = true;
    Intent intent = new Intent(this, CheckoutActivity.class);
    startActivity(intent);
    if (toast != null) {
      toast.cancel();
    }
  }
}
