package xyz.c3rberuss.movies.image_view

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.igreenwood.loupe.Loupe
import xyz.c3rberuss.movies.R
import xyz.c3rberuss.movies.databinding.ActivityImageViewBinding

class ImageViewActivity : AppCompatActivity(R.layout.activity_image_view) {

    private val binding: ActivityImageViewBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Muestra el arrow back en el AppBar
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        Glide.with(this)
            .load(intent.getStringExtra("url"))
            .into(binding.imageview)


        // Hace que la imagen sea 'zoomeable'
        Loupe.create(binding.imageview, binding.container) { // imageView is your ImageView
            onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                override fun onStart(view: ImageView) {
                    //Do nothing
                }

                override fun onViewTranslate(view: ImageView, amount: Float) {
                    //Do nothing
                }

                override fun onRestore(view: ImageView) {
                    //Do nothing
                }

                override fun onDismiss(view: ImageView) {
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}