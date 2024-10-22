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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.adapter.CartAdapter;
import com.prm392.learnpe1.model.Cart;
import com.prm392.learnpe1.model.Product;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements CartAdapter.OnQuantityChangeListener{

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartList;
    private Button checkoutBtn, btnDesc, btnInc;

    private FirebaseFirestore database;
    private FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCart);
        checkoutBtn = view.findViewById(R.id.btnCheckout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartList = new ArrayList<>();
        adapter = new CartAdapter(cartList);
        recyclerView.setAdapter(adapter);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadCartItem();

        checkoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Order placed successfully.", Toast.LENGTH_SHORT).show();
        });

        return view;


    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadCartItem() {
        String userId = auth.getCurrentUser().getUid();
        database.collection("carts").document(userId).collection("user").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                cartList.clear();
                for (int i = 0; i < task.getResult().size(); i++) {
                    Cart cart = task.getResult().toObjects(Cart.class).get(i);
                    cartList.add(cart);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Failed to load cart items.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeOrder() {


    }


    @Override
    public void onQuantityChange(int position, int quantity) {
        // Update the quantity in the cartList
        Cart cart = cartList.get(position);
        cart.setQuantity(quantity);
        adapter.notifyItemChanged(position);
    }
}
