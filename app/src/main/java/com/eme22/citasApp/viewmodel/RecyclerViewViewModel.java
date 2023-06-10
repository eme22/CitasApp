package com.eme22.citasApp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eme22.citasApp.model.api.Api;
import com.eme22.citasApp.model.api.ApiClient;

import java.util.ArrayList;

public abstract class RecyclerViewViewModel<T> extends ViewModel {

    final MutableLiveData<ArrayList<T>> listMutableLiveData = new MutableLiveData<>();
    ArrayList<T> itemsArrayList = new ArrayList<>();

    final MutableLiveData<Boolean> listEmptyMutableLiveData = new MutableLiveData<>(false);

    final MutableLiveData<Boolean> loadingMutableLivedata = new MutableLiveData<>(false);


    final Api api = ApiClient.getSOService();

    public MutableLiveData<Boolean> getListEmptyMutableLiveData() {
        return listEmptyMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoadingMutableLivedata() {
        return loadingMutableLivedata;
    }

    public MutableLiveData<ArrayList<T>> getUserMutableLiveData(){
        return listMutableLiveData;
    }

    public RecyclerViewViewModel() {
        init();
    }

    public void init(){}

}
