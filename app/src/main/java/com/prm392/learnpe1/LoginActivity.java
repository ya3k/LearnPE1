package com.prm392.learnpe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prm392.learnpe1.databinding.ActivityLoginBinding;
import com.prm392.learnpe1.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        //insert product list to firestore
//        insertProductListToFireStore();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }


        //login ev
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass;
                email = binding.etEmail.getText().toString();
                pass = binding.etPw.getText().toString();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //register activity
        binding.btnRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }

    private List<Product> getList() {

        List<Product> products = Arrays.asList(
                new Product("Product 1", "Description for product 1", 10, "https://mymenu.vn/assets/images/products/795/450x450/pl.jpg"),
                new Product("Product 2", "Description for product 2" ,20, "https://mymenu.vn/assets/images/products/732/450x450/450-pizza-mag-01.jpg"),
                new Product("Product 3", "Des", 30, "https://mymenu.vn/assets/images/products/904/450x450/450-pizza-chay-01.jpg"),
                new Product("Product 4", "Des", 50, "https://mymenu.vn/assets/images/products/905/450x450/450-pizza-nam-01.jpg"),
                new Product("Product 5", "Des", 40, "https://mymenu.vn/assets/images/products/733/450x450/pizza-peperoni-mn-01.jpg")
        );

        return products;
    }

    private void insertProductListToFireStore() {
        List<Product> products = getList();

        for (Product product : products) {
            database.collection("products")
                    .add(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
