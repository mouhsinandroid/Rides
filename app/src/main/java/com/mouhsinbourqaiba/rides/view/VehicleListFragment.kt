package com.mouhsinbourqaiba.rides.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mouhsinbourqaiba.rides.databinding.FragmentVehicleListBinding
import com.mouhsinbourqaiba.rides.utils.MyItemDecoration
import com.mouhsinbourqaiba.rides.viewmodel.VehicleListViewModel


class VehicleListFragment : Fragment() {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VehicleListViewModel by viewModels()
    private lateinit var adapter: VehicleListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = VehicleListAdapter { vehicle ->
            val action = VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(vehicle)
            findNavController().navigate(action)
        }
        binding.recyclerViewVehicles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewVehicles.addItemDecoration(MyItemDecoration())
        binding.recyclerViewVehicles.adapter = adapter
    }

    private fun setupListeners() {
        binding.buttonFetch.setOnClickListener {
            fetchVehiclesAndHideKeyboard()
        }

        binding.editTextCount.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                fetchVehiclesAndHideKeyboard()
                true
            } else {
                false
            }
        }

        binding.recyclerViewVehicles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })
    }

    private fun fetchVehiclesAndHideKeyboard() {
        hideKeyboard()
        fetchVehicles()
    }

    private fun fetchVehicles() {
        val count = binding.editTextCount.text.toString().toIntOrNull()
        if (count != null && count in 1..100) {
            viewModel.fetchVehicles(count)
        } else {
            binding.editTextCount.error = "Please enter a number between 1 and 100"
        }
    }

    private fun observeViewModel() {
        viewModel.vehicles.observe(viewLifecycleOwner) { vehicles ->
            adapter.submitList(vehicles)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}