package com.mouhsinbourqaiba.rides.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VehicleDetailsViewModelTest {

    private lateinit var viewModel: VehicleDetailsViewModel

    @Before
    fun setUp() {
        viewModel = VehicleDetailsViewModel()
    }

    @Test
    fun `estimate Carbone Emissions with Kilometrage Equal To 5000`() {
        val kilometrage = 5000
        val expectedEmissions = 1.0 * kilometrage
        val actualEmissions = viewModel.estimateCarboneEmissions(kilometrage)
        assertEquals(expectedEmissions, actualEmissions, 0.0)
    }

    @Test
    fun `estimate Carbone Emissions with Kilometrage Greater Than 5000`() {
        val kilometrage = 6000
        val baseEmissions = 1.0 * 5000
        val additionalEmissions = 1.5 * (kilometrage - 5000)
        val expectedEmissions = baseEmissions + additionalEmissions
        val actualEmissions = viewModel.estimateCarboneEmissions(kilometrage)
        assertEquals(expectedEmissions, actualEmissions, 0.0)
    }

    @Test
    fun `estimate Carbone Emissions with Kilometrage Less Than 5000`() {
        val kilometrage = 4000
        val expectedEmissions = 1.0 * kilometrage
        val actualEmissions = viewModel.estimateCarboneEmissions(kilometrage)
        assertEquals(expectedEmissions, actualEmissions, 0.0)
    }
}
