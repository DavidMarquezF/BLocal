package com.bteam.blocal.ui.shared.item_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bteam.blocal.utility.InStockText;
import com.bumptech.glide.Glide;
import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.utility.Constants;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.DocumentSnapshot;

public class ItemListAdapter extends FirestorePagingAdapter<ItemModel, ItemListAdapter.ItemViewHolder> {

    private IItemClickListener listener;
    private Context context;
    private SwipeRefreshLayout _swipeRefreshLayout;

    public ItemListAdapter(IItemClickListener listener, @NonNull FirestorePagingOptions<ItemModel> options) {
        super(options);
        this.listener = listener;
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
    protected void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i, @NonNull ItemModel itemModel) {
        itemViewHolder.name.setText(itemModel.getName());
        itemViewHolder.price.setText(String.format("%s", itemModel.getPrice()));
        itemViewHolder.itemImage.setImageResource(R.drawable.ic_baseline_shopping_basket_24);
        itemViewHolder.inStock.setText(InStockText.isInStockText(itemModel.isInStock()));
        Glide.with(context).load(itemModel.getImageUrl()).apply(Constants.getItemDefaultOptions()).into(itemViewHolder.itemImage);
    }

    @Override
    protected void onError(@NonNull Exception e) {
        super.onError(e);
        //TODO: Handle error with callback so that snackbar can be shown
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        switch (state){
            case LOADING_INITIAL:
            case LOADING_MORE:
                swipeRefreshIsReloading(true);
                break;
            case LOADED:
            case FINISHED:
                swipeRefreshIsReloading(false);
                break;
            case ERROR:
                //TODO: Handle error
                swipeRefreshIsReloading(false);
                break;
        }
    }

    public void bindSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout){
        _swipeRefreshLayout = swipeRefreshLayout;
    }

    private void swipeRefreshIsReloading(boolean isRefreshing){
        if(_swipeRefreshLayout != null){
            _swipeRefreshLayout.setRefreshing(isRefreshing);
        }
    }

    public interface IItemClickListener {
        void onItemClick(DocumentSnapshot document, int index);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION && listener != null){
                listener.onItemClick(getItem(position),position);
            }

        }
    }
}


