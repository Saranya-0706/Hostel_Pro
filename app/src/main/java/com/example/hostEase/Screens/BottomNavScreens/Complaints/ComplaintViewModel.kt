package com.example.hostEase.Screens.BottomNavScreens.Complaints

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ComplaintViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    private val _complaints = MutableStateFlow<List<Complaint>>(emptyList())
    val complaints = _complaints.asStateFlow()

    private val _searchMatchedComplaints = MutableStateFlow<List<Complaint>>(emptyList())
    val searchMatchedComplaints = _searchMatchedComplaints.asStateFlow()

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser = _currentUser.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    init {
        loadComplaint()
    }

    fun loadComplaint(){
        _loading.value = true

        db.child("complaints").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val complaints = snapshot.children.mapNotNull {dataSnapshot ->
                    dataSnapshot.getValue(Complaint ::class.java)?.copy(id = dataSnapshot.key ?: "")
                }.sortedByDescending { it.timeStamp }
                _complaints.value = complaints
                _loading.value = false
            }

            override fun onCancelled(error: DatabaseError) {
                _loading.value = false
            }

        })
    }

    fun addComplaint(complaint: Complaint, onComplete: (Boolean) -> Unit){
        val user = auth.currentUser ?: return
        val userId = user.uid

        db.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userName = snapshot.child("username").getValue(String ::class.java) ?: "Unknown"
                val email = snapshot.child("email").getValue(String ::class.java) ?: "Unknown"
                val hostel = snapshot.child("hostel").getValue(String ::class.java) ?: ""

                val key = db.child("complaints").push().key ?: return
                val complaint = complaint.copy(
                    id = key,
                    userName = userName,
                    email = email,
                    hostel = hostel, //hostel of the complaint is same as the user adding it
                    userId = userId)
                db.child("complaints").child(key).setValue(complaint).addOnCompleteListener {task->
                    if (task.isSuccessful)
                        onComplete(true)
                    else
                        onComplete(false)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
    fun deleteComplaint(complaintId: String, onComplete: (Boolean) -> Unit) {
        db.child("complaints").child(complaintId).removeValue().addOnCompleteListener { task->
            if (task.isSuccessful)
                onComplete(true)
            else
                onComplete(false)
        }
    }

    fun upVoteComplaint(complaintId: String){
        val user = auth.currentUser ?: return
        val userId = user.uid

        db.child("complaints").child(complaintId).runTransaction(object : Transaction.Handler{
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val complaint = currentData.getValue(Complaint::class.java) ?: return Transaction.success(currentData)
                val voters = complaint.voters.toMutableMap()
               if (voters[userId] == true){
                   //User Already Upvoted,so remove upvote
                   complaint.upVotes -= 1
                   voters.remove(userId)
               }else{
                   //user upvoting, checking for downvote
                   if (voters[userId] == false) {
                       complaint.downVotes -= 1
                   }
                   complaint.upVotes += 1
                   voters[userId] = true
               }
                complaint.voters = voters
                currentData.value = complaint
                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                //Transaction completed
            }

        })
    }

    fun downVoteComplaint(complaintId: String){
        val user = auth.currentUser ?: return
        val userId = user.uid

        db.child("complaints").child(complaintId).runTransaction(object : Transaction.Handler{
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val complaint = currentData.getValue(Complaint::class.java) ?: return Transaction.success(currentData)
                val voters = complaint.voters.toMutableMap()
                if (voters[userId] == false){
                    //User Already downVoted,so remove downvote
                    complaint.downVotes -= 1
                    voters.remove(userId)
                }else{
                    //user downVoting, checking for upvote
                    if (voters[userId] == true) {
                        complaint.upVotes -= 1
                    }
                    complaint.downVotes += 1
                    voters[userId] = false
                }
                complaint.voters = voters
                currentData.value = complaint
                return Transaction.success(currentData)
            }

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?
            ) {
                //Transaction completed
            }

        })
    }

    fun loadSearchedComplaints(searchValue : String){

        if (searchValue.isNotEmpty()){
            val regex = Regex("\\b$searchValue", RegexOption.IGNORE_CASE)
            val filteredList = _complaints.value.filter { complaint ->
                regex.containsMatchIn(complaint.heading) || regex.containsMatchIn(complaint.content)
            }
            _searchMatchedComplaints.value = filteredList
        }else{
            _searchMatchedComplaints.value = _complaints.value
        }

    }

}