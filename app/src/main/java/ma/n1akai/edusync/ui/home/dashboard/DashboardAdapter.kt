package ma.n1akai.edusync.ui.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import ma.n1akai.edusync.R
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import kotlin.IllegalArgumentException

class DashboardAdapter : RecyclerView.Adapter<DashboardHolder>() {

    var items = listOf<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var context: Context
    lateinit var onHomeworkClickListener: OnHomeworkClickListener
    lateinit var onHomeworkCheckedChangedListener: OnHomeworkCheckedChangedListener
    lateinit var onMoreClickListener: OnMoreClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder {
        if (!this::context.isInitialized) {
            context = parent.context
        }
        return when (viewType) {
            R.layout.title_item -> DashboardHolder.TitleViewHolder(
                TitleItemBinding
                    .inflate(LayoutInflater.from(context), parent, false)
            )

            R.layout.notes_list_item -> {
                DashboardHolder.TestViewHolder(
                    NotesListItemBinding
                        .inflate(LayoutInflater.from(context), parent, false)
                )
            }

            R.layout.homeworks_list_item -> {
                DashboardHolder.HomeworkViewHolder(
                    HomeworksListItemBinding
                        .inflate(LayoutInflater.from(context), parent, false)
                )
            }

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DashboardHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is DashboardHolder.HomeworkViewHolder -> holder.bind(
                item as Homework,
                context,
                onHomeworkCheckedChangedListener,
                onHomeworkClickListener
            )

            is DashboardHolder.TestViewHolder -> holder.bind(item as Test, context)
            is DashboardHolder.TitleViewHolder -> holder.bind(item as Title, onMoreClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Title -> R.layout.title_item
            is Test -> R.layout.notes_list_item
            is Homework -> R.layout.homeworks_list_item
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    interface OnHomeworkClickListener {
        fun onHomeworkClick(homework: Homework, view: View)
    }

    interface OnHomeworkCheckedChangedListener {

        fun onHomeworkCheckedChanged(homework: Homework, view: View, checked: Boolean)

    }

    interface OnMoreClickListener {
        fun onMoreClick(navDirections: NavDirections, view: View)
    }
}