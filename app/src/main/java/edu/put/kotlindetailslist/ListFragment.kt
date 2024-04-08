package edu.put.kotlindetailslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.put.kotlindetailslist.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentListBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TrailAdapter(trails = mainVm.loadDataFromDB(context), onTrailClick = { user->
            mainVm.setTrail(user)
            findNavController().navigate(R.id.action_listFragment_to_detailFragment)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter= adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}