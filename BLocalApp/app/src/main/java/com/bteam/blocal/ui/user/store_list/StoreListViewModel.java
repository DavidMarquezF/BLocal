package com.bteam.blocal.ui.user.store_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;


import com.bteam.blocal.data.model.StoreModel;
import com.bteam.blocal.data.repository.StoreRepository;
import com.google.firebase.firestore.Query;

import java.util.List;

public class StoreListViewModel extends ViewModel {
    public Query getQuery(){
        return StoreRepository.getInstance().getStoreQuery();
    }

    public PagedList.Config getPagingConfig(){
        // Init Paging Configuration
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(2)
                .setPageSize(10)
                .build();
    }


}
