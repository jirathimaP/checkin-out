package com.jirap.checkinout.attendance

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse
import com.jirap.checkinout.databinding.FragmentAttendanceBinding

class AttendanceFragment : Fragment(), LocationListener, AttendanceContractor.View {

    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var statusApprove = "APPROVE"
    private var operation = "SAVE"
    private var checkTypeIn = "INSIDE-IN"
    private var checkTypeOut = "INSIDE-OUT"
    private var ip = ""
    private var event = "เข้างานปกติ"
    private var typeJob = "NORMAL"
    private var lat = 0.0
    private var long = 0.0
    private var _binding: FragmentAttendanceBinding? = null
    lateinit var presenter: AttendancePresenter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceViewModel =
            ViewModelProvider(this).get(AttendanceViewModel::class.java)

        _binding = FragmentAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (hasReadLocationPermission()){
            getLocation()
        } else {
            requestPermission()
        }
        presenter = AttendancePresenter(this)

        val btnCheckIn = binding.btnCheckIn
        val btnCheckOut = binding.btnCheckOut
        btnCheckIn.setOnClickListener {
            requestCheckIn()
        }
        btnCheckOut.setOnClickListener {
            requestCheckOut()
        }
        return root
    }
    private fun hasReadLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestCheckIn() {
        val attendanceRequest = AttendanceRequest(
            operation = operation,
            checkType = checkTypeIn,
            latitudeIn = lat,
            longitudeIn = long,
            latitudeOut = 0.0,
            longitudeOut = 0.0,
            ipAddress = ip,
            typeJob = typeJob,
            event = event,
            status = statusApprove
        )
        presenter.attendance(requireActivity(),attendanceRequest)
    }

    private fun requestCheckOut() {
        val attendanceRequest = AttendanceRequest(
            operation = operation,
            checkType = checkTypeOut,
            latitudeOut = lat,
            longitudeOut = long,
            latitudeIn = 0.0,
            longitudeIn = 0.0,
            ipAddress = ip,
            typeJob = typeJob,
            event = event,
            status = statusApprove
        )
        presenter.attendance(requireActivity(),attendanceRequest)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionCode
        )
    }
    private fun getLocation() {
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        long = location.longitude
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_SHORT).show()
                getLocation()
            } else {
                Toast.makeText(requireActivity(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun success(attendanceResponse: AttendanceResponse) {
        Toast.makeText(requireActivity(), "Check In Success.", Toast.LENGTH_SHORT).show()
    }

    override fun fail(responseMessage: String?) {
        Toast.makeText(requireActivity(), "Fail!! : $responseMessage", Toast.LENGTH_SHORT).show()
    }
}