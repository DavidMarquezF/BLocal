package com.bteam.blocal.data.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.bteam.blocal.data.model.ItemModel;
import com.bteam.blocal.data.model.Resource;
import com.bteam.blocal.data.model.errors.NoDocumentException;
import com.bteam.blocal.data.model.errors.UnsuccessfulQueryException;
import com.bteam.blocal.utility.FirestoreLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
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


    public interface IOnSuccessCallback<T>{
        void OnSuccess(T data);
    }
    public interface IOnErrorCallback{
        void OnError(Throwable err);
    }

    public interface IOnCompleteCallback<T> extends  IOnErrorCallback, IOnSuccessCallback<T>{
    }

    public interface IOnResourceChange<T>{
        void OnResourceChange(Resource<T> resource);
    }

    public void updateItem(String uid, ItemModel model, IOnCompleteCallback<Void> callback){
        db.collection("stores").document("y51lMtTGgqZMHTbpcAKh")
                .collection("items").document(uid).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.OnSuccess(null);
                }
                else{
                    callback.OnError(new UnsuccessfulQueryException());
                }
            }
        });
    }

    public void createItem(ItemModel model, IOnCompleteCallback<ItemModel> callback) {
        db.collection("stores").document("y51lMtTGgqZMHTbpcAKh")
                .collection("items").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    DocumentReference doc = task.getResult();
                    model.setUid(doc.getId());
                    callback.OnSuccess(model);
                }
                else{
                    callback.OnError(new UnsuccessfulQueryException());
                }
            }
        });

    }


    public LiveData<Resource<ItemModel>> getStoreItem(String uid){
        FirestoreLiveData liveDat = new FirestoreLiveData(db.collection("stores")
                .document("y51lMtTGgqZMHTbpcAKh")
                .collection("items")
                .document(uid));

        return Transformations.map(liveDat, new Function<DocumentSnapshot, Resource<ItemModel>>() {
            @Override
            public Resource<ItemModel> apply(DocumentSnapshot input) {
                return Resource.success(input.toObject(ItemModel.class));
            }
        });

       /* MutableLiveData<Resource<ItemModel>> liveData = new MutableLiveData<>(Resource.loading(null));
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

        return liveData;*/
    }
}
