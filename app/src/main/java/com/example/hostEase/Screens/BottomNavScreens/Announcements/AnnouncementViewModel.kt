package com.example.hostEase.Screens.BottomNavScreens.Announcements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Timer
import kotlin.concurrent.fixedRateTimer

class AnnouncementViewModel: ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference

    private val _announcements = MutableStateFlow<List<Announcement>>(emptyList())
    val announcements = _announcements.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    init {
        loadAnnouncements()
    }
     fun loadAnnouncements(){
        _loading.value = true

        db.child("announcements").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val announcements = snapshot.children.mapNotNull {dataSnapshot ->
                    dataSnapshot.getValue(Announcement ::class.java)?.copy(id = dataSnapshot.key ?: "")
                }.sortedByDescending { it.timeStamp }
                _announcements.value = announcements
                _loading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _loading.value = false
            }

        })
    }

    fun addAnnouncement(announcement: Announcement,onComplete :(Boolean) -> Unit){
        val key = db.child("announcements").push().key ?: return
        val announcementWithId = announcement.copy(id = key)
        db.child("announcements").child(key).setValue(announcementWithId).addOnCompleteListener {task->
            if (task.isSuccessful)
                onComplete(true)
            else
                onComplete(false)
        }
    }

    fun deleteAnnouncement(announcementId : String,onComplete: (Boolean) -> Unit){
        db.child("announcements").child(announcementId).removeValue().addOnCompleteListener { task->
            if (task.isSuccessful)
                onComplete(true)
            else
                onComplete(false)
        }
    }
}
