package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.memeshare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentimageUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
       binding = ActivityMainBinding.inflate(layoutInflater)
        val  view = binding.root
        setContentView(view)
        loadmeme()
        val next = binding.next
        val share = binding.share


      next.setOnClickListener{
          loadmeme()
      }
      share.setOnClickListener{
         val intent = Intent(Intent.ACTION_SEND)
          intent.setType("text/plain")
//          intent.setType("image/*")
          intent.putExtra(Intent.EXTRA_TEXT,"it's funny though $currentimageUrl")
          val chooser = Intent.createChooser(intent,"share this meme using.. ")
          startActivity(intent)
      }

    }
  private fun loadmeme(){
        //     using Volley library to send request for meme api
// Instantiate the RequestQueue.
      val progressBar = binding.progressBarID
     progressBar.visibility = View.VISIBLE


        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                currentimageUrl = response.getString("url")

                Glide.with(this).load(currentimageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                                return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(binding.imageview)
            },
            {

            })

// Add the request to the RequestQueue.
       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

}