package binar.and.challengechaptertujuh.view.fragment


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.data.RegisterResponse
import binar.and.challengechaptertujuh.model.GetUserItem
import binar.and.challengechaptertujuh.network.ApiModule
import binar.and.challengechaptertujuh.viewmodel.viewmodeluser
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class RegisterFragment : Fragment() {
    lateinit var regisemailtext: String

    lateinit var dataUser: List<GetUserItem>

    lateinit var register: String
    lateinit var viewModel: viewmodeluser
    lateinit var password: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        getDataUserItem()

        view.register_button.setOnClickListener {

            val username = register_username.text.toString()
            regisemailtext = register_email.text.toString()
            password = register_password.text.toString()
            val confirmpass = register_konfirmasipassword.text.toString()


            if (register_username.text.isNotEmpty() && register_email.text.isNotEmpty()
                && register_password.text.isNotEmpty()
                && register_konfirmasipassword.text.isNotEmpty()
            ) {

                if (password == confirmpass) {
                    for (i in dataUser.indices) {
                        if (regisemailtext == dataUser[i].username) {
                            register = "false"
                            break
                        } else {
                            register = "true"
                        }
                    }
                    if (register == "true") {

                        regisUser(username, regisemailtext, password)
                        view.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(requireContext(), "Password tidak Sama", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(requireContext(), "Data Masih Kosong", Toast.LENGTH_SHORT).show()
            }

        }
        return view

    }



    fun regisUser(username: String, email: String, password: String) {
        ApiModule.instance.register(username, email, password)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "berhasil", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

                }
            })
    }

    fun getDataUserItem() {
        viewModel = ViewModelProvider(this).get(viewmodeluser::class.java)
        viewModel.getLiveUserObserver().observe(viewLifecycleOwner, Observer {
            dataUser = it

        })
        viewModel.userApi()

    }
}
