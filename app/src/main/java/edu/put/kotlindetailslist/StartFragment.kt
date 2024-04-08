package edu.put.kotlindetailslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import edu.put.kotlindetailslist.databinding.ActivityMainBinding
import edu.put.kotlindetailslist.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startText.setOnClickListener{
            findNavController().navigate(R.id.action_startFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}