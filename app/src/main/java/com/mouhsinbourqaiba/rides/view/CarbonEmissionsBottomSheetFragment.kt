package com.mouhsinbourqaiba.rides.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mouhsinbourqaiba.rides.R
import com.mouhsinbourqaiba.rides.databinding.FragmentCarbonEmissionsBottomSheetBinding

class CarbonEmissionsBottomSheetFragment(private val emissions: String) : BottomSheetDialogFragment() {

    private var _binding: FragmentCarbonEmissionsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCarbonEmissionsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.carbonEmissionsTextView.text = getString(R.string.estimated_carbon_emissions, emissions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        val TAG = CarbonEmissionsBottomSheetFragment.javaClass.simpleName
    }
}