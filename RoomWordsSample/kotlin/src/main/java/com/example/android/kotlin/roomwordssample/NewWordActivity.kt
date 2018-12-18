package com.example.android.kotlin.roomwordssample

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



/**
 * Activity for entering a word.
 */

class NewWordActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_REPLY = "com.example.android.roomwordssample.REPLY"
    }

    private val mEditWordView by lazy { findViewById<EditText>(R.id.edit_word) }

    private val button by lazy { findViewById<Button>(R.id.button_save) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)

        button.setOnClickListener {  val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditWordView!!.text)) {
                setResult(RESULT_CANCELED, replyIntent)
            } else {
                val word = mEditWordView!!.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(RESULT_OK, replyIntent)
            }
            finish() }

    }

}

