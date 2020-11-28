package com.bteam.blocal.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.model.errors.NoDocumentException;
import com.bteam.blocal.data.model.errors.UnsuccessfulQueryException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.List;

public class StoreRepository {

    private static StoreRepository _instance;

    //private final MutableLiveData<String> _currStoreUid;

    private FirebaseFirestore db;
    private StoreRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public static StoreRepository getInstance(){
        if (_instance == null) {
            _instance = new StoreRepository();
        }
        return _instance;
    }



    public Query getItemsQuery(){
        return db.collection("stores")
                .document("y51lMtTGgqZMHTbpcAKh")
                .collection("items");
    }

    public LiveData<Resource<List<ItemModel>>> getStoreItems(){
        MutableLiveData<Resource<List<ItemModel>>> liveData = new MutableLiveData<>(Resource.loading(null));
        db.collection("stores")
                .document("y51lMtTGgqZMHTbpcAKh")
                .collection("items")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            QuerySnapshot doc = task.getResult();
                            liveData.setValue(Resource.success(doc.toObjects(ItemModel.class)));
                        }
                        else{
                            liveData.setValue(Resource.error(new UnsuccessfulQueryException(), null));
                        }
                    }
                });

        return liveData;
    }

    public LiveData<Resource<ItemModel>> getStoreItem(String uid){
        MutableLiveData<Resource<ItemModel>> liveData = new MutableLiveData<>(Resource.loading(null));
        db.collection("stores")
                .document("y51lMtTGgqZMHTbpcAKh")
                .collection("items")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()){
                                liveData.setValue(Resource.success(doc.toObject(ItemModel.class)));
                            }
                            else{
                                liveData.setValue(Resource.error(new NoDocumentException(), null));
                            }
                        }
                        else{
                            liveData.setValue(Resource.error(new UnsuccessfulQueryException(), null));
                        }
                    }
                });

        return liveData;
    }
}
