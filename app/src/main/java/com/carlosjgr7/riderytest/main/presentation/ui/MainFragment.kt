package com.carlosjgr7.riderytest.main.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.carlosjgr7.riderytest.R
import com.carlosjgr7.riderytest.databinding.FragmentMainBinding
import com.carlosjgr7.riderytest.main.presentation.viewmodels.MainViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var map: GoogleMap
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupToolbar()
        setupNavigationDrawer()
        mGoogleSignInClient =
            GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        binding.toolbar.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun setupNavigationDrawer() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.closeSession -> {
                    val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
                    if (account != null) {
                        mGoogleSignInClient.signOut()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    viewModel.logoutWithGoogle()
                                } else {
                                    Toast.makeText(
                                        requireActivity(),
                                        "Ha ocurrido un error",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                    }
                }
            }
            true
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        job = lifecycleScope.launch {
            viewModel.sesion.collect {
                if (it) {
                    findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
                }

            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            showUserLocation()
        }
    }

    private fun showUserLocation() {
        try {
            map.isMyLocationEnabled = true
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    map.addMarker(MarkerOptions().position(userLocation).title("Tu Ubicación"))
                    map.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            userLocation,
                            15f
                        )
                    ) // Zoom atractivo
                } ?: run {
                    Toast.makeText(
                        requireContext(),
                        "No se pudo obtener la ubicación",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: SecurityException) {
            Toast.makeText(
                requireContext(),
                "Permisos de ubicación no concedidos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUserLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permisos de ubicación requeridos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}

