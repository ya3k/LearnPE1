package com.prm392.learnpe1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.model.Cart;
import com.prm392.learnpe1.model.Product;


public class ProductDetailsFragment extends Fragment {
    private Product product;
    private int quantity = 1;

    private FirebaseFirestore database;
    private FirebaseAuth auth;

    public ProductDetailsFragment(Product product) {
        this.product = product;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        //init firestore
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        TextView nameTv = view.findViewById(R.id.txtProductName);
        TextView priceTv = view.findViewById(R.id.txtProductPrice);
        TextView descriptionTv = view.findViewById(R.id.txtProductDescription);
        ImageView imgProduct = view.findViewById(R.id.imgProduct);
        Button btnCart = view.findViewById(R.id.btnAddToCart);
        Button btnDesc = view.findViewById(R.id.btnDecrease);
        Button btnInc = view.findViewById(R.id.btnIncrease);
        TextView quantityTv = view.findViewById(R.id.txtProductQuantity);
        //setProductDetail
        nameTv.setText(product.getProName());
        String formattedPrice = String.format("$%.2f", product.getPrice());

        priceTv.setText(formattedPrice);
        descriptionTv.setText(product.getDescription());
        Glide.with(getContext()).load(product.getImg()).into(imgProduct);


        btnDesc.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityTv.setText("Quantity: " + quantity);
            } else {
                Toast.makeText(getContext(), "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
            }
        });

        btnInc.setOnClickListener(v -> {
            quantity++;
            quantityTv.setText("Quantity: " + quantity);
        });

        //addtocard
        btnCart.setOnClickListener(view1 -> {

            Cart cart = new Cart(product.getProName(), product.getPrice(), product.getImg(), quantity);
            String uid = auth.getCurrentUser().getUid();
            database.collection("carts").document(uid)
                    .collection("user")
                    .add(cart)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error: " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        return view;
    }


}