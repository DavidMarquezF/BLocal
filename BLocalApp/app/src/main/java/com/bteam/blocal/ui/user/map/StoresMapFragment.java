package com.bteam.blocal.ui.user.map;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bteam.blocal.R;
import com.bteam.blocal.data.model.StoreModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class StoresMapFragment extends com.bteam.blocal.ui.shared.map.MapsFragment {
    private List<Marker> storeMarkers = new ArrayList<>();
    private StoresMapViewModel mapsViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapsViewModel = new ViewModelProvider(this).get(StoresMapViewModel.class);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onMapReady(GoogleMap googleMap) {

        mapsViewModel.getNearbyStores().observe(getViewLifecycleOwner(), stores -> {
            // Clear the store markers
            for (Marker marker : storeMarkers) {
                marker.remove();
            }
            storeMarkers.clear();

            // Add them again
            for (StoreModel store : stores) {
                LatLng storePos = new LatLng(store.getLocation().getLatitude(), store.getLocation().getLongitude());
                Marker marker = googleMap.addMarker(new MarkerOptions().position(storePos)
                        .title(store.getName()).snippet(store.getOwnerId()));
                marker.setTag(store);
                storeMarkers.add(marker);
            }

        });

        googleMap.setOnMarkerClickListener(marker -> {
            openStoreDialog((StoreModel) marker.getTag());
            return true;
        });
    }

    private void openStoreDialog(StoreModel storeModel) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(storeModel.getName())
                .setMessage("ID: " + storeModel.getUid() + "\nOwner: " + storeModel.getOwnerId())
                .setIcon(R.drawable.ic_baseline_store_24)
                .setNeutralButton("Close", (dialog, which) -> {
                })
                .setPositiveButton("Store Detail", (dialog, which) -> {
                    // Pass store's uid to be retrieved in the StoreDetailFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("storeUid", storeModel.getUid());
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.openStoreDetailFromMaps, bundle);
                })
                .show();
    }

}