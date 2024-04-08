package ma.n1akai.edusync.ui.home.dashboard

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ma.n1akai.edusync.data.models.Homework
import ma.n1akai.edusync.data.models.Test
import ma.n1akai.edusync.data.models.Title
import ma.n1akai.edusync.databinding.HomeworksListItemBinding
import ma.n1akai.edusync.databinding.NotesListItemBinding
import ma.n1akai.edusync.databinding.TitleItemBinding
import ma.n1akai.edusync.util.formatToRelativeTime
import ma.n1akai.edusync.util.snackbar

sealed class DashboardHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: TitleItemBinding) :
        DashboardHolder(binding) {
        fun bind(title: Title) {
            binding.let {
                it.title.text = title.title
                it.title.setCompoundDrawablesWithIntrinsicBounds(title.icon, 0, 0, 0)
            }
        }
    }

    class TestViewHolder(private val binding: NotesListItemBinding) :
        DashboardHolder(binding) {
        fun bind(test: Test) {
            binding.apply {
                tvLabel.text = test.course_name
                tvDate.text = formatToRelativeTime(test.created_at)
                tvNote.text = test.mark.toString()
                val concatModuleAndCc = "${test.course_code} - ${test.test_code}"
                tvModuleCc.text = concatModuleAndCc

                root.setOnLongClickListener {
                    val message =
                        "${test.course_code}: ${test.course_name} - ${test.test_code}: ${test.mark}"
                    it.snackbar(message)
                    true
                }
            }
        }
    }

    class HomeworkViewHolder(private val binding: HomeworksListItemBinding) :
        DashboardHolder(binding) {
        fun bind(hw: Homework) {
            binding.apply {
                homework.text = hw.homework
                val concatCourseAndDate =
                    "${hw.course_name} - ${formatToRelativeTime(hw.created_at)}"
                courseAndDate.text = concatCourseAndDate
                isDone.isChecked = hw.finished == 1
            }
        }
    }

}