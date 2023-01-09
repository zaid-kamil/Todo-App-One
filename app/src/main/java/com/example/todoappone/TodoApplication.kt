package com.example.todoappone

import android.app.Application
import com.example.todoappone.data.MyDatabase

class TodoApplication : Application() {
    val database: MyDatabase by lazy { MyDatabase.getDatabase(this) }
}