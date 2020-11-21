package com.bteam.blocal.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "REPOSITORY";
    private FirebaseAuth auth;

    private FirebaseFirestore db;
    private ExecutorService executor;

    private MutableLiveData<ArrayList<ItemModel>> items;

    //Auth - is this even needed?
    public FirebaseAuth auth(){
        return this.auth;
    }

    // Repository methods
    public Repository() {
        db = FirebaseFirestore.getInstance();
        executor = Executors.newSingleThreadExecutor();
        //Items initialisation
        if(items==null){
            items = new MutableLiveData<ArrayList<ItemModel>>();
        }
        db.collection("items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<ItemModel> updatedItems = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                ItemModel im = doc.toObject(ItemModel.class);
                                if (im != null){
                                    updatedItems.add(im);
                                }
                            }
                            items.setValue(updatedItems);
                        }
                    }
                });
    }

    //Database CRUD methods
    //CREATE
    public void addItem(ItemModel item){
        db.collection("items").add(new ItemModel(item.getUid(), item.getName(), item.getImageUrl(), item.getPrice(), item.getStock()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Adding item succeeded: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Adding item failed: ", e);
                    }
                });
    }

    //READ
    public MutableLiveData<ArrayList<ItemModel>> getItems(){
        db.collection("items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        ArrayList<ItemModel> updatedItems = new ArrayList<>();
                        if(snapshot!=null && !snapshot.isEmpty()){
                            for(DocumentSnapshot doc : snapshot.getDocuments()){
                                ItemModel im = doc.toObject(ItemModel.class);
                                if (im != null){
                                    updatedItems.add(im);
                                }
                            }
                            items.setValue(updatedItems);
                        }
                    }
                });
        return items;
    }

    //UPDATE

    //DELETE
    public void deleteAll(){
        db.collection("items").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for(DocumentSnapshot doc : snapshots){
                            doc.getReference().delete();
                        }
                    }
                });
    }
}
