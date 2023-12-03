package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Writer(
    val subject: String,
    val content: String,
    val recevid: String
)

class MessagesUpload : AppCompatActivity() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val MessagesCollectionRef = db.collection("Messages")

    private val MESSAGES_COLLECTION = "Messages"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.send)

        val subject = findViewById<TextView>(R.id.textView)
        val content = findViewById<TextView>(R.id.textView4)
        val button = findViewById<Button>(R.id.send)
        val recevid = findViewById<TextView>(R.id.textView2)

        recevid.text=intent.getStringExtra("POST_AUTHOR_ID")


        button.setOnClickListener {
            // Input validation
            val subjectText = subject.text.toString()

            val contentText = content.text.toString()
            val recevidText=recevid.text.toString()

            if (subjectText.isEmpty() || contentText.isEmpty() ) {
                Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new Messages
            val newMessages = Writer(
                subject = subjectText,
                content = contentText,
                recevid = recevidText

                )


            val newMessagesRef = MessagesCollectionRef.document()

            newMessagesRef.set(newMessages)
                .addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->

                    Log.e(TAG, "Error adding document", e)
                    Toast.makeText(
                        this,
                        getString(R.string.post_add_error, e.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    companion object {
        private const val TAG = "MessagesUpload"
    }
}