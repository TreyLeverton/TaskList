//task adapter creation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class TaskAdapter(private val taskList: List<Task>, private val listener: OnTaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnTaskClickListener {
        fun onTaskClick(task: Task)
        fun onTaskLongClick(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position], listener)
    }

    override fun getItemCount(): Int = taskList.size

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskName: TextView = itemView.findViewById(R.id.textViewTaskName)
        private val taskCompleted: CheckBox = itemView.findViewById(R.id.checkBoxTaskCompleted)

        fun bind(task: Task, listener: OnTaskClickListener) {
            taskName.text = task.name
            taskCompleted.isChecked = task.isCompleted

            itemView.setOnClickListener {
                listener.onTaskClick(task)
            }

            itemView.setOnLongClickListener {
                listener.onTaskLongClick(task)
                true
            }

            taskCompleted.setOnClickListener {
                task.isCompleted = taskCompleted.isChecked
                listener.onTaskClick(task)
            }
        }
    }
}
