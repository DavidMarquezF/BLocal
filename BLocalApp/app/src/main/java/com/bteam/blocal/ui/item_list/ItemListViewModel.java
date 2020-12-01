package com.bteam.blocal.ui.item_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.repository.StoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class ItemListViewModel extends ViewModel {

    private MutableLiveData<List<ItemModel>> _items;

    public Query getQuery(){
        return StoreRepository.getInstance().getItemsQuery();
    }

    public PagedList.Config getPagingConfig(){
        // Init Paging Configuration
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(2)
                .setPageSize(10)
                .build();
    }

    public ItemListViewModel() {

        _items = new MutableLiveData<>();

    }

    public LiveData<List<ItemModel>> getItems() {
        return _items;
    }
}