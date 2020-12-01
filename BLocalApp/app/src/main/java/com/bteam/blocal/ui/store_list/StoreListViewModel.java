package com.bteam.blocal.ui.store_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.bteam.blocal.data.model.StoreModel;

import java.util.Arrays;
import java.util.List;

public class StoreListViewModel extends ViewModel {
    private MutableLiveData<List<StoreModel>> stores;

    public StoreListViewModel() {
        this.stores = new MutableLiveData<>();

        init();
    }

    public LiveData<List<StoreModel>> getStores() {
        return stores;
    }

    public void setStores(List<StoreModel> stores) {
        this.stores.setValue(stores);
    }

    private void init() {
        //this.stores.setValue(Arrays.asList(items));
    }
/*
    private StoreModel[] items = {
        new StoreModel("1", "Skejby Specialbutik", "Store owner 1", 56.1943794, 10.1950201),
        new StoreModel("2", "Naeringen Lokal Kiosk", "Store owner 2", 56.1941645, 10.1991454),
        new StoreModel("3", "LIDL Skejby", "Store owner 3", 56.1995529, 10.1828308)
    };*/
}
