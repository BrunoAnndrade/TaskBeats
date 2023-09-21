package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.Task
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.RetrofitModule
import okhttp3.Callback

/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    // layout pra colocar imagem de vazio, quando não tiver tarefas
    private lateinit var ctnContent: LinearLayout


    //colocando a função de abrir o detalhe da task no adapter
    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter(::openTaskListDetail)
    }

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recuperando layout
        ctnContent = view.findViewById(R.id.ctn_content)

        //recyclerview
        val taskList: RecyclerView = view.findViewById(R.id.RecycleView_task_List)
        //atrelando xml recycleView + adapter
        taskList.adapter = adapter



    }

    override fun onStart() {
        super.onStart()
        listFromDateBase()
    }

    private fun listFromDateBase() {
        //Observer
        val listObserver = Observer<List<Task>> { listTasks ->
            if (listTasks.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            } else {
                ctnContent.visibility = View.GONE
            }
            adapter.submitList(listTasks)
        }
        //Live Data
        //toda vez que tiver uma alteração na lista , ele vai observar e alterar
        viewModel.taskListLiveData.observe(this, listObserver)
    }

    // clicar em criar nova tarefa
    private fun openTaskListDetail(task: Task) {
        val intent = TaskDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment TaskListFragment.
         */

        @JvmStatic
        fun newInstance() = TaskListFragment()
    }
}