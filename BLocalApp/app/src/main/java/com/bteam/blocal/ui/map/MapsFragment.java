package com.bteam.blocal.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bteam.blocal.R;
import com.bteam.blocal.model.StoreModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    private static final String TAG = "MapsFragment";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1111;

    private GoogleMap gMap;
    private Marker currentLocationMarker;
    private List<Marker> storeMarkers = new ArrayList<>();
    private MapsViewModel mapsViewModel;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;

            // Show the current location on the map
            mapsViewModel.getCurrentLocation().observe(getViewLifecycleOwner(), location -> {
                if (null != currentLocationMarker) currentLocationMarker.remove();
                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                currentLocationMarker = gMap.addMarker(new MarkerOptions().position(current)
                        .title("You are here!").snippet("current location"));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
            });

            mapsViewModel.getNearbyStores().observe(getViewLifecycleOwner(), stores -> {
                // Clear the store markers
                storeMarkers.forEach(Marker::remove);
                storeMarkers.clear();

                // Add them again
                stores.forEach(s -> {
                    LatLng storePos = new LatLng(s.getLat(), s.getLon());
                    Marker marker = gMap.addMarker(new MarkerOptions().position(storePos)
                            .title(s.getName()).snippet(s.getOwner()));
                    marker.setTag(s);
                    storeMarkers.add(marker);
                });
            });

            gMap.setOnMarkerClickListener(marker -> {
                if (!marker.equals(currentLocationMarker)) {
                    openStoreDialog((StoreModel) marker.getTag());
                } else {
                    marker.showInfoWindow();
                }
                return true;
            });
        }
    };

    private void openStoreDialog(StoreModel storeModel) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(storeModel.getName())
                .setMessage("ID: " + storeModel.getUid() + "\nOwner: " + storeModel.getOwner())
                .setIcon(R.drawable.ic_baseline_store_24)
                .setNeutralButton("Close", (dialog, which) -> {
                })
                .setPositiveButton("Store Detail", (dialog, which) -> {
                    Log.d(TAG, "openStoreDialog: " + storeModel.getName() + " " + storeModel.getLat() + " " + storeModel.getLon());
                    // Pass store's uid to be retrieved in the StoreDetailFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("storeUid", storeModel.getUid());
                    NavHostFragment.findNavController(this)
                            .navigate(R.id.action_user_nav_maps_to_user_nav_store_detail, bundle);
                })
                .show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);

        checkLocationPermissions();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: requesting location update");
        mapsViewModel.requestCurrentLocation();
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Log.d(TAG, "checkLocationPermissions: Location permission already granted.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onRequestPermissionsResult: Location permission was successfully obtained.");
        } else {
            Log.d(TAG, "onRequestPermissionsResult: Location permission was not granted!");
        }
    }
}