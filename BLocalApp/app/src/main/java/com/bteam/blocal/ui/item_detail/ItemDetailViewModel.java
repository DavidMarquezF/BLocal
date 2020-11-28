package com.bteam.blocal.ui.item_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.repository.StoreRepository;

public class ItemDetailViewModel  extends ViewModel {

    public LiveData<Resource<ItemModel>> itemDetail;

    public ItemDetailViewModel(@NonNull SavedStateHandle savedStateHandle){
        itemDetail = new MutableLiveData<>();
    }

    public void setArguments(Bundle savedInstanceState) {
        StoreRepository repository = StoreRepository.getInstance();
        String uid = ItemDetailFragmentArgs.fromBundle(savedInstanceState).getItemUid();
        itemDetail = repository.getStoreItem(uid);
    }
}
