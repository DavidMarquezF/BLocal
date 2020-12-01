package com.bteam.blocal.ui.store_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.model.StoreModel;

public class StoreDetailViewModel extends ViewModel {
    MutableLiveData<StoreModel> currentStore;

    public StoreDetailViewModel() {
        this.currentStore = new MutableLiveData<>();
        setCurrentStore(dummy);
    }

    public LiveData<StoreModel> getCurrentStore() {
        return currentStore;
    }

    public void setCurrentStore(StoreModel currentStore) {
        this.currentStore.setValue(currentStore);
    }

    // TODO: Add internal methods for finding requested store in the database (by position,
    //  name or owner)

    private StoreModel dummy = new StoreModel("1", "Skejby Specialbutik", "Store owner 1", 56.1943794, 10.1950201);
}