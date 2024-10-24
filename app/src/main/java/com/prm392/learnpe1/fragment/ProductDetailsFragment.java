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

        // Initialize Firestore and Auth
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // View components
        TextView nameTv = view.findViewById(R.id.txtProductName);
        TextView priceTv = view.findViewById(R.id.txtProductPrice);
        TextView descriptionTv = view.findViewById(R.id.txtProductDescription);
        ImageView imgProduct = view.findViewById(R.id.imgProduct);
        Button btnCart = view.findViewById(R.id.btnAddToCart);
        Button btnDesc = view.findViewById(R.id.btnDecrease);
        Button btnInc = view.findViewById(R.id.btnIncrease);
        TextView quantityTv = view.findViewById(R.id.txtProductQuantity);

        // Set product details
        nameTv.setText(product.getProName());
        priceTv.setText(String.format("$%.2f", product.getPrice()));
        descriptionTv.setText(product.getDescription());
        Glide.with(getContext()).load(product.getImg()).into(imgProduct);
        quantityTv.setText("Quantity: " + quantity);

        // Set button listeners
        btnDesc.setOnClickListener(v -> decreaseQuantity(quantityTv));
        btnInc.setOnClickListener(v -> increaseQuantity(quantityTv));

        // Add to cart
        btnCart.setOnClickListener(view1 -> addToCart());

        return view;
    }

    private void decreaseQuantity(TextView quantityTv) {
        if (quantity > 1) {
            quantity--;
            quantityTv.setText("Quantity: " + quantity);
        } else {
            Toast.makeText(getContext(), "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void increaseQuantity(TextView quantityTv) {
        quantity++;
        quantityTv.setText("Quantity: " + quantity);
    }

    private void addToCart() {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "You need to log in to add items to the cart.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cart cart = new Cart(product.getId(), product.getProName(), product.getPrice(), product.getImg(), quantity);
        String uid = auth.getCurrentUser().getUid();

        // Add product to cart
        database.collection("carts").document(uid)
                .collection("user")
                .whereEqualTo("productId", product.getId())
                .get()
                .addOnCompleteListener(querySnapshot -> {
                    //check if existed
                    if (querySnapshot.getResult().isEmpty()) {
                        database.collection("carts").document(uid)
                                .collection("user")
                                .add(cart)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to add product to cart", Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        // If it exists, update the quantity
                        String documentId = querySnapshot.getResult().getDocuments().get(0).getId();
                        int existingQuantity = querySnapshot.getResult().getDocuments().get(0).getLong("quantity").intValue(); // Get the current quantity

                        // Increment the quantity
                        int newQuantity = existingQuantity + quantity;
                        //update exited product
                        database.collection("carts").document(uid)
                                .collection("user")
                                .document(querySnapshot.getResult().getDocuments().get(0).getId())
                                .update("quantity", newQuantity)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getContext(), "Product updated in cart", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to update product in cart", Toast.LENGTH_SHORT).show();
                                });
                    }
                });
    }
}
