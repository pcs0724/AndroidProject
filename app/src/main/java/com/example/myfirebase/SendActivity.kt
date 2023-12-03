package com.example.myfirebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SendActivity : AppCompatActivity() {
    private var adapter: MessageAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("Messages")
    private val recyclerViewItems by lazy { findViewById<RecyclerView>(R.id.recyclerViewItems2) }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_view)


        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        adapter = MessageAdapter(this, emptyList())
        recyclerViewItems.adapter = adapter
        //updateList()  // list items on recyclerview
        fitterList()


    }
    private fun updateList() {
        postCollectionRef.get().addOnSuccessListener {
            val posts = mutableListOf<Messages>()
            for (doc in it) {
                posts.add(Messages(doc))
            }
            adapter?.updateList(posts)
        }
    }
    private fun fitterList() {
        // item.sale이 true인 것들만 필터링하여 가져오기
        val currentUser = Firebase.auth.currentUser?.email

        postCollectionRef.whereEqualTo("recevid", currentUser)
            .get()
            .addOnSuccessListener { result ->
                val posts = mutableListOf<Messages>()
                for (document in result) {
                    posts.add(Messages(document))
                }
                adapter?.updateList(posts) // RecyclerView에 업데이트된 리스트 적용
            }
            .addOnFailureListener { exception ->
                // 실패할 경우 처리하는 코드
            }
    }
}
