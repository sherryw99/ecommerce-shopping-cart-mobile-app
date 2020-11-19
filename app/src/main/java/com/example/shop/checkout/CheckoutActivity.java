package com.example.shop.checkout;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

import java.util.Objects;

public class CheckoutActivity extends AppCompatActivity implements CheckoutActivityView {
  @SuppressLint("StaticFieldLeak")
  public static CartAdapter adapterCart;

  public static double sum;
  private RecyclerView recyclerView;
  private CheckoutActivityPresenter presenter;
  private TextView toastAddText;
  private TextView totalNum;
  private TextView taxNum;
  private TextView discountNum;
  private TextView subtotalNum;
  private EditText editText;
  private CheckBox checkBox;
  private Button cartBtn;
  private Button applyBtn;
  private Button removeBtn;
  private Boolean checked;
  private Boolean applied;
  private View layout;
  private Toast toast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_checkout);

    presenter = new CheckoutActivityPresenter(this, this);

    setToolBar();
    setCartBtn();
    setInitialValue();
    setCheckBox();
    setDiscountButtons();
    setTexts();
    buildRecyclerView();
    setToast();
  }

  @Override
  protected void onResume() {
    super.onResume();

    presenter.updateCartTotal(checked, applied);
  }

  @Override
  protected void onPause() {
    super.onPause();

    if (toast != null) {
      toast.cancel();
    }
  }

  /** Set up the tool bar. */
  public void setToolBar() {
    Toolbar toolbar = findViewById(R.id.toolBar);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  /** Set up the cart button to be invisible. */
  public void setCartBtn() {
    cartBtn = findViewById(R.id.cartBtn);
    cartBtn.setVisibility(View.GONE);
  }

  /** Check box is not checked and no discount code applied. */
  public void setInitialValue() {
    checked = false;
    applied = false;
  }

  /** Set up the include-tax check box. */
  public void setCheckBox() {
    checkBox = findViewById(R.id.checkBox);
    checkBox.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            checked = checkBox.isChecked();
            presenter.updateCartTotal(checked, applied);
          }
        });
  }

  public void setDiscountButtons() {
    applyBtn = findViewById(R.id.applyBtn);
    removeBtn = findViewById(R.id.removeBtn);

    applyBtn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            String msg = editText.getText().toString();
            applied = !msg.equals("");
            presenter.updateCartTotal(checked, applied);
            if (applied) {
              showToast();
            }
          }
        });

    removeBtn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            applied = false;
            editText.getText().clear();
            presenter.updateCartTotal(checked, applied);
          }
        });
  }

  public void setTexts() {
    totalNum = findViewById(R.id.totalNum);
    taxNum = findViewById(R.id.taxNum);
    discountNum = findViewById(R.id.discountNum);
    subtotalNum = findViewById(R.id.subtotalNum);
    editText = findViewById(R.id.discountCode);

    presenter.updateCartTotal(checked, applied);
  }

  /** Build the shopping cart list. */
  public void buildRecyclerView() {
    recyclerView = findViewById(R.id.cartList);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    presenter.setList();

    adapterCart.setOnItemClickListener(
        new CartAdapter.OnItemClickListener() {

          @Override
          public void onDeleteClick(View itemView) {
            presenter.deleteItem((long) itemView.getTag(), checked, applied);
          }

          @Override
          public void onDecreaseClick(View itemView) {
            presenter.decreaseItem((long) itemView.getTag(), checked, applied);
          }

          @Override
          public void onIncreaseClick(View itemView) {
            presenter.increaseItem((long) itemView.getTag(), checked, applied);
          }
        });
  }

  /** Set up the toast. */
  public void setToast() {
    LayoutInflater inflater = getLayoutInflater();
    layout = inflater.inflate(R.layout.toast_apply, (ViewGroup) findViewById(R.id.toastApply));
    toastAddText = layout.findViewById(R.id.toastApplyTxt);
  }

  /** Show a message when you've successfully applied a discount to your oder. */
  public void showToast() {
    toastAddText.setText("You saved 10% off your order!");
    toast = new Toast(getApplicationContext());
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setView(layout);
    toast.show();
  }

  /**
   * Display the shopping cart list.
   *
   * @param cursor the cursor pointing the data we want
   */
  @Override
  public void showList(Cursor cursor) {
    adapterCart = new CartAdapter(this, cursor);
    recyclerView.setAdapter(adapterCart);
  }

  /** Refresh the shopping cart list. */
  @Override
  public void refreshList(Cursor cursor) {
    adapterCart.swapCursor(cursor);
  }

  @Override
  public void setSubtotal(String text) {
    subtotalNum.setText(text);
  }

  @Override
  public void setDiscount(String text) {
    discountNum.setText(text);
  }

  @Override
  public void setTax(String text) {
    taxNum.setText(text);
  }

  @Override
  public void setTotal(String text) {
    totalNum.setText(text);
  }
}
