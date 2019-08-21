//package com.example.tasktrackerclient
//
//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.tasktrackerclient.database.TaskDatabase
//import com.example.tasktrackerclient.database.TaskDatabaseDao
//import junit.framework.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import androidx.test.platform.app.InstrumentationRegistry
//import org.junit.After
//import java.io.IOException
//
//@RunWith(AndroidJUnit4::class)
//class TaskDatabaseTest {
//
//    private lateinit var taskDao: TaskDatabaseDao
//    private lateinit var db: TaskDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
//            .allowMainThreadQueries()
//            .build()
//        taskDao = db.taskDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    fun testValidator() {
//        assertEquals(1,1)
//    }
//}