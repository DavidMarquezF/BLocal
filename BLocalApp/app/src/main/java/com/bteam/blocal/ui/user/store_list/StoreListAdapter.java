package com.bteam.blocal.ui.user.store_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.StoreModel;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder> {
    private List<StoreModel> nearbyStores;
    private IItemClickListener listener;

    public StoreListAdapter(IItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent,
                false);
        return new StoreListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreListViewHolder holder, int position) {
        StoreModel sm = nearbyStores.get(position);
        holder.txtStoreName.setText(sm.getName());
        holder.txtStoreOwner.setText(sm.getOwnerId());
        // TODO: calculate distance to the stores
        holder.txtStoreDistance.setText("3.4km");
    }

    @Override
    public int getItemCount() {
        if (null != nearbyStores)
            return nearbyStores.size();
        else
            return 0;
    }

    public void updateItemList(List<StoreModel> items) {
        nearbyStores = items;
        notifyDataSetChanged();
    }

    public interface IItemClickListener {
        void onItemClick(int index);
    }

    public class StoreListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtStoreName, txtStoreOwner, txtStoreDistance;
        ImageView imgStoreIcon;

        IItemClickListener listener;

        public StoreListViewHolder(@NonNull View itemView, IItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            txtStoreName = itemView.findViewById(R.id.txt_store_item_name);
            txtStoreOwner = itemView.findViewById(R.id.txt_store_item_owner);
            txtStoreDistance = itemView.findViewById(R.id.txt_store_item_distance);
            imgStoreIcon = itemView.findViewById(R.id.img_store_item_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}
