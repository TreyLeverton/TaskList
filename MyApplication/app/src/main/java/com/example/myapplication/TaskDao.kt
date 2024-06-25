//TaskDao function
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myapplication.TaskDbHelper

class TaskDao(context: Context) {

    private val dbHelper = TaskDbHelper(context)

    //creates tasks
    fun addTask(task: Task) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskDbHelper.COLUMN_NAME, task.name)
            put(TaskDbHelper.COLUMN_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        db.insert(TaskDbHelper.TABLE_TASKS, null, values)
        db.close()
    }

    //saves tasks to SQLite
    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(TaskDbHelper.TABLE_TASKS, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(TaskDbHelper.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(TaskDbHelper.COLUMN_NAME))
                val isCompleted = getInt(getColumnIndexOrThrow(TaskDbHelper.COLUMN_COMPLETED)) == 1
                tasks.add(Task(id, name, isCompleted))
            }
        }
        cursor.close()
        db.close()
        return tasks
    }

    //updates tasks
    fun updateTask(task: Task) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskDbHelper.COLUMN_NAME, task.name)
            put(TaskDbHelper.COLUMN_COMPLETED, if (task.isCompleted) 1 else 0)
        }
        db.update(TaskDbHelper.TABLE_TASKS, values, "${TaskDbHelper.COLUMN_ID} = ?", arrayOf(task.id.toString()))
        db.close()
    }

    //allows deletion
    fun deleteTask(taskId: Int) {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        db.delete(TaskDbHelper.TABLE_TASKS, "${TaskDbHelper.COLUMN_ID} = ?", arrayOf(taskId.toString()))
        db.close()
    }
}
