package ru.success.road_to_success.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import ru.success.road_to_success.databinding.FragmentDocsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DocsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DocsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDocsBinding
    private lateinit var toggleButtons: List<ToggleButton>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        toggleButtons = listOf(
            binding.docsButton1,
            binding.docsButton2,
            binding.docsButton3,
            binding.docsButton4,
            binding.docsButton5,
            binding.docsButton6,
            binding.docsButton7,
            binding.docsButton8
        )

        toggleButtons.forEach { button ->
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    uncheckOtherButtons(button)
                }
            }
        }
    }

    private fun uncheckOtherButtons(checkedButton: ToggleButton) {
        toggleButtons.forEach { button ->
            if (button != checkedButton) {
                button.isChecked = false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = DocsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}