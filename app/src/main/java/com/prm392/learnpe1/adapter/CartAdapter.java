package com.prm392.learnpe1.adapter;

import android.view.View;
import android.view.ViewGroup;
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

    public CartAdapter(List<Cart> cartList, OnQuantityChangeListener onQuantityChangeListener) {
        this.cartList = cartList;
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTv, priceTv, quantityTv;
        private ImageView imgProduct;
        private CardView cView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.txtProductName);
            priceTv = itemView.findViewById(R.id.txtProductPrice);
            quantityTv = itemView.findViewById(R.id.txtCartProductQuantity);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            cView = itemView.findViewById(R.id.cview);
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
        holder.priceTv.setText(String.valueOf(cart.getProductPrice()));
        holder.quantityTv.setText(cart.getQuantity());
        Glide.with(holder.itemView.getContext()).load(cart.getProductImage()).into(holder.imgProduct);

//        // Set up the increment button
//        holder.itemView.findViewById(R.id.btnIncrease).setOnClickListener(v -> {
//            int quantity = cart.getQuantity();
//            cart.setQuantity(quantity + 1); // Increase quantity
//            onQuantityChangeListener.onQuantityChange(position, quantity + 1);
//            holder.quantityTv.setText(String.valueOf(quantity + 1));
//        });
//        // Set up the decrement button
//        holder.itemView.findViewById(R.id.btnDecrease).setOnClickListener(v -> {
//            int quantity = cart.getQuantity();
//            if (quantity > 1) { // Prevent negative quantity
//                cart.setQuantity(quantity - 1); // Decrease quantity
//                onQuantityChangeListener.onQuantityChange(position, quantity - 1);
//                holder.quantityTv.setText(String.valueOf(quantity - 1));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, int quantity);
    }
}
