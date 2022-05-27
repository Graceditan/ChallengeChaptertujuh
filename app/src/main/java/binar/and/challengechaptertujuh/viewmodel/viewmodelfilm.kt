package binar.and.challengechaptertujuh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.and.challengechaptertujuh.model.GetAllFilmItem
import binar.and.challengechaptertujuh.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


    @HiltViewModel
    class viewmodelfilm @Inject constructor(api: ApiService) : ViewModel() {
        private var filmdatalive = MutableLiveData<List<GetAllFilmItem>>()
        val film : LiveData<List<GetAllFilmItem>> = filmdatalive

//    @Inject
//    lateinit var dataa : ApiService

        init {
            viewModelScope.launch {
                val datafilm = api.getAllfilm()
                delay(2000)
                filmdatalive.value = datafilm
            }
        }


    }
