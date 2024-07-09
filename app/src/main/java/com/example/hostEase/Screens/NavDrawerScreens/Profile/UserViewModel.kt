package com.example.hostEase.Screens.NavDrawerScreens.Profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user : LiveData<User> get() = _user
    private val database :DatabaseReference = FirebaseDatabase.getInstance().reference
    private val storageRef = FirebaseStorage.getInstance().reference

    fun loadUserProfile(){
        viewModelScope.launch {

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            database.child("users").child(userId).addListenerForSingleValueEvent(
                object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        _user.value = user!!
                    }

                    override fun onCancelled(error: DatabaseError) {}
                } )
        }
    }

    fun updateUserProfile(userId: String, phone: String, userName: String, profileImgUri: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {

            val updatedUser = _user.value?.copy(
                username = userName,
                phone = phone,
                profileImgUrl = profileImgUri
            )
            updatedUser?.let {
                database.child("users").child(userId).setValue(it).addOnCompleteListener {
                    onComplete(it.isSuccessful)
                }
            }

            loadUserProfile()

        }
    }

    fun uploadProfileImage(userId : String, uri : Uri, onSuccess: (String) -> Unit,onFailure :(Exception)-> Unit ){

        val imgRef = storageRef.child("Profile Images").child(userId)

        imgRef.putFile(uri).addOnSuccessListener {
            imgRef.downloadUrl.addOnSuccessListener { uri->
                onSuccess(uri.toString())
            }.addOnFailureListener {
                onFailure(it)
            }
        }

        loadUserProfile()
    }
}