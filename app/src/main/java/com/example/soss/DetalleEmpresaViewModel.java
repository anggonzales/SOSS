package com.example.soss;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.soss.Model.ClsEmpresa;
import com.example.soss.Model.ClsRating;

public class DetalleEmpresaViewModel extends ViewModel {
    private MutableLiveData<ClsRating> mutableLiveDataRating;
    private MutableLiveData<ClsEmpresa> mutableLiveDataEmpresa;

    public void setRatingModel (ClsRating clsRating){
        if (mutableLiveDataRating !=null){
            mutableLiveDataRating.setValue(clsRating);
        }
    }

    public MutableLiveData<ClsRating> getMutableLiveDataRating() {
        return mutableLiveDataRating;
    }

    public DetalleEmpresaViewModel(){
        mutableLiveDataRating = new MutableLiveData<>();
    }

    public MutableLiveData<ClsEmpresa> getMutableLiveDataEmpresa() {

        if (mutableLiveDataEmpresa == null){
            mutableLiveDataEmpresa = new MutableLiveData<>();
        }
      //  mutableLiveDataEmpresa.setValue();
        return mutableLiveDataEmpresa;
    }

    public void setEmpresaModel(ClsEmpresa clsEmpresa) {
        if (mutableLiveDataEmpresa != null){
            mutableLiveDataEmpresa.setValue(clsEmpresa);
        }
    }
}
