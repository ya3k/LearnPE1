package com.prm392.learnpe1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392.learnpe1.R;
import com.prm392.learnpe1.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView textOrderDate;
        private TextView textTotalPrice;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textOrderDate = itemView.findViewById(R.id.text_order_date);
            textTotalPrice = itemView.findViewById(R.id.text_total_price);
        }

        public void bind(Order order) {
            // Assuming orderDate is stored as a timestamp
            textOrderDate.setText(String.valueOf(order.getOrderDate())); // Format the timestamp as needed
            textTotalPrice.setText(String.format("$%.2f", order.getTotalPrice())); // Format the price as needed
        }
    }
}
