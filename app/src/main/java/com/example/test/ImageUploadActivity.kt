package com.example.test

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapter.ImageAdapter
import com.example.test.databinding.ActivityImageUploadBinding
import com.example.test.model.viewmodel.ImageViewModel
import java.io.File

class ImageUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageUploadBinding
    private lateinit var viewModel: ImageViewModel
    private val imageDataUris = mutableListOf<String>()
    private val selectedImageUris = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        binding.btnSelectImages.setOnClickListener {
            if(imageDataUris.size>4){
                Toast.makeText(this,"You Already selected 5 pic",Toast.LENGTH_SHORT).show()
            }else {
                openGalleryForImages()
            }
        }

        binding.btnUploadImages.setOnClickListener {
            try {
                val filePaths = viewModel.imageUris.value ?: emptyList()
                val description = "Images upload description"
                viewModel.uploadImages(filePaths, description)
            }catch (e:Exception){}
        }

        viewModel.imageUris.observe(this, Observer { filePaths ->

            binding.recyclerViewImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewImages.adapter = ImageAdapter(imageDataUris,this)
        })

        viewModel.uploadStatus.observe(this, Observer { response ->

        })
    }



    val REQUEST_CODE = 200


    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , REQUEST_CODE
            )
        }
        else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

            // if multiple images are selected
            if (data?.getClipData() != null) {
                var count = data.clipData?.itemCount

                for (i in 0..count!! - 1) {
                    if (count>4){
                        Toast.makeText(this,"You select more than 5 pic",Toast.LENGTH_SHORT).show()
                    }else {
                        var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                        val uriString: String = imageUri.toString()
                        imageDataUris.add(uriString)

                    }


                }
                viewModel.setImageUris(imageDataUris)

            } else if (data?.getData() != null) {
                // if single image is selected

                var imageUri: Uri = data.data!!
                val uriString: String = imageUri.toString()

                imageDataUris.add(uriString)
                viewModel.setImageUris(imageDataUris)


            }
        }
    }
}