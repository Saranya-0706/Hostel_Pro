package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LostFound_ViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference
    private val storage = FirebaseStorage.getInstance().reference

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser = _currentUser.asStateFlow()

    private val _lostItems = MutableStateFlow<List<LostFoundItem>>(emptyList())
    val lostItems = _lostItems.asStateFlow()

    private val _foundItems = MutableStateFlow<List<LostFoundItem>>(emptyList())
    val foundItems = _foundItems.asStateFlow()

    private val _item = MutableStateFlow<LostFoundItem?>(null)
    val item = _item.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems(){
        db.child("lost").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(LostFoundItem::class.java)
                }
                _lostItems.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        db.child("found").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    dataSnapshot.getValue(LostFoundItem::class.java)
                }
                _foundItems.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun addItem(item: LostFoundItem, type: String){
        val user = auth.currentUser ?: return
        val userId = user.uid

        db.child(type).child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val key = db.child(type).push().key ?: return
                val item = item.copy(id = key,userId = userId)
                db.child(type).child(key).setValue(item)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        loadItems()

    }

    fun deleteItem(item: LostFoundItem, type: String){
        db.child(type).child(item.id).removeValue()
    }

    fun uploadImage(uri :Uri,type: String,onSuccess :(String)-> Unit,onFailure :(Exception)-> Unit ){
        val imgRef = storage.child("$type/").child(type)
        imgRef.putFile(uri).addOnSuccessListener {
            imgRef.downloadUrl.addOnSuccessListener { uri->
                onSuccess(uri.toString())
            }.addOnFailureListener {
                onFailure(it)
            }
        }
    }

    fun getItemByID(itemId : String,type: String){

        db.child(type).child(itemId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                _item.value = snapshot.getValue(LostFoundItem::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                _item.value = null
            }

        })

    }
}












