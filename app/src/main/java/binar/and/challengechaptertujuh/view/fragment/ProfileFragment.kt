package binar.and.challengechaptertujuh.view.fragment

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import binar.and.challengechaptertujuh.R
import binar.and.challengechaptertujuh.UserManager
import binar.and.challengechaptertujuh.viewmodel.viewmodeluser
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.logoutdialog.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    lateinit var viewModel: viewmodeluser
    lateinit var username: String
    lateinit var email : String
    lateinit var idUser : String
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        userManager = UserManager(requireContext())

        view.btnupdate.setOnClickListener {
            pickImageFromGallery()
        }

        userManager.userUsername.asLiveData().observe(requireActivity()){
            view.update1.setText(it)
        }
        userManager.userNama.asLiveData().observe(requireActivity()){
            view.update2.setText(it) }
        userManager.userTGLHR.asLiveData().observe(requireActivity()){
            view.update3.setText(it)
        }
        userManager.userAlamat.asLiveData().observe(requireActivity()){
            view.update4.setText(it)
        }
        userManager.userEmail.asLiveData().observe(requireActivity()){
            email = it.toString()
        }

        view.btnupdate.setOnClickListener {
            userManager.userID.asLiveData().observe(requireActivity()){
                idUser = it
            }
            username = view.update1.text.toString()
            val cn = view.update2.text.toString()
            val dateofbirth = view.update3.text.toString()
            val address = view.update4.text.toString()

            GlobalScope.launch {
                userManager.saveDataUser(idUser , email, username, cn, dateofbirth, address )
            }

            updateDataUser(idUser.toInt() ,username, cn, dateofbirth, address)

            view.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)

        }



        view.btnlogout.setOnClickListener {
            val custom = LayoutInflater.from(requireContext()).inflate(R.layout.logoutdialog, null)
            val a = AlertDialog.Builder(requireContext())
                .setView(custom)
                .create()

            custom.btnlogouttidak.setOnClickListener {
                a.dismiss()
            }

            custom.btnlogoutya.setOnClickListener {

                GlobalScope.launch {
                    userManager.deleteDataLogin()
                }

                a.dismiss()
                view.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
            a.show()

        }
        return view
    }
    fun updateDataUser(id : Int, username : String, completeName :String, dateofbirth : String, address : String){
        viewModel = ViewModelProvider(this).get(viewmodeluser::class.java)
        viewModel.getLiveUserObserver().observe(requireActivity(), Observer {
            if (it  != null){
                Toast.makeText(requireContext(), "Gagal Update Data", Toast.LENGTH_LONG ).show()
            }else{
                Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_LONG ).show()

            }

        })
        viewModel.updateDataUser(id, username, completeName, dateofbirth, address)

    }

    fun pickImageFromGallery(){

        val inten = Intent(Intent.ACTION_PICK)
        inten.type = "image/*"

        startActivityForResult(inten, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode === Activity.RESULT_OK) {
            profilegambar.setImageURI(data?.data)
        }
    }


}