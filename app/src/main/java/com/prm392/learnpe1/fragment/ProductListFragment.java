package com.prm392.learnpe1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.adapter.ProductAdapter;
import com.prm392.learnpe1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private FirebaseFirestore database;

    public ProductListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        //init firestore
        database =  FirebaseFirestore.getInstance();

        //init recyclerview
        recyclerView = view.findViewById(R.id.recyclerViewProducts);

        // Set a GridLayoutManager with 2 columns
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2)); // Change here

        // Initialize product list and adapter
        productList = new ArrayList<>();

        //load product list
        loadProducts();
        adapter =  new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);



        return view;

    }



    @SuppressLint("NotifyDataSetChanged")
    private void loadProducts() {
        database.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            for (Product product : querySnapshot.toObjects(Product.class)) {
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        Toast.makeText(getContext(), "Error getting products", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}