package com.danish.kisah25nabi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danish.kisah25nabi.data.KisahResponse
import com.danish.kisah25nabi.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel() : ViewModel() {

     var isLoading = MutableLiveData<Boolean>()
     var isError = MutableLiveData<Throwable>()
     var KisahResponse = MutableLiveData<List<KisahResponse>>()

    fun getData(responseHandler :  (List<KisahResponse>) -> Unit, errorHandler : (Throwable) -> Unit) {
        ApiClient.getApiService().getKisahNabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responseHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getKisahNabi() {
        isLoading.value = true
        getData({
                isLoading.value = false
            KisahResponse.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}