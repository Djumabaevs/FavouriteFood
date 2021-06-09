package com.bignerdranch.android.favouritefood.view.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bignerdranch.android.favouritefood.R
import com.bignerdranch.android.favouritefood.databinding.ActivityAddUpdateDishBinding
import com.bignerdranch.android.favouritefood.databinding.DialogCustomImageSelectionBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import java.util.jar.Manifest

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityAddUpdateDishBinding
    private var mImagePath: String = ""

    private val contractCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if(result?.resultCode == Activity.RESULT_OK) {
            result.data?.extras?.let {
                val thumbnail: Bitmap = result.data!!.extras!!.get("data") as Bitmap
              /*  mBinding.ivDishImage
                    .setImageBitmap(thumbnail)*/
                Glide
                    .with(this)
                    .load(thumbnail)
                    .centerCrop()
                    .into(mBinding.ivDishImage)

                mImagePath = saveImageToInternalStorage(thumbnail)
                Log.i("imagePath", mImagePath)

                mBinding.ivAddDishImage
                    .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))
            }
        }
    }

    private val contractGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult? ->
        if(result?.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val selectedPhotoUri = result.data!!.data
                /*mBinding.ivDishImage
                    .setImageURI(selectedPhotoUri)*/
                Glide
                    .with(this)
                    .load(selectedPhotoUri)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("Tag","Error loading image",e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.let {
                                val bitmap: Bitmap = resource.toBitmap()
                                mImagePath = saveImageToInternalStorage(bitmap)
                                return false
                            }
                        }

                    })
                    .into(mBinding.ivDishImage)

                mBinding.ivAddDishImage
                    .setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpActionBar()

        mBinding.ivAddDishImage.setOnClickListener(this)
    }
    private fun setUpActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if(v != null) {
            when(v.id) {
                R.id.iv_add_dish_image -> {
                    customImageSelectionDialog()
                    return
                }
            }
        }
    }



    private fun customImageSelectionDialog() {
        val dialog = Dialog(this)
        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {

            Dexter.withContext(this@AddUpdateDishActivity).withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(object: MultiplePermissionsListener {

                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if(report.areAllPermissionsGranted()) {
                         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            contractCamera.launch(intent)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }

            ).onSameThread().check()

            dialog.dismiss()

        }


        binding.tvGallery.setOnClickListener {

            Dexter.withContext(this@AddUpdateDishActivity).withPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    contractGallery.launch(intent)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@AddUpdateDishActivity,
                        "You denied Gallery permission to select an image.", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }


            }

            ).onSameThread().check()

            dialog.dismiss()
        }

        dialog.show()
    }



    private fun showRationalDialogForPermissions() {
        AlertDialog
            .Builder(this)
            .setMessage("It looks like you have turned off permissions" +
        " required for this feature. It can be enabled under Application settings")
            .setPositiveButton("Go to settings") {
                _,_ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()

                 }
            }
            .setNegativeButton("Cancel") {
                dialog,_ ->
                dialog.dismiss()
            }.show()

    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) : String {
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir(IMAGES_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

    companion object {
        private const val IMAGES_DIRECTORY = "FavDishImages"
    }

}