package com.bteam.blocal.ui.shared.item_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public abstract class ItemListFragment extends Fragment implements ItemListAdapter.IItemClickListener {

    private ItemListViewModel itemListViewModel;
    private ItemListAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemListViewModel =
                new ViewModelProvider(this).get(ItemListViewModel.class);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        adapter = new ItemListAdapter(this, new FirestorePagingOptions.Builder<ItemModel>()
                .setLifecycleOwner(this)
                .setQuery(itemListViewModel.getQuery(), itemListViewModel.getPagingConfig(), ItemModel.class)
                .build());


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the adapter

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh_store);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Clear items and obtain data again
                adapter.refresh();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.list_items);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter.bindSwipeRefresh(swipeRefreshLayout);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot document, int index) {
        navigateToDetail(document.toObject(ItemModel.class).getUid());
    }
    protected abstract void navigateToDetail(String uid);
}