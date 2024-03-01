package com.jirap.checkinout.attendance

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jirap.checkinout.R
import com.jirap.checkinout.api.model.AttendanceRequest
import com.jirap.checkinout.api.model.AttendanceResponse
import com.jirap.checkinout.databinding.FragmentAttendanceHistoryBinding
import java.text.SimpleDateFormat
import java.util.Locale


class AttendanceHistoryFragment : Fragment(), AttendanceContractor.View {

    private var _binding: FragmentAttendanceHistoryBinding? = null

    private val binding get() = _binding!!
    private var operation = "USER"
    lateinit var presenter: AttendancePresenter
    private lateinit var tableView: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceHistoryViewModel =
            ViewModelProvider(this).get(AttendanceHistoryViewModel::class.java)

        _binding = FragmentAttendanceHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        presenter = AttendancePresenter(this)
        tableView = binding.tableView
        val attendanceRequest = AttendanceRequest(
            operation = operation,
            checkType = "",
            latitudeOut = 0.0,
            longitudeOut = 0.0,
            latitudeIn = 0.0,
            longitudeIn = 0.0,
            ipAddress = "",
            typeJob = "",
            event = "",
            status = ""
        )
        presenter.attendance(requireContext(),attendanceRequest)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun success(attendanceResponse: AttendanceResponse) {
        val formatterOld =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        var formatterDate = SimpleDateFormat("dd/MM/yyyy")
        var formatterTime = SimpleDateFormat("HH:mm")

        for (item in attendanceResponse.data) {
            val tableRow = TableRow(requireContext())

            var textView = TextView(requireContext())
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            if (item.date == ""){
                textView.text = ""
            } else {
                val date = formatterOld.parse(item.date)
                textView.text = formatterDate.format(date)
            }
            textView.width = 90
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            if (item.checkIn == ""){
                textView.text = ""
            } else {
                val date = formatterOld.parse(item.checkIn)
                textView.text = formatterTime.format(date)
            }
            textView.width = 50
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            if (item.checkOut == ""){
                textView.text = ""
            } else {
                val date = formatterOld.parse(item.checkOut)
                textView.text = formatterTime.format(date)
            }
            textView.width = 50
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            textView.text = item.late
            textView.width = 60
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.text = item.event
            /*textView.layoutParams = TableLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,
                1f
            )*/
            textView.width = 80
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            tableRow.addView(textView)

            tableView.addView(tableRow)
        }
    }

    override fun fail(responseMessage: String?) {
        TODO("Not yet implemented")
    }
}