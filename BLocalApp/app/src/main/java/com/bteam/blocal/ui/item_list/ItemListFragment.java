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
import com.bteam.blocal.ui.item_list.dummy.DummyContent;

public class ItemListFragment extends Fragment implements ItemListAdapter.IItemClickListener {

    private ItemListViewModel itemListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemListViewModel =
                new ViewModelProvider(this).get(ItemListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item_list, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        itemListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                /*textView.setText(s);*/
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
            ItemListAdapter adapter = new ItemListAdapter(this);
            adapter.updateItemsList(DummyContent.ITEMS);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(int index) {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_list_to_itemDetailFragment);
    }
}