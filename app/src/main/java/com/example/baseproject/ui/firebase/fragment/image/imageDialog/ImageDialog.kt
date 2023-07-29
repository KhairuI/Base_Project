package com.example.baseproject.ui.firebase.fragment.image.imageDialog

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseproject.R
import com.example.baseproject.databinding.DialogImageBinding
import com.example.baseproject.ui.base.BaseDialogFragment
import com.example.baseproject.ui.firebase.fragment.image.model.ModelImage
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.camera
import com.example.baseproject.utils.extension.error
import com.example.baseproject.utils.extension.gallery
import com.example.baseproject.utils.extension.loading
import com.example.baseproject.utils.extension.safeLet
import com.example.baseproject.utils.extension.success
import com.github.drjacky.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageDialog : BaseDialogFragment() {

    private val viewModel by viewModels<ImageDialogViewModel>()

    private var _binding: DialogImageBinding? = null
    private val binding get() = _binding!!

    private var uri: Uri? = null
    private var name: String? = null

    private var success: (() -> Unit)? = null

    companion object {
        const val TAG: String = "ImageDialog"
        fun newInstance(): ImageDialog = ImageDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogImageBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun setup(view: View) {

        binding.addImage.setOnClickListener { pickImage() }

        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvSave.setOnClickListener {
            if (isValidate()) {
                safeLet(name, uri) { name, uri ->
                    viewModel.image(name, uri)
                }

            }
        }

    }

    private fun pickImage() {
        val popup = PopupMenu(binding.addImage.context, binding.addImage)
        popup.inflate(R.menu.menu_upload)
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.action_browse_gallery -> gallery()
                R.id.action_take_photo -> camera()
            }
            true
        }
        popup.show()
    }

    private fun camera() {
        pickerLauncher.launch(requireActivity().camera())
    }

    private fun gallery() {
        pickerLauncher.launch(requireActivity().gallery())
    }

    private val pickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                Activity.RESULT_OK -> {
                    it.data?.data?.let { fileUri ->
                        binding.ivPreviewPhoto.setImageURI(fileUri)
                        binding.tvName.text = fileUri.toFile().name
                        uri = fileUri
                        name = fileUri.toFile().name
                    }
                }

                ImagePicker.RESULT_ERROR ->
                    error(UiText.DynamicString(ImagePicker.getError(it.data)))

                else -> error(UiText.StringResource(R.string.task_canceled))
            }
        }

    override fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.imageResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                loading(result.isLoading)
                            }

                            is Result.Success -> {
                                /*success(UiText.DynamicString(result.data))
                                success?.invoke().also { dismiss() }*/
                                Log.d("xxx", "url: ${result.data}")
                                viewModel.saveUrl(name!!, result.data)
                            }
                        }
                    }
                }

                launch {
                    viewModel.saveUrlResponse.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                error(result.uiText)
                            }

                            is Result.Loading -> {
                                //loading(result.isLoading)
                            }

                            is Result.Success -> {
                                success(UiText.DynamicString(result.data))
                                success?.invoke().also { dismiss() }
                            }
                        }
                    }
                }
            }
        }
    }

    fun success(success: () -> Unit) {
        this.success = success
    }

    private fun isValidate(): Boolean {
        if (uri == null) {
            error(UiText.StringResource(R.string.image_missing))
            return false
        }
        return true
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}