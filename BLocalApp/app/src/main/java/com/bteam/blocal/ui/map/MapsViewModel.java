package com.bteam.blocal.ui.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bteam.blocal.data.model.StoreModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;
import java.util.List;

public class MapsViewModel extends AndroidViewModel {
    private static final String TAG = "MapsViewModel";

    private MutableLiveData<List<StoreModel>> nearbyStores;
    private MutableLiveData<StoreModel> selectedStore;

    private FusedLocationProviderClient fusedLocationClient;
    private MutableLiveData<Location> currentLocation;

    public MapsViewModel(Application application) {
        super(application);
        this.nearbyStores = new MutableLiveData<>();
        this.selectedStore = new MutableLiveData<>();
        this.currentLocation = new MutableLiveData<>();

        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);

        //this.nearbyStores.setValue(Arrays.asList(dummyData));
    }

    public LiveData<List<StoreModel>> getNearbyStores() {
        return nearbyStores;
    }

    public void setNearbyStores(List<StoreModel> nearbyStores) {
        this.nearbyStores.setValue(nearbyStores);
    }

    public LiveData<StoreModel> getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(StoreModel selectedStore) {
        this.selectedStore.setValue(selectedStore);
    }

    public LiveData<Location> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation.setValue(currentLocation);
    }

    @SuppressLint("MissingPermission")
    public void requestCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (null != location) {
                        Log.d(TAG, "requestCurrentLocation: Obtained last location!");
                        currentLocation.setValue(location);
                    } else {
                        Log.d(TAG, "requestCurrentLocation: Last location was not obtained!");
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "requestCurrentLocation: " + e.getLocalizedMessage(), e)
                );
    }

}