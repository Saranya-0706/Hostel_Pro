package com.example.hostEase.authentication.AuthValidation

import com.google.firebase.firestore.FirebaseFirestore

object hostelAuth {


    fun checkEmailAndHostel( email : String, onSuccess : ( Boolean, String?) -> Unit, onFailure : (String) -> Unit ){

        val db = FirebaseFirestore.getInstance()
        val hostels = listOf("hostelA", "hostelB")

        var found = false

        for (hostel in hostels){
            if (found)
                break

            //Check if email is in admin collection
            db.collection("hostels").document(hostel).collection("admins").document(email).get()
                .addOnSuccessListener { adminSnapshot ->
                    if (adminSnapshot.exists()) {
                        onSuccess(true, hostel)
                        found = true
                        return@addOnSuccessListener
                    }

                    //Check if email is in student collection
                    db.collection("hostels").document(hostel).collection("students").document(email)
                        .get()
                        .addOnSuccessListener { studentSnapshot ->
                            if (studentSnapshot.exists()) {
                                onSuccess(false, hostel)
                                found = true

                            } else if (hostel == hostels.last() && !found) {
                                onSuccess(false, null)
                            }
                        }.addOnFailureListener {
                            if (hostel == hostels.last()) {
                                onFailure(it.message.toString())
                            }
                        }
                }.addOnFailureListener {
                    if (hostel == hostels.last()) {
                        onFailure(it.message.toString())
                    }
                }
        }
    }
}