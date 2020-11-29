package com.bteam.blocal.ui.edit_item;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.repository.StoreRepository;
import com.bteam.blocal.ui.item_detail.ItemDetailFragmentArgs;

public class EditItemViewModel extends ViewModel {

    public boolean getIsModeEdit(){
        return uid != null && !uid.isEmpty();
    }
    private String uid;

    private LiveData<Resource<ItemModel>> itemDetail;
    public LiveData<Resource<ItemModel>> getItemDetail(){
        return itemDetail;
    }


    public void updateItem(ItemModel model, StoreRepository.IOnCompleteCallback<Void> callback){
        StoreRepository.getInstance().updateItem(uid, model ,callback);
    }

    public void createItem(ItemModel model, StoreRepository.IOnCompleteCallback<ItemModel> callback){
        StoreRepository.getInstance().createItem(model, callback);
    }

    public void setArguments(Bundle savedInstanceState) {
        StoreRepository repository = StoreRepository.getInstance();
        ItemDetailFragmentArgs args = ItemDetailFragmentArgs.fromBundle(savedInstanceState);
        if(args != null){
            uid = args.getItemUid();
            itemDetail = repository.getStoreItem(uid);
        }

    }
}