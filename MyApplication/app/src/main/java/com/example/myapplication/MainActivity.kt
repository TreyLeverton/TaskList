//MainActivity.kt

//program must create a
//todo list and use SQLite
package com.example.myapplication

import Task
import TaskAdapter
import TaskDao
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

//main function
class MainActivity : AppCompatActivity() {

    //implementation of other functions
    private lateinit var taskDao: TaskDao
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskDao = TaskDao(this)
        taskList = taskDao.getAllTasks().toMutableList()

        //variables
        val editTextTask = findViewById<EditText>(R.id.editTextTask)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerViewTasks = findViewById<RecyclerView>(R.id.recyclerViewTasks)

        //implementation of recycler View
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList, object : TaskAdapter.OnTaskClickListener {
            override fun onTaskClick(task: Task) {
                taskDao.updateTask(task)
                refreshTaskList()
            }

            override fun onTaskLongClick(task: Task) {
                taskDao.deleteTask(task.id)
                refreshTaskList()
            }
        })
        recyclerViewTasks.adapter = taskAdapter

        //introduction of check button
        buttonAdd.setOnClickListener {
            val taskName = editTextTask.text.toString().trim()
            if (taskName.isNotEmpty()) {
                val newTask = Task(0, taskName, false)
                taskDao.addTask(newTask)
                editTextTask.text.clear()
                refreshTaskList()
            }
        }
    }

    private fun refreshTaskList() {
        taskList.clear()
        taskList.addAll(taskDao.getAllTasks())
        taskAdapter.notifyDataSetChanged()
    }
}

