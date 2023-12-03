package com.example.myfirebase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class EditSaleItemActivity : AppCompatActivity() {
    private var adapter: MyAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore
    private val postCollectionRef = db.collection("post")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modify_post)
        val posts = mutableListOf<Item>()
        val subjectEditText: EditText = findViewById(R.id.subject)
        val valueEditText: EditText = findViewById(R.id.value)
        val isSaleEditText: EditText = findViewById(R.id.issale)
        val sendButton: Button = findViewById(R.id.send)
        // 이전 액티비티에서 전달된 데이터를 받아옴
        val itemId = intent.getStringExtra("ITEM_ID") ?: ""
        val originalSubject = intent.getStringExtra("ITEM_SUB") ?: ""
        val originalValue = intent.getStringExtra("ITEM_PRICE") ?: ""
        val originalIsSale = intent.getStringExtra("ITEM_SALE") ?: ""

        // 받아온 데이터로 EditText 초기화
        subjectEditText.setText(originalSubject)
        valueEditText.setText(originalValue)
        isSaleEditText.setText(originalIsSale)
        sendButton.setOnClickListener {
            // 수정된 내용을 가져오기
            val modifiedSubject = subjectEditText.text?.toString() ?: ""
            val modifiedValue = valueEditText.text?.toString() ?: ""
            val modifiedIsSale = isSaleEditText.text?.toString() ?: ""


            // 수정된 내용을 MainActivity로 전달
            val resultIntent = Intent()
            resultIntent.putExtra("ITEM_ID", itemId)
            resultIntent.putExtra("ITEM_SUB", modifiedSubject)
            resultIntent.putExtra("ITEM_PRICE", modifiedValue)
            resultIntent.putExtra("ITEM_SALE", modifiedIsSale)

            setResult(Activity.RESULT_OK, resultIntent)

            // Firestore에 수정된 내용 업데이트
            val updatedPost = mapOf(
                "subject" to modifiedSubject,
                "price" to modifiedValue,
                "sale" to modifiedIsSale,

                )

            // itemId에 해당하는 문서를 업데이트
            postCollectionRef.document(itemId)
                .update(updatedPost)
                .addOnSuccessListener {
                    // 업데이트 성공
                    Log.d(TAG, "DocumentSnapshot successfully updated!")

                    // 로컬 데이터 업데이트 및 어댑터에 알림
                    posts.find { it.id == itemId }?.apply {
                        "subject" to modifiedSubject
                        "price" to modifiedValue
                        "sale" to modifiedIsSale
                    }


                    finish()  // 현재 액티비티 종료
                }

            updateList()
            adapter?.updateList(posts)
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
    companion object {
        private const val TAG = "EditSaleItemActivity"
    }
}