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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.edu.uwr.lista_4.fragments.DatePickerFragment
import pl.edu.uwr.projekt_pum.ItemViewModel
import pl.edu.uwr.projekt_pum.R
import pl.edu.uwr.projekt_pum.RestCountriesViewModel
import pl.edu.uwr.projekt_pum.databinding.FragmentAddBinding
import pl.edu.uwr.projekt_pum.model.Item
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddFragment: Fragment() {
    private lateinit var binding: FragmentAddBinding

    private val itemViewModel: RestCountriesViewModel by viewModels()
    private lateinit var pictureAbsolutePath: Uri
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
    val current = LocalDateTime.now().format(formatter)


    private fun insertToDatabase() {
        val name = binding.nameEditText.text.toString()
        var date = binding.dateEditText.text.toString()
        val image = pictureAbsolutePath.toString()
        val today = Calendar.getInstance()
       // date = year.toString() + "-" + month.toString() + "-" + day.toString()
//        val date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
//        if(current == date)
//            println(current)
        if (name.isNotEmpty() && date.isNotEmpty()) {
            val item = Item(0, name, date,image)
            itemViewModel.addItem(item)

            findNavController()
                .navigate(AddFragmentDirections.actionAddFragmentToListFragment())
        } else {
            binding.nameEditText.error = "Podaj nazwę"
            binding.dateEditText.error = "Podaj ilość"
//            binding.imageViewFood.setImageURI(Uri.parse(image))
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {insertToDatabase()}
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