package edu.put.kotlindetailslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.put.kotlindetailslist.database.Trail
import edu.put.kotlindetailslist.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val mainVm by activityViewModels<MainViewModel>()
    private var _binding: FragmentDetailBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindTrailData(mainVm.getTrail())

        val timerFragment = TimerFragment()
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.timerContainer, timerFragment)
        transaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun bindTrailData(trail: Trail?) {
        trail?:return

        binding.imageView.setImageResource(trail.image.toInt())
        binding.nameOfTrail.text = "Nazwa: ${trail.name}"
        binding.lengthOfTrail.text = "Długość: ${trail.length} km"
        binding.difficultyOfTrail.text = "Trudność: ${trail.difficulty}"
        binding.estimatedDurationOfTrail.text = "Przewyższenia: ${trail.estimatedDuration.toString()} m"
        binding.descriptionOfTrail.text = "Opis: ${trail.description}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}