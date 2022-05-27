package binar.and.challengechaptertujuh.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and.challengechaptertujuh.model.GetUserItem
import binar.and.challengechaptertujuh.network.ApiModule
import retrofit2.Call
import retrofit2.Response

class viewmodeluser : ViewModel(){

    lateinit var liveDataUserItem : MutableLiveData<List<GetUserItem>>

    init {
        liveDataUserItem = MutableLiveData()
    }

    fun getLiveUserObserver() : MutableLiveData<List<GetUserItem>> {
        return liveDataUserItem
    }

    fun userApi(){
        ApiModule.instance.getAllUser()
            .enqueue(object : retrofit2.Callback<List<GetUserItem>>{
                override fun onResponse(
                    call: Call<List<GetUserItem>>,
                    getAllItem: Response<List<GetUserItem>>
                ) {
                    if (getAllItem.isSuccessful){
                        liveDataUserItem.postValue(getAllItem.body())

                    }else{
                        liveDataUserItem.postValue(null)

                    }
                }
                override fun onFailure(call: Call<List<GetUserItem>>, t: Throwable) {
                    liveDataUserItem.postValue(null)
                }
            })
    }

    fun updateDataUser(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        ApiModule.instance.updateUser(id, username, completeName, dateofbirth, address)
            .enqueue(object : retrofit2.Callback<List<GetUserItem>> {
                override fun onResponse(
                    call: Call<List<GetUserItem>>,
                    response: Response<List<GetUserItem>>
                ) {
                    liveDataUserItem.postValue(response.body())

                }
                override fun onFailure(call: Call<List<GetUserItem>>, t: Throwable) {
                    liveDataUserItem.postValue(null)
                }
            })

    }

}