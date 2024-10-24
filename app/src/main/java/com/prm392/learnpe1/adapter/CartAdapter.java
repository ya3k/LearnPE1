package com.prm392.learnpe1.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartList;
    private OnQuantityChangeListener onQuantityChangeListener;
    private OnCartItemRemove onCartItemRemove;

    public CartAdapter(List<Cart> cartList, OnQuantityChangeListener onQuantityChangeListener, OnCartItemRemove onCartItemRemove) {

        this.cartList = cartList;
        this.onQuantityChangeListener = onQuantityChangeListener;
        this.onCartItemRemove = onCartItemRemove;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTv, priceTv, quantityTv;
        private ImageView imgProduct;
        private CardView cView;
        private Button btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.txtcProductName);
            priceTv = itemView.findViewById(R.id.txtcProductPrice);
            quantityTv = itemView.findViewById(R.id.txtcQuantity);
            imgProduct = itemView.findViewById(R.id.cImg);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            cView = itemView.findViewById(R.id.cCview);
        }
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_cart, null);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        holder.nameTv.setText(cart.getProductName());
        String formattedPrice = String.format("$%.2f", cart.getProductPrice());
        holder.priceTv.setText(formattedPrice);
        holder.quantityTv.setText(String.valueOf(cart.getQuantity()));
        Glide.with(holder.itemView.getContext()).load(cart.getProductImage()).into(holder.imgProduct);

        holder.btnDelete.setOnClickListener(view -> {
            if(onCartItemRemove != null){
                onCartItemRemove.onCartItemRemove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, int quantity);
    }

    public interface  OnCartItemRemove{
        void onCartItemRemove(int position);
    }
}
