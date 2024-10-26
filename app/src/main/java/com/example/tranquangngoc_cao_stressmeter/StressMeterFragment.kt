package com.example.tranquangngoc_cao_stressmeter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import androidx.fragment.app.Fragment

class StressMeterFragment : Fragment() {

    private lateinit var imageGridView: GridView
    private lateinit var moreImagesButton: Button
    private lateinit var imageAdapter: ImageAdapter

    private var currentImageSet = 0
    private val imageSets = listOf(
        listOf(
            R.drawable.psm_anxious, R.drawable.psm_baby_sleeping, R.drawable.psm_bar, R.drawable.psm_barbed_wire2,
            R.drawable.psm_beach3, R.drawable.psm_bird3, R.drawable.psm_blue_drop, R.drawable.psm_cat,
            R.drawable.psm_clutter3, R.drawable.psm_dog_sleeping, R.drawable.psm_exam4, R.drawable.psm_gambling4,
            R.drawable.psm_headache2, R.drawable.psm_hiking3, R.drawable.psm_kettle, R.drawable.psm_lake3
        ),
        listOf(
            R.drawable.psm_lawn_chairs3, R.drawable.psm_lonely, R.drawable.psm_lonely2, R.drawable.psm_mountains11,
            R.drawable.psm_neutral_child, R.drawable.psm_neutral_person2, R.drawable.psm_peaceful_person, R.drawable.psm_puppy,
            R.drawable.psm_puppy3, R.drawable.psm_reading_in_bed2, R.drawable.psm_running3,R.drawable.psm_running4,
            R.drawable.psm_sticky_notes2, R.drawable.psm_stressed_cat, R.drawable.psm_stressed_person, R.drawable.psm_stressed_person12
        ),
        listOf(
            R.drawable.psm_stressed_person3, R.drawable.psm_stressed_person4, R.drawable.psm_stressed_person6, R.drawable.psm_stressed_person7,
            R.drawable.psm_stressed_person8, R.drawable.psm_talking_on_phone2, R.drawable.psm_to_do_list, R.drawable.psm_to_do_list3,
            R.drawable.psm_wine3, R.drawable.psm_work4, R.drawable.psm_yoga4, R.drawable.stress_image_1,
            R.drawable.stress_image_2, R.drawable.stress_image_3, R.drawable.stress_image_4, R.drawable.psm_clutter
        )
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stress_meter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageGridView = view.findViewById(R.id.imageGridView)
        moreImagesButton = view.findViewById(R.id.moreImagesButton)

        imageAdapter = ImageAdapter(requireContext(), imageSets[currentImageSet])
        imageGridView.adapter = imageAdapter

        // Set up click listener for grid items
        imageGridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(requireContext(), ImageSelectionActivity::class.java)
            intent.putExtra("IMAGE_RES_ID", imageSets[currentImageSet][position])
            startActivity(intent)
        }

        moreImagesButton.setOnClickListener {
            currentImageSet= (currentImageSet + 1) % imageSets.size
            imageAdapter.updateImages(imageSets[currentImageSet])
        }
    }

}