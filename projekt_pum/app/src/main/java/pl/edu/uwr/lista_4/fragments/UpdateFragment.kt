package pl.edu.uwr.projekt_pum.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.edu.uwr.lista_4.fragments.DatePickerFragment
import pl.edu.uwr.projekt_pum.R
//import pl.edu.uwr.projekt_pum.ItemViewModel
import pl.edu.uwr.projekt_pum.FoodProductsViewModel
import pl.edu.uwr.projekt_pum.databinding.FragmentUpdateBinding
import pl.edu.uwr.projekt_pum.model.Item
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding

    private val itemViewModel: FoodProductsViewModel by viewModels()
    private val itemId: Int by lazy { requireArguments().getInt("id") }
    private lateinit var pictureAbsolutePath: Uri
    private val addList by lazy { AddFragment() }

    private fun displayItem(item: Item) {
        binding.nameEditText.setText(item.name)
        binding.dateEditText.setText(item.date.toString())
        binding.imageViewFood.setImageURI(Uri.parse(item.image))
    }

    private fun updateItem() {
        val name = binding.nameEditText.text.toString()
        val date = binding.dateEditText.text.toString()
        val image = pictureAbsolutePath.toString()

        if (name.isNotEmpty() && date.isNotEmpty()) {
            val item = Item(itemId, name, date,image)
            itemViewModel.updateItem(item)
            findNavController()
                .navigate(UpdateFragmentDirections
                    .actionUpdateFragmentToListFragment())
        } else{
            binding.nameEditText.error = "Podaj nazwę"
            binding.dateEditText.error = "Podaj ilość"

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel.getItem(itemId)
            .observe(viewLifecycleOwner, this::displayItem)

        binding.updateButton.setOnClickListener { updateItem() }
        binding.imageViewFood.setOnClickListener{
            openCamera()
        }
        binding.dateEditText.setOnClickListener {
            // create new instance of DatePickerFragment
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding.dateEditText.setText(date)
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")

        }
    }

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            launchCamera()
        }
    }
    private val resultLauncherCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageViewFood.setImageBitmap(imageBitmap)
            pictureAbsolutePath = saveImage(imageBitmap) // zapis pliku oraz ścieżki
        }
    }
    fun openCamera(){
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED -> {
                launchCamera() // włączam aplikację przez implicit intent
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA) -> {
                showMessageOKCancel(getString(R.string.rationale_camera)) // Rationale
            }
            else -> {
                requestCameraPermissionLauncher
                    .launch(Manifest.permission.CAMERA) // jeżeli nie to nic nie robię
            }
        }
    }
    fun showMessageOKCancel(message: String) {
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("OK") { dialogInterface: DialogInterface, _: Int ->
                // jeżeli ok proszę o upoważnienie
                requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                dialogInterface.dismiss()
            }
            .setNegativeButton("Cancel", null) // jeżeli nie to nic nie robię
            .create()
            .show()
    }
    private fun launchCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncherCamera.launch(intent)
    }
    private fun checkForErrors(): Boolean{

        if (!this::pictureAbsolutePath.isInitialized)
            return true
        return false
    }
    private fun saveImage(bitmap: Bitmap): Uri {
        var file = requireContext().getDir("myGalleryKotlin", Context.MODE_PRIVATE)

        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }
}