package com.bteam.blocal.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bteam.blocal.R;
import com.bteam.blocal.model.DashboardButtonModel;

import java.util.List;

public class DashboardFragment extends Fragment implements DashboardAdapter.IItemClickListener {
    private static final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel;

    private RecyclerView recyclerView;
    private DashboardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Setup ViewModel, Adapter and RecyclerView
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        adapter = new DashboardAdapter(this);
        recyclerView = view.findViewById(R.id.dashboard_recycler_view);

        // Update the Adapter with the set of buttons from the ViewModel
        dashboardViewModel.getButtons().observe(getViewLifecycleOwner(),
                buttons -> adapter.updateItemsList(buttons));

        // Create Grid with the buttons
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int index) {
        DashboardButtonModel clickedButton = dashboardViewModel.getButtons().getValue().get(index);
        NavHostFragment.findNavController(this).navigate(clickedButton.getActionId());
    }
}