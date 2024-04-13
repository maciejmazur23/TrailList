package edu.put.kotlindetailslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import edu.put.kotlindetailslist.databinding.FragmentListBinding

class ListFragment : Fragment() {
    var difficulty = ""
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val difficulty = arguments?.getString("difficulty")
        val adapter = TrailAdapter(
            trails = mainVm.loadDataFromDB(context, difficulty.toString()),
            onTrailClick = { user ->
                mainVm.setTrail(user)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment)
            })

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_DIFFICULTY = "difficulty"

        @JvmStatic
        fun newInstance(difficulty: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DIFFICULTY, difficulty)
                }
            }
    }
}