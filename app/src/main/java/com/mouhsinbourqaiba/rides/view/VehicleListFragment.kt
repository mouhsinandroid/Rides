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
import com.mouhsinbourqaiba.rides.validation.ValidationResult
import com.mouhsinbourqaiba.rides.validation.VehicleCountValidator
import com.mouhsinbourqaiba.rides.viewmodel.VehicleListViewModel

class VehicleListFragment : Fragment() {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is null")


    private val viewModel: VehicleListViewModel by viewModels()
    private lateinit var adapter: VehicleListAdapter
    private val validator = VehicleCountValidator()
    private val swipeRefreshLayout by lazy { binding.swipeRefreshLayout }
    companion object {
        private const val DEFAULT_VEHICLE_COUNT = 10
    }

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

    private fun createVehicleListAdapter() = VehicleListAdapter { vehicle ->
        val action = VehicleListFragmentDirections.actionVehicleListFragmentToVehicleDetailsFragment(vehicle)
        findNavController().navigate(action)
    }
    private fun setupRecyclerView() {
        adapter = createVehicleListAdapter()
        binding.recyclerViewVehicles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MyItemDecoration())
            adapter = this@VehicleListFragment.adapter
        }
    }

    private fun setupListeners() {

        swipeRefreshLayout.setOnRefreshListener {
            refreshVehicles()
            swipeRefreshLayout.isRefreshing = false
        }

        binding.buttonFetch.setOnClickListener {
            fetchVehiclesAndHideKeyboard()
        }

        binding.editTextCount.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
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
        val input = binding.editTextCount.text.toString()
        val result = validator.validate(input)

        if (result is ValidationResult.Valid) {
            fetchValidatedVehicles(result.count)
        } else if (result is ValidationResult.Invalid) {
            showError(result.errorMessage)
        }
    }

    private fun fetchValidatedVehicles(count: Int) {
        viewModel.fetchVehicles(count)
    }

    private fun showError(message: String) {
        binding.editTextCount.error = message
    }

    private fun refreshVehicles() {
        val itemCount = adapter.itemCount.takeIf { it > 0 } ?: DEFAULT_VEHICLE_COUNT
        viewModel.fetchVehicles(itemCount)
    }

    private fun observeViewModel() {
        observeVehicles()
        observeLoading()
        observeError()
    }

    private fun observeVehicles() {
        viewModel.vehicles.observe(viewLifecycleOwner) { vehicles ->
            adapter.submitList(vehicles)
        }
    }
    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }
    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let { displayError(it) }
        }
    }
    private fun displayError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
