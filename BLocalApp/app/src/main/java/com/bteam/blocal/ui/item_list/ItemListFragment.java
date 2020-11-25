package com.bteam.blocal.ui.item_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.ui.item_list.dummy.DummyContent;

import java.util.List;

public class ItemListFragment extends Fragment implements ItemListAdapter.IItemClickListener {

    private ItemListViewModel itemListViewModel;
    private ItemListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemListViewModel =
                new ViewModelProvider(this).get(ItemListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);

        adapter = new ItemListAdapter(this);

        //final TextView textView = root.findViewById(R.id.text_home);
        itemListViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                //adapter.updateItemsList(DummyContent.ITEMS);
                adapter.updateItemsList(itemModels);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int index) {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_list_to_itemDetailFragment);
    }
}