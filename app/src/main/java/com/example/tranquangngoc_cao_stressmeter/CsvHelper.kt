package com.example.tranquangngoc_cao_stressmeter
import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader

object CsvHelper {

    private const val FILE_NAME = "stress_results.csv"
    data class StressEntry(val timestamp: Long, val stressScore: Int)

    fun saveStressResult(context: Context, stressScore: Int) {
        val timestamp = System.currentTimeMillis() / 1000 // Unix timestamp in seconds
        val file = File(context.filesDir, FILE_NAME)

        val fileExists = file.exists()

        FileWriter(file, true).use { writer ->
            if (!fileExists) {
                writer.append("Time,Stress\n")
            }
            writer.append("$timestamp,$stressScore\n")
        }
    }

    fun readStressResults(context: Context): List<StressEntry> {
        val entries = mutableListOf<StressEntry>()
        try {
            context.openFileInput(FILE_NAME).use { fileInputStream ->
                BufferedReader(InputStreamReader(fileInputStream)).use { reader ->
                    // Skip the header line
                    reader.readLine()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        val parts = line?.split(",")
                        if (parts != null && parts.size == 2) {
                            val timestamp = parts[0].toLongOrNull()
                            val stressScore = parts[1].toIntOrNull()
                            if (timestamp != null && stressScore != null) {
                                entries.add(StressEntry(timestamp, stressScore))
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return entries
    }

}