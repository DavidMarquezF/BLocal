package com.bteam.blocal.ui.shared.store_detail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bteam.blocal.R;
import com.google.android.material.button.MaterialButton;

public class StoreDetailFragment extends Fragment {
    private static final String TAG = "StoreDetailFragment";
    private StoreDetailViewModel storeDetailViewModel;

    // REMOVE
    private String storeUid;

    private TextView storeName, storeOwner;
    private MaterialButton btnItems, btnRate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        storeDetailViewModel = new ViewModelProvider(this).get(StoreDetailViewModel.class);

        storeDetailViewModel.getCurrentStore().observe(getViewLifecycleOwner(), storeModel -> {
            storeName.setText(storeModel.getName());
            storeOwner.setText(storeModel.getOwnerId());
        });

        return inflater.inflate(R.layout.fragment_store_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storeName = view.findViewById(R.id.txt_store_detail_name);
        storeOwner = view.findViewById(R.id.txt_store_detail_owner);
        btnItems = view.findViewById(R.id.btn_store_detail_items);
        btnRate = view.findViewById(R.id.btn_store_detail_rating);

        btnItems.setOnClickListener(v -> navigateToItemList());

        // TODO: maybe add rating feature
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: store uid is: " + getArguments().getString("storeUid"));
        // TODO: update the ViewModel internal variable to the real store
//        storeDetailViewModel.setCurrentStore(findStoreById(getArguments().getString("storeUid")));
        storeUid = getArguments().getString("storeUid");
    }

    private void navigateToItemList() {
        Bundle bundle = new Bundle();
        bundle.putString("storeUid", storeUid);
//        bundle.putString("storeUid", storeDetailViewModel.getCurrentStore().getValue().getUid());
        NavHostFragment.findNavController(this)
                .navigate(R.id.openItemListFromStoreDetail, bundle);
    }
}