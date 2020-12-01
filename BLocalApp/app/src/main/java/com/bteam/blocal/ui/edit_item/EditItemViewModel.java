package com.bteam.blocal.ui.edit_item;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.repository.StoreRepository;

public class EditItemViewModel extends ViewModel {

    public boolean getIsModeEdit(){
        return uid != null && !uid.isEmpty();
    }
    private String uid;

    private final StoreRepository storeRepository;

    private LiveData<Resource<ItemModel>> itemDetail;
    public LiveData<Resource<ItemModel>> getItemDetail(){
        return itemDetail;
    }


    public EditItemViewModel(){
        storeRepository = StoreRepository.getInstance();
    }
    public void updateItem(ItemModel model, StoreRepository.IOnCompleteCallback<Void> callback){
        storeRepository.updateItem(uid, model ,callback);
    }

    public void createItem(ItemModel model, StoreRepository.IOnCompleteCallback<ItemModel> callback){
        storeRepository.createItem(model, callback);
    }
    public void uploadImage(Bitmap image, StoreRepository.IOnCompleteCallback<String> callback){
        storeRepository.uploadItemImage(image, callback);
    }

    public void setArguments(Bundle savedInstanceState) {
        StoreRepository repository = StoreRepository.getInstance();
        EditItemFragmentArgs args = EditItemFragmentArgs.fromBundle(savedInstanceState);
        if(args != null){
            String uid = args.getItemUid();
            // this function is called when the screen rotates. We only want to get the data the first time
            if(itemDetail == null || !uid.equals(this.uid)){
                if(uid != null){
                    itemDetail = repository.getStoreItem(uid);
                    this.uid = uid;
                }
                else{
                    itemDetail = new MutableLiveData<>();
                }
            }
        }

    }
}