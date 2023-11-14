package com.example.datavivapractica;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import kotlin.jvm.functions.Function1;

public class BaileViewModel extends AndroidViewModel {
    Baile baile;

    LiveData<Integer> baileLiveData;
    LiveData<String> repeticionLiveData;

    public BaileViewModel(@NonNull Application application) {
        super(application);

        baile = new Baile();

        baileLiveData = Transformations.switchMap(baile.ordenLiveData, new Function1<String, LiveData<Integer>>() {

            String baileAntiguo;

            @Override
            public LiveData<Integer> invoke(String orden) {
                String bailes = orden.split(":")[0];

                if(!bailes.equals(baileAntiguo)){
                    baileAntiguo = bailes;
                    int imagen;
                    switch (bailes) {
                        case "BAILE1":
                        default:
                            imagen = R.drawable.gif1;
                            break;
                        case "BAILE2":
                            imagen = R.drawable.gif2;
                            break;
                        case "BAILE3":
                            imagen = R.drawable.gif3;
                            break;
                        case "BAILE4":
                            imagen = R.drawable.gif4;
                            break;
                        case "BAILE5":
                            imagen = R.drawable.gif5;
                            break;
                        case "BAILE6":
                            imagen = R.drawable.gif6;
                            break;
                        case "BAILE7":
                            imagen = R.drawable.gif7;
                            break;
                    }

                    return new MutableLiveData<>(imagen);
                }
                return null;
            }

        });

        repeticionLiveData = Transformations.switchMap(baile.ordenLiveData, new Function1<String, LiveData<String>>() {
            @Override
            public LiveData<String> invoke(String orden) {
                return new MutableLiveData<>(orden.split(":")[1]);
            }

        });
    }

    LiveData<Integer> obtenerBaile(){
        return baileLiveData;
    }

    LiveData<String> obtenerRepeticion(){
        return repeticionLiveData;
    }
}

