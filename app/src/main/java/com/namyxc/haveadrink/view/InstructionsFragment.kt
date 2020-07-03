package com.namyxc.haveadrink.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.namyxc.haveadrink.R
import kotlinx.android.synthetic.main.fragment_instructions.*

class InstructionsFragment: Fragment() {

    var instructionsText: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
    Bundle?): View? {
        instructionsText = arguments?.getString(BundleKeys.INSTRUCTIONS.toString())
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instructionsTextView.text = instructionsText
    }

    companion object {

        fun newInstance(instructions: String): InstructionsFragment {


            val args = Bundle()
            args.putString(BundleKeys.INSTRUCTIONS.toString(), instructions)
            val fragment = InstructionsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}