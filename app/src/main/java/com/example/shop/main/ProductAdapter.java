package com.example.shop.main;

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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
  private OnItemClickListener onItemClickListener;

  private Context pContext;
  private Cursor pCursor;

  public ProductAdapter(Context context, Cursor cursor) {
    pContext = context;
    pCursor = cursor;
  }

  public interface OnItemClickListener {
    void onAddClick(View itemView);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    onItemClickListener = listener;
  }

  public static class ProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView productName;
    public TextView priceTag;
    public ImageView addCartImage;

    public ProductViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
      super(itemView);
      imageView = itemView.findViewById(R.id.imageView);
      productName = itemView.findViewById(R.id.productName);
      priceTag = itemView.findViewById(R.id.priceTag);
      addCartImage = itemView.findViewById(R.id.addToCart);

      addCartImage.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                  listener.onAddClick(itemView);
                }
              }
            }
          });
    }
  }

  /** Connect to our layout. */
  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(pContext).inflate(R.layout.product_item, parent, false);
    ProductViewHolder productViewHolder = new ProductViewHolder(view, onItemClickListener);
    return productViewHolder;
  }

  /**
   * Get info from ProductItem. Pass value to imageView, productName, and priceTag. First item in
   * the list has position 0.
   *
   * @param position The item we are currently looking at.
   */
  public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    if (!pCursor.moveToPosition(position)) {
      return;
    }

    String name =
        pCursor.getString(pCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_NAME));
    double price_num =
        pCursor.getInt(pCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRICE));
    String price = "$" + String.format("%.2f", price_num);
    int image = pCursor.getInt(pCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_IMAGE));
    long id = pCursor.getLong(pCursor.getColumnIndex(ProductContract.ProductEntry._ID));

    holder.imageView.setImageResource(image);
    holder.productName.setText(name);
    holder.priceTag.setText(price);
    holder.itemView.setTag(id);
  }

  /** Define how many items in our list. */
  @Override
  public int getItemCount() {
    return pCursor.getCount();
  }

  /** Refresh the list. */
  public void swapCursor(Cursor newCursor) {
    if (pCursor != null) {
      pCursor.close();
    }
    pCursor = newCursor;
    if (newCursor != null) {
      notifyDataSetChanged();
    }
  }
}
