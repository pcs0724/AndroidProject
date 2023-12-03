package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*class Messages : AppCompatActivity() {
    private var adapter: MessageAdater? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("Messages")
    private val recyclerViewItems by lazy { findViewById<RecyclerView>(R.id.recyclerViewItems2) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_view)

        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdater(this, emptyList())
        recyclerViewItems.adapter = adapter
        updateList()  // list items on recyclerview


        //여기부터 수정
        val subjectId=intent.getStringExtra("SUBJECT")
        val senderId=intent.getStringExtra("SENDER_ID")
        val contentId=intent.getStringExtra("CONTENT_ID")

        val subject=findViewById<TextView>(R.id.textView)
        val sender=findViewById<TextView>(R.id.textView2)
        val content=findViewById<TextView>(R.id.textView4)


        subject.text = "$subjectId"
        sender.text = "$senderId"
        content.text="$contentId"


        val button=findViewById<Button>(R.id.send)
        button.setOnClickListener {
            val sendIntent = Intent(this, Messages::class.java)
            //  글 작성자의 아이디와 내용 제목을 Messages로 전달
            sendIntent.putExtra("SUBJECT", subjectId)
            sendIntent.putExtra("SENDER_ID", senderId)
            sendIntent.putExtra("CONTENT_ID", contentId)
            startActivity(sendIntent)
            finish()
        }

    }
    private fun updateList() {
        postCollectionRef.get().addOnSuccessListener {
            val posts = mutableListOf<Item>()
            for (doc in it) {
                posts.add(Item(doc))
            }
            adapter?.updateList(posts)
        }
    }
} */