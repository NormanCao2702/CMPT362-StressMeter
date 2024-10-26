package com.example.tranquangngoc_cao_stressmeter

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ResultsFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var tableLayout: TableLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.lineChart)
        tableLayout = view.findViewById(R.id.tableLayout)

        val stressEntries = CsvHelper.readStressResults(requireContext())
        setupChart(stressEntries)
        setupTable(stressEntries)
    }

    private fun setupChart(entries: List<CsvHelper.StressEntry>) {
        if (entries.isEmpty()) return

        val chartEntries = entries.mapIndexed { index, entry ->
            Entry(index.toFloat(), entry.stressScore.toFloat())
        }

        val dataSet = LineDataSet(chartEntries, "Stress Level")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawFilled(true)
        dataSet.mode = LineDataSet.Mode.LINEAR

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.setTouchEnabled(false)
        lineChart.setDrawGridBackground(false)
        lineChart.setDrawBorders(false)

        // X-axis setup
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = entries.size.toFloat()
        xAxis.granularity = 1f
        xAxis.labelCount = entries.size
        xAxis.valueFormatter = IndexAxisValueFormatter(entries.indices.map { (it).toString() })

        // Y-axis setup
        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(true)
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 16f
        yAxis.labelCount = 5
        yAxis.setDrawZeroLine(true)

        lineChart.axisRight.isEnabled = false

        lineChart.animateX(1000)

        lineChart.invalidate()
    }

    private fun setupTable(entries: List<CsvHelper.StressEntry>) {
        // Add table headers
        val headerRow = TableRow(context)
        headerRow.addView(createTextView("Time", true))
        headerRow.addView(createTextView("Stress", true))
        tableLayout.addView(headerRow)

        for (entry in entries) {
            addTableRow(formatTimestamp(entry.timestamp), entry.stressScore.toString())
        }
    }

    private fun formatTimestamp(timestamp: Long): String {
        return timestamp.toString()
    }

    private fun createTextView(text: String, isHeader: Boolean = false): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.gravity = Gravity.CENTER
        textView.setPadding(16, 16, 16, 16)

        if (isHeader) {
            textView.setTypeface(null, Typeface.BOLD)
            textView.background = ContextCompat.getDrawable(requireContext(), R.drawable.table_header_border)
        }
        else textView.background = ContextCompat.getDrawable(requireContext(), R.drawable.table_row_border)

        return textView
    }


    private fun addTableRow(time: String, stress: String) {
        val row = TableRow(context)
        row.addView(createTextView(time))
        row.addView(createTextView(stress))

        tableLayout.addView(row)
    }

}