package binar.and.challengechaptertujuh.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.UserManager
import binar.and.challengechaptertujuh.model.GetUserItem

import binar.and.challengechaptertujuh.network.ApiModule
import binar.and.challengechaptertujuh.viewmodel.viewmodeluser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mumtaz.binar.challangechapterenam.model.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var dataUser : List<GetUserItem>
    lateinit var viewModel : viewmodeluser
    lateinit var email: String
    lateinit var password: String
    lateinit var toast : String
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        userManager = UserManager(requireContext())
        getDataUserItem()

        val daftar = view.findViewById<TextView>(R.id.login_daftar)
        val login = view.findViewById<Button>(R.id.login_button)

        daftar.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        login.setOnClickListener {
            if (login_username.text.isNotEmpty() && login_password.text.isNotEmpty()){
                email = login_username.text.toString()
                password = login_password.text.toString()
                check(dataUser)
            }
            else{
                toast = "Harap Isi Semua Data"
                custom()
            }
        }
        return view
    }

    fun custom(){
        val text = toast
        val toast = Toast.makeText(
            requireActivity()?.getApplicationContext(),
            text,
            Toast.LENGTH_LONG
        )
        val text1 =
            toast.getView()?.findViewById(android.R.id.message) as TextView
        val toastView: View? = toast.getView()
        toastView?.setBackgroundColor(Color.TRANSPARENT)
        text1.setTextColor(Color.RED);
        text1.setTextSize(15F)
        toast.show()
        toast.setGravity(Gravity.CENTER or Gravity.TOP, 0, 960)
    }

    fun getDataUserItem(){
        viewModel = ViewModelProvider(this).get(viewmodeluser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it


        })
        viewModel.userApi()
    }

    fun check(dataUser : List<GetUserItem>) {
        userManager = UserManager(requireContext())
        login(email, password)
        for (i in dataUser.indices) {
            if (email == dataUser[i].username && password == dataUser[i].password) {

                GlobalScope.launch {
                    userManager.saveDataLogin("true")
                    userManager.saveDataUser(dataUser[i].id, dataUser[i].email,dataUser[i].username, dataUser[i].completeName,dataUser[i].dateofbirth, dataUser[i].address )
                }

                view?.findNavController()
                    ?.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    fun login(email :String, password : String){
        ApiModule.instance.login(email, password).enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful){

                    Toast.makeText(requireContext(), "Login Berhasil", Toast.LENGTH_SHORT).show()
                }else{
                    toast = "Data yang dimasukkan salah!"
                    custom()
                }
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

            }
        })
    }


}
//class LoginFragment : Fragment() {
//
//    lateinit var username : String
//    lateinit var passsword : String
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        // return inflater.inflate(R.layout.fragment_login, container, false)
//
//        val view = inflater.inflate(R.layout.fragment_login,container,false)
//        return view
//
//
//        val daftar = view.findViewById<TextView>(R.id.login_daftar)
//
//        daftar.setOnClickListener {
//            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
//        }
//       return view
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        login_button.setOnClickListener{
//            if (login_username.text.isNotEmpty() && login_password.text.isNotEmpty()){
//                username = login_username.text.toString()
//                passsword = login_password.text.toString()
//
//            }else{
//                login(username , passsword)
//            }
//
//        }
//
//    }
//
//
//
//    fun login(username :String, password : String){
//        ApiClient.instance.getAllUser()
//            .enqueue(object : retrofit2.Callback<List<GetUserItem>>{
//                override fun onResponse(
//                    call: Call<List<GetUserItem>>,
//                    response: Response<List<GetUserItem>>
//                ) {
//                    if (response.isSuccessful){
//                        if (response.body()?.isEmpty()==true){
//                            Toast.makeText(requireContext(),"Unknown User", Toast.LENGTH_LONG).show()
//                        }else{
//                            when{
//                                response.body()?.size!! > 1 -> {
//                                    Toast.makeText(requireContext(), "mohon masukkan data yang benar",android.widget.Toast.LENGTH_LONG).show()
//                                }
//                                username != response.body()!![0].username -> {
//                                    Toast.makeText(requireContext(), "email tidak terdaftar",android.widget.Toast.LENGTH_LONG).show()
//                                }
//                                password != response.body()!![0].password -> {
//                                    Toast.makeText(requireContext(), "password salah",android.widget.Toast.LENGTH_LONG).show()
//
//                                }else ->{
//                                Navigation.findNavController(view!!).navigate(R.id.action_loginFragment_to_homeFragment)
//                            }
//                            }
//                        }
//
//                    }else{
//                        Toast.makeText(requireContext(),response.message(), Toast.LENGTH_LONG).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<GetUserItem>>, t: Throwable) {
//                    Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
//                }
//            })}
//
//}