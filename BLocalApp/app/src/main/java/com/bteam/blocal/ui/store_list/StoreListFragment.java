package com.bteam.blocal.ui.store_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.StoreModel;

public class StoreListFragment extends Fragment implements StoreListAdapter.IItemClickListener {
    private static final String TAG = "StoreListFragment";
    private StoreListViewModel storeListViewModel;

    private RecyclerView rcvStoreList;
    private StoreListAdapter adapter;

    public StoreListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_store_list, container, false);

        // Setup ViewModel, Adapter and RecyclerView
        storeListViewModel =
                new ViewModelProvider(this).get(StoreListViewModel.class);
        adapter = new StoreListAdapter(this);
        rcvStoreList = view.findViewById(R.id.rcv_store_list);

        // Update the Adapter with the set of stores from the ViewModel
        storeListViewModel.getStores().observe(getViewLifecycleOwner(),
                stores -> adapter.updateItemList(stores));

        // Create List with the store view items
        rcvStoreList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rcvStoreList.addItemDecoration(new DividerItemDecoration(rcvStoreList.getContext(),
                DividerItemDecoration.VERTICAL));
        rcvStoreList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int index) {
        // TODO implement transition from the list to the detail
        StoreModel sm = storeListViewModel.getStores().getValue().get(index);
        Bundle bundle = new Bundle();
        bundle.putString("storeUid", sm.getUid());
        NavHostFragment.findNavController(this)
                .navigate(R.id.openStoreDetailFromList, bundle);
    }
}