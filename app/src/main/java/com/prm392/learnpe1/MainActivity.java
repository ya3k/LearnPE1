package com.prm392.learnpe1;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prm392.learnpe1.databinding.ActivityMainBinding;
import com.prm392.learnpe1.fragment.CartFragment;
import com.prm392.learnpe1.fragment.OrderHistoryFragment;
import com.prm392.learnpe1.fragment.ProductListFragment;
import com.prm392.learnpe1.model.Users;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init toolbar
        setSupportActionBar(binding.toolbar);
        // firebase
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        // Kiểm tra nếu người dùng đã đăng nhập
        if (auth.getCurrentUser() != null) {
            loadUserProfile();
        } else {
            Toast.makeText(this, "User not logged in. Redirecting to Login.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        replaceFragment(new ProductListFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_product_list) {
                replaceFragment(new ProductListFragment());
                setTitle("Products");
            } else if (itemId == R.id.nav_cart) {
                replaceFragment(new CartFragment());
                setTitle("Cart");
            } else if (itemId == R.id.nav_order_history) {
                replaceFragment(new OrderHistoryFragment());
                setTitle("Order History");
            }
            return true;
        });

        binding.btnCart.setOnClickListener(v ->{
            replaceFragment(new CartFragment());
            setTitle("Cart");
        });





    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    private void loadUserProfile() {
        String uid = auth.getCurrentUser().getUid();

        database.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Users users = document.toObject(Users.class);
                            if (users != null) {
                                binding.txtName.setText(users.getUserName());
                            } else {
                                binding.txtName.setText("No Profile Data");
                            }
                        } else {
                            binding.txtName.setText("No Profile Found");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
