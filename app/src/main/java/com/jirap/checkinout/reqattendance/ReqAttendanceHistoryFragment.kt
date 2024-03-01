package com.jirap.checkinout.reqattendance

import android.app.AlertDialog
import android.os.Bundle
import android.text.format.DateUtils.getMonthString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jirap.checkinout.R
import com.jirap.checkinout.api.SessionManager
import com.jirap.checkinout.api.model.RequestAttendanceHistoryResponse
import com.jirap.checkinout.api.model.RequestAttendanceRequest
import com.jirap.checkinout.api.model.RequestAttendanceResponse
import com.jirap.checkinout.databinding.FragmentReqAttendanceHistoryBinding
import com.kal.rackmonthpicker.RackMonthPicker
import com.kal.rackmonthpicker.listener.DateMonthDialogListener
import com.kal.rackmonthpicker.listener.OnCancelMonthDialogListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class ReqAttendanceHistoryFragment : Fragment(), ReqAttendanceHistoryContractor.View {

    private var _binding: FragmentReqAttendanceHistoryBinding? = null

    private val binding get() = _binding!!
    private var operation = "LIST"
    lateinit var presenter: ReqAttendanceHistoryPresenter
    private lateinit var tableView: TableLayout
    private lateinit var btnDate: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val attendanceHistoryViewModel =
            ViewModelProvider(this).get(ReqAttendanceHistoryViewModel::class.java)

        _binding = FragmentReqAttendanceHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sessionManager = SessionManager(requireContext())
        presenter = ReqAttendanceHistoryPresenter(this)
        tableView = binding.tableView
        btnDate = binding.btnDate
        btnDate.setOnClickListener{
                RackMonthPicker(requireActivity())
                    .setLocale(Locale.ENGLISH)
                    .setPositiveButton(object : DateMonthDialogListener {
                        override fun onDateMonth(
                            month: Int,
                            startDate: Int,
                            endDate: Int,
                            year: Int,
                            monthLabel: String?
                        ) {
                            btnDate.text = monthLabel
                            var monthText = if (month<=9) "0$month" else "$month"
                            val attendanceRequest = RequestAttendanceRequest(
                                operation = operation,
                                empNo = sessionManager.getUsername(),
                                month = "$year-$monthText-01T00:00:00.000Z",
                                reason = "",
                                checkOut = "",
                                checkIn = "",
                                date = ""
                            )
                            presenter.requestAttendanceHistory(requireContext(),attendanceRequest)
                        }
                    })
                    .setNegativeButton(object : OnCancelMonthDialogListener {
                        override fun onCancel(dialog: androidx.appcompat.app.AlertDialog?) {
                            dialog?.dismiss()
                        }
                    }).show()
            }

        val attendanceRequest = RequestAttendanceRequest(
            operation = operation,
            empNo = sessionManager.getUsername(),
            month = "",
            reason = "",
            checkOut = "",
            checkIn = "",
            date = ""
        )
        presenter.requestAttendanceHistory(requireContext(),attendanceRequest)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun success(attendanceResponse: RequestAttendanceHistoryResponse) {
        val formatterOld =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        var formatterDate = SimpleDateFormat("dd/MM/yyyy")
        var formatterTime = SimpleDateFormat("HH:mm")
        if (tableView.childCount > 1){
            tableView.removeViews(1, tableView.childCount - 1)
        }
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
            textView.width = 80
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
            textView.width = 55
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
            textView.width = 60
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            textView.text = item.reason
            textView.width = 60
            tableRow.addView(textView)

            textView = TextView(requireContext())
            textView.text = item.status
            textView.width = 100
            textView.setPadding(10,5,5,5)
            textView.setBackgroundResource(R.drawable.border)
            tableRow.addView(textView)

            tableView.addView(tableRow)
        }
    }

    override fun fail(responseMessage: String?) {

    }
}