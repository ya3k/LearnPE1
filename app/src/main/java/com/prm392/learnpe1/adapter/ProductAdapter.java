package com.prm392.learnpe1.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.learnpe1.MainActivity;
import com.prm392.learnpe1.R;
import com.prm392.learnpe1.fragment.ProductDetailsFragment;
import com.prm392.learnpe1.model.Product;

import java.net.URL;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTv, priceTv, descriptionTv;
        private ImageView imgProduct;
        private CardView cView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            proID = itemView.findViewById(R.id.txtProID);
            nameTv = itemView.findViewById(R.id.txtProName);
            priceTv = itemView.findViewById(R.id.txtProPrice);
            descriptionTv = itemView.findViewById(R.id.txtProDes);
            imgProduct = itemView.findViewById(R.id.imgPro);
            cView = itemView.findViewById(R.id.cview);
        }
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_product, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
//        holder.proID.setText(product.getId());
        holder.nameTv.setText(product.getProName());

        String formattedPrice = String.format("$%.2f", product.getPrice());

        holder.priceTv.setText(formattedPrice);
        holder.descriptionTv.setText(product.getDescription());

        // Use Glide to load the image from the URL
        Glide.with(holder.itemView.getContext())
                .load(product.getImg()) // Ensure product.getImg() returns the image URL
                .into(holder.imgProduct);

        //set ev product detail
        holder.itemView.setOnClickListener(v ->{
            ProductDetailsFragment detailsFragment = new ProductDetailsFragment(product);
            //replace current fragment with product fragment
            ((MainActivity) holder.itemView.getContext()).replaceFragment(detailsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
