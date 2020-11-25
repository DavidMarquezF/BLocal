package com.bteam.blocal.ui.item_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.utility.Constants;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private IItemClickListener listener;
    private List<ItemModel> items;

    private Context context;

    public ItemListAdapter(IItemClickListener listener) {
        this.listener = listener;
    }

    public void updateItemsList(List<ItemModel> list) {
        items = list;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // We obtain the context here to use it in the onBindViewHolder
        // Even though it's possible to get it from the item views in the onBindViewHolder function
        // This solution seems to be better
        // Source: https://stackoverflow.com/a/48660869
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        ItemViewHolder vh = new ItemViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = items.get(position);
        holder.name.setText(item.getUid());
        holder.price.setText(String.format("%s", item.getPrice()));
        holder.itemImage.setImageResource(R.drawable.ic_baseline_shopping_basket_24);
        holder.inStock.setText(item.isInStock() ? R.string.lbl_in_stock : R.string.lbl_out_stock);
        Glide.with(context).load(item.getImageUrl()).apply(Constants.getItemDefaultOptions()).into(holder.itemImage);
    }


    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }

    public interface IItemClickListener {
        void onItemClick(int index);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemImage;
        TextView name;
        TextView price;
        TextView inStock;

        IItemClickListener listener;

        public ItemViewHolder(@NonNull View itemView, IItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemImage = itemView.findViewById(R.id.img_item);
            name = itemView.findViewById(R.id.txt_title);
            price = itemView.findViewById(R.id.txt_price);
            inStock = itemView.findViewById(R.id.txt_in_stock);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}


