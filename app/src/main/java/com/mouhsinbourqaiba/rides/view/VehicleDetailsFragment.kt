package com.mouhsinbourqaiba.rides.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mouhsinbourqaiba.rides.R
import com.mouhsinbourqaiba.rides.databinding.FragmentVehicleDetailsBinding
import com.mouhsinbourqaiba.rides.viewmodel.VehicleDetailsViewModel



class VehicleDetailsFragment : Fragment() {

    private var _binding: FragmentVehicleDetailsBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is null")


    private val args: VehicleDetailsFragmentArgs by navArgs()
    private val viewModel: VehicleDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        args.vehicle?.let { vehicle ->
            viewModel.setVehicle(vehicle)
            viewModel.kilometrage = vehicle.kilometrage

            binding.carbonEmissions.setOnClickListener {
                val emissions: String =
                    viewModel.estimateCarboneEmissions(viewModel.kilometrage).toString()
                val emissionTextViewValue = getString(R.string.emission_unit, emissions)
                val modalBottomSheet = CarbonEmissionsBottomSheetFragment(emissionTextViewValue)

                activity?.let { activity ->
                    modalBottomSheet.show(
                        activity.supportFragmentManager,
                        CarbonEmissionsBottomSheetFragment.TAG
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
