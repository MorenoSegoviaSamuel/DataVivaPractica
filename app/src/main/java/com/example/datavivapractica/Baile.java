package com.example.datavivapractica;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.lifecycle.LiveData;

public class Baile {
    interface BaileListener {
        void cuandoDeLaOrden(String orden);
    }

    Random random = new Random();
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> bailando;

    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarBaile(new BaileListener() {
                @Override
                public void cuandoDeLaOrden(String orden) {
                    postValue(orden);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararBaile();
        }
    };

    void iniciarBaile(BaileListener baileListener) {
        if (bailando == null || bailando.isCancelled()) {
            bailando = scheduler.scheduleAtFixedRate(new Runnable() {
                int baile;
                int repeticiones = -1;

                @Override
                public void run() {
                    if (repeticiones < 0) {
                        repeticiones = random.nextInt(3) + 3;
                        baile = random.nextInt(7)+1;
                    }
                    baileListener.cuandoDeLaOrden("BAILE" + baile + ":" + (repeticiones == 0 ? "CAMBIO" : repeticiones));
                    repeticiones--;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararBaile() {
        if (bailando != null) {
            bailando.cancel(true);
        }
    }
}

