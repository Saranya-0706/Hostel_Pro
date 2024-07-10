package com.example.hostEase.Screens.BottomNavScreens.ChatSection

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ChatRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    fun getMessages(hostel : String, userRole : String ): StateFlow<List<ChatMessage>>{

        val messagesFlow = MutableStateFlow<List<ChatMessage>>(emptyList())

        db.child("chatRooms").child(hostel).child("$userRole messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull {dataSnapshot ->
                    dataSnapshot.getValue(ChatMessage::class.java)?.copy(id = dataSnapshot.key ?: "")
                }
                messagesFlow.value = messages
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return messagesFlow
    }

    fun sendMessage(hostel: String,userRole: String, message: ChatMessage){

        val user = auth.currentUser ?: return
        val userId = user.uid

        db.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userName = snapshot.child("username").getValue(String ::class.java) ?: "Unknown"
                val email = snapshot.child("email").getValue(String ::class.java) ?: "Unknown"
                val role = snapshot.child("role").getValue(String ::class.java) ?: ""

                val messageId = db.child("chatRooms").child(hostel).child("$userRole messages").push().key ?: return
                val message = message.copy(
                    id = messageId,
                    senderId = userId,
                    senderEmail = email,
                    senderName = userName,
                    hostel = hostel,
                    senderRole = userRole
                )
                db.child("chatRooms").child(hostel).child("$userRole messages").child(messageId).setValue(message)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteMessage(userRole: String, hostel: String, messageId : String){
        db.child("chatRooms").child(hostel).child("$userRole messages").child(messageId).removeValue()
    }


}