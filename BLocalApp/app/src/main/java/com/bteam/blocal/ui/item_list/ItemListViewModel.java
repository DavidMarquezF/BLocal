package com.bteam.blocal.ui.item_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bteam.blocal.data.model.ItemModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class ItemListViewModel extends ViewModel {

    private MutableLiveData<List<ItemModel>> _items;

    public ItemListViewModel() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        _items = new MutableLiveData<>();
     //   db.collection("shopItems").whereEqualTo("shopId", "y51lMtTGgqZMHTbpcAKh").whereArrayContains("categories", Arrays.asList("food", "meat")).get()
        db.collection("stores").document("y51lMtTGgqZMHTbpcAKh").collection("items")
                .whereEqualTo("categories.food", true)
                .whereEqualTo("categories.meat", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        _items.setValue(queryDocumentSnapshots.toObjects(ItemModel.class));
                    }
                });
    }

    public LiveData<List<ItemModel>> getItems() {
        return _items;
    }
}