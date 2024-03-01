package com.jirap.checkinout.reqattendance

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jirap.checkinout.api.SessionManager
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse
import com.jirap.checkinout.api.model.LogInUserRequest
import com.jirap.checkinout.databinding.FragmentReqAttendanceBinding
import com.jirap.checkinout.login.LoginContractor
import com.jirap.checkinout.login.LoginPresenter
import java.util.Calendar

class ReqAttendanceFragment : Fragment(), ReqAttendanceContractor.View {

    private var operation = "SAVE"
    private var _binding: FragmentReqAttendanceBinding? = null
    lateinit var presenter: ReqAttendancePresenter
    private val binding get() = _binding!!
    private lateinit var btnSave: Button
    private lateinit var etDate: Button
    private lateinit var etTimeIn: Button
    private lateinit var etTimeOut: Button
    private lateinit var etReason: EditText

    var cal: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceViewModel =
            ViewModelProvider(this).get(ReqAttendanceViewModel::class.java)

        _binding = FragmentReqAttendanceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val sessionManager = SessionManager(requireContext())
        presenter = ReqAttendancePresenter(this)
        btnSave= binding.btnSave
        etReason = binding.reason
        etDate = binding.etDate
        var txDate=""
        var txMonth=""
        var txYear=""
        var txHourIn=""
        var txMinuteIn=""
        var txHourOut=""
        var txMinuteOut=""
        etDate.setOnClickListener{
            val dateStartSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                   var month = monthOfYear + 1
                    etDate.text = Editable.Factory.getInstance().newEditable("$dayOfMonth/$month/$year")
                    txDate = reformatInt(dayOfMonth)
                    txMonth = reformatInt(month)
                    txYear = year.toString()
                }

            DatePickerDialog(
                requireContext(),
                dateStartSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        etTimeIn = binding.etTimeIn
        etTimeIn.setOnClickListener{
            val timeStartSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                etTimeIn.text = Editable.Factory.getInstance().newEditable("$hourOfDay:$minute")
                txHourIn = reformatInt(hourOfDay)
                txMinuteIn = reformatInt(minute)
            }

            TimePickerDialog(
                requireContext(),
                timeStartSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        etTimeOut = binding.etTimeOut
        etTimeOut.setOnClickListener{
            val timeStartSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                etTimeOut.text = Editable.Factory.getInstance().newEditable("$hourOfDay:$minute")
                txHourOut = reformatInt(hourOfDay)
                txMinuteOut = reformatInt(minute)
            }

            TimePickerDialog(
                requireContext(),
                timeStartSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        btnSave.setOnClickListener {
            if (txYear != "" && txMonth != "" && txDate != "" && txHourIn !="" && txHourOut !="") {
                requestSave(
                    sessionManager.getUsername(),
                    "$txYear-$txMonth-$txDate"+"T00:00:00.000Z",
                    "$txYear-$txMonth-$txDate"+'T'+"$txHourIn:$txMinuteIn:00.000Z",
                    "$txYear-$txMonth-$txDate"+'T'+"$txHourOut:$txMinuteOut:00.000Z",
                    etReason.text.toString()
                )
            } else {
                Toast.makeText(requireActivity(), "Fail!! : กรุณากรอกข้อมูล", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    private fun reformatInt(data:Int):String{
        return if (data<=9){
            "0$data"
        } else {
            "$data"
        }
    }
    private fun requestSave(empNo:String,date:String,checkIn:String,checkOut:String,reason:String) {
        val attendanceRequest = RequestAttendanceRequest(
            operation = operation,
            empNo = empNo,
            date = date,
            checkIn = checkIn,
            checkOut = checkOut,
            reason = reason,
            month = ""
        )
        presenter.requestAttendance(requireActivity(),attendanceRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun success(attendanceResponse: RequestAttendanceResponse) {
        Toast.makeText(requireActivity(), "Update Success.", Toast.LENGTH_SHORT).show()
    }

    override fun fail(responseMessage: String?) {
        Toast.makeText(requireActivity(), "Fail!! : $responseMessage", Toast.LENGTH_SHORT).show()
    }
}