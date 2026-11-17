package com.example.tlttd_lec8.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.tlttd_lec8.databinding.FragmentStudentDetailBinding
import com.example.tlttd_lec8.model.Student
import com.example.tlttd_lec8.viewmodel.StudentViewModel

class StudentDetailFragment : Fragment() {
    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, 
        container: ViewGroup?, 
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        viewModel.selectedStudent.observe(viewLifecycleOwner) { student ->
            student?.let { displayStudentDetail(it) }
        }

        setFragmentResultListener("STUDENT_DETAIL_REQ") { _, bundle ->
            val studentId = bundle.getString("STUDENT_ID")
            viewModel.students.value?.find { it.id == studentId }?.let { student ->
                displayStudentDetail(student)
            }
        }
    }

    private fun displayStudentDetail(student: Student) {
        binding.tvDetailName.text = student.name
        binding.tvDetailMssv.text = "MSSV: ${student.mssv}"
        binding.tvDetailMajor.text = "Chuyên ngành: ${student.major}"
        binding.tvDetailGpa.text = "GPA: ${student.gpa}"
        binding.tvDetailAvatar.text = student.name.take(1)
        binding.tvDetailAvatar.background?.setTint(student.avatarColor)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
