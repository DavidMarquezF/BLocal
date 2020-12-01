package com.bteam.blocal.ui.item_detail;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.repository.StoreRepository;

public class ItemDetailViewModel  extends ViewModel {

    public LiveData<Resource<ItemModel>> itemDetail;

    public ItemDetailViewModel(){
        itemDetail = new MutableLiveData<>();
    }

    public void setArguments(Bundle savedInstanceState) {
        StoreRepository repository = StoreRepository.getInstance();
        String uid = ItemDetailFragmentArgs.fromBundle(savedInstanceState).getItemUid();
        itemDetail = repository.getStoreItemLive(uid);
    }
}