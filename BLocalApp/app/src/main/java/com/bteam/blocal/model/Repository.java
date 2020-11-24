package com.bteam.blocal.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Repository {
    private static final String TAG = "REPOSITORY";
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private CollectionReference itemCollection;
    private static Repository repository;
    //Persisted items
    private MutableLiveData<ArrayList<ItemModel>> items;
    private MutableLiveData<ItemModel> selectedItem;
    private MutableLiveData<ArrayList<ItemModel>> storeItems;
    //Search functionality
    Boolean found = false;

    // Repository methods
    public Repository() {
        Log.d(TAG, "Repository: ");
        db = FirebaseFirestore.getInstance();
        if(this.auth == null){
            auth = FirebaseAuth.getInstance();
        }
        //Items initialisation
        if(items==null){
            items = new MutableLiveData<ArrayList<ItemModel>>();
        }
        if(storeItems == null){
            storeItems = new MutableLiveData<ArrayList<ItemModel>>();
        }
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection
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

    //Auth
    public FirebaseAuth auth(){
        return this.auth;
    }

    public static Repository getInstance(){
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    //Database CRUD methods
    //CREATE
    public void addItem(ItemModel item){
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection
                .add(new ItemModel(item.getUid(), item.getName(), item.getImageUrl(), item.getPrice(), item.getStock(), item.getStore()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Adding item succeeded: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Adding item failed: ", e);
                    }
                });
    }

    //READ
    public MutableLiveData<ArrayList<ItemModel>> getItems(){
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection
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

    public MutableLiveData<ItemModel> getItem(String uid){
        found = false;
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection.document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                if(selectedItem == null){
                                    selectedItem = new MutableLiveData<ItemModel>();
                                }
                                selectedItem.setValue(document.toObject(ItemModel.class));
                                found = true;
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }

                });
        if(found){
            return selectedItem;
        } else {
            return null;
        }
    }

    //UPDATE
    //Note that if a document with corresponding name isn't found, it will be created
    public void updateItem(ItemModel item){
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection.document(item.getUid()).set(item);
    }

    //DELETE
    public void deleteAll(){
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        for(DocumentSnapshot doc : snapshots){
                            doc.getReference().delete();
                        }
                    }
                });
    }

    public void deleteItem(ItemModel item){
        if(itemCollection == null){
            itemCollection = db.collection("items");
        }
        itemCollection.document(item.getUid()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Deleted item " + item.getName());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting document: " + e);
                    }
                });
    }
}
