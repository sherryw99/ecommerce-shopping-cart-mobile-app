package com.example.shop.checkout;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
  private OnItemClickListener onItemClickListener;
  private Context cContext;
  private Cursor cCursor;

  public interface OnItemClickListener {

    void onDeleteClick(View itemView);

    void onDecreaseClick(View itemView);

    void onIncreaseClick(View itemView);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    onItemClickListener = listener;
  }

  public CartAdapter(Context context, Cursor cursor) {
    cContext = context;
    cCursor = cursor;
  }

  public static class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameText;
    public TextView priceText;
    public TextView amountText;
    public ImageView delete;
    public ImageView decrease;
    public ImageView increase;

    public CartViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
      super(itemView);

      imageView = itemView.findViewById(R.id.productImage);
      nameText = itemView.findViewById(R.id.name);
      priceText = itemView.findViewById(R.id.price);
      amountText = itemView.findViewById(R.id.amount);
      delete = itemView.findViewById(R.id.delete);
      decrease = itemView.findViewById(R.id.decrease);
      increase = itemView.findViewById(R.id.increase);

      delete.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                  listener.onDeleteClick(itemView);
                }
              }
            }
          });

      decrease.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                  listener.onDecreaseClick(itemView);
                }
              }
            }
          });

      increase.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                  listener.onIncreaseClick(itemView);
                }
              }
            }
          });
    }
  }

  @NonNull
  @Override
  public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(cContext);
    View view = inflater.inflate(R.layout.cart_item, parent, false);
    return new CartViewHolder(view, onItemClickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
    if (!cCursor.moveToPosition(position)) {
      return;
    }
    String name = cCursor.getString(cCursor.getColumnIndex(CartContract.CartEntry.COLUMN_NAME));
    double price_num = cCursor.getInt(cCursor.getColumnIndex(CartContract.CartEntry.COLUMN_PRICE));
    String price = "$" + String.format("%.2f", price_num);
    int amount = cCursor.getInt(cCursor.getColumnIndex(CartContract.CartEntry.COLUMN_AMOUNT));
    int image = cCursor.getInt(cCursor.getColumnIndex(CartContract.CartEntry.COLUMN_IMAGE));
    long id = cCursor.getLong(cCursor.getColumnIndex(CartContract.CartEntry._ID));

    holder.nameText.setText(name);
    holder.priceText.setText(price);
    holder.amountText.setText(String.valueOf(amount));
    holder.imageView.setImageResource(image);
    holder.itemView.setTag(id);
  }

  @Override
  public int getItemCount() {
    return cCursor.getCount();
  }

  public void swapCursor(Cursor newCursor) {
    if (cCursor != null) {
      cCursor.close();
    }
    cCursor = newCursor;
    if (newCursor != null) {
      notifyDataSetChanged();
    }
  }
}
