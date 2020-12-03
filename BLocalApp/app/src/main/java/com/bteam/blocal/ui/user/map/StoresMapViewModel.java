package com.bteam.blocal.ui.user.map;

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

import java.util.List;

public class StoresMapViewModel extends AndroidViewModel {
    private static final String TAG = "MapsViewModel";

    private MutableLiveData<List<StoreModel>> nearbyStores;
    private MutableLiveData<StoreModel> selectedStore;


    public StoresMapViewModel(Application application) {
        super(application);
        this.nearbyStores = new MutableLiveData<>();
        this.selectedStore = new MutableLiveData<>();


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

}