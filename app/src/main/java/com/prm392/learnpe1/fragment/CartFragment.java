package com.prm392.learnpe1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.adapter.CartAdapter;
import com.prm392.learnpe1.model.Cart;
import com.prm392.learnpe1.model.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements CartAdapter.OnQuantityChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartList;
    private Button checkoutBtn, btnDesc, btnInc, btnDelete;
    private TextView totalPice, tvEmptyCartMessage;
    private FirebaseFirestore database;
    private FirebaseAuth auth;

    public CartFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        checkoutBtn = view.findViewById(R.id.btnCheckout);
        totalPice = view.findViewById(R.id.txtTotalPrice);
        tvEmptyCartMessage = view.findViewById(R.id.tvEmptyCartMessage);
        //init firebase
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //init recyclerview
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //init cart list
        cartList = new ArrayList<>();
        //load cart
//        loadSampleData();
        loadCartItem();

        adapter = new CartAdapter(cartList, this, position -> {
            Cart cart = cartList.get(position);
            removeCartItem(cart, position);
        });
        recyclerView.setAdapter(adapter);


        checkoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Order placed successfully.", Toast.LENGTH_SHORT).show();
        });

        return view;


    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadCartItem() {
        String userId = auth.getCurrentUser().getUid();
        database.collection("carts").document(userId).collection("user").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                cartList.clear();
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    Cart cart = snapshot.toObject(Cart.class);
                    cartList.add(cart);
                    adapter.notifyDataSetChanged();

                }
                adapter.notifyDataSetChanged();
                updateTotalPrice();
                checkCartEmptyState();
            } else {
                Toast.makeText(getContext(), "Failed to load cart items.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeOrder() {


    }

    private void checkCartEmptyState() {
        if (cartList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);  // Hide the RecyclerView
            tvEmptyCartMessage.setVisibility(View.VISIBLE);  // Show the empty cart message
        } else {
            recyclerView.setVisibility(View.VISIBLE);  // Show the RecyclerView
            tvEmptyCartMessage.setVisibility(View.GONE);  // Hide the empty cart message
        }
    }
    @Override
    public void onQuantityChange(int position, int quantity) {
        // Update the quantity in the cartList
        Cart cart = cartList.get(position);
        cart.setQuantity(quantity);
        adapter.notifyItemChanged(position);
        updateTotalPrice();
    }

    //handler total price
    private void updateTotalPrice() {
        double total = 0;
        for (Cart item : cartList) {
            total += item.getQuantity() * item.getProductPrice();
        }
        totalPice.setText(String.format("Total Price: $%.2f", total)); // Update the total price TextView

    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeCartItem(Cart cart, int position) {
        String userId = auth.getCurrentUser().getUid();
        String productId = cart.getProductId();

        database.collection("carts")
                .document(userId)
                .collection("user")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            Cart cart1 = snapshot.toObject(Cart.class);
                            if (cart1.getProductId().equals(productId)) {
                                snapshot.getReference().delete();
                                cartList.remove(position);

                                adapter.notifyDataSetChanged();

                                updateTotalPrice();  // Update total price after removal
                                checkCartEmptyState();
                                Toast.makeText(getContext(), "Item removed from cart.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to remove item from cart.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

