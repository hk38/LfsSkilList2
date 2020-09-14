package lit.amida.lfsskillist2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var second = 10
    val timer:CountDownTimer = object :CountDownTimer(10000, 1000){
        override fun onTick(p0: Long) {
            second--
            textTime.text = second.toString()
        }

        override fun onFinish() {
            button.isVisible = true
            second = 10
            textTime.text = second.toString()
        }

    }

    val readRequestCode = 42
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //CountDownTimer
        textTime.text = second.toString()
        button.setOnClickListener {
            button.isVisible = false
            timer.start()
        }

        // Intent
        buttonIntent.setOnClickListener {
            val toSecondActivityIntent = Intent(this, SecondActivity::class.java)
            startActivity(toSecondActivityIntent)
        }

        buttonPlayStore.setOnClickListener {
            val playStoreIntent = Intent(Intent.ACTION_VIEW)
            playStoreIntent.data = Uri.parse("https://play.google.com/store/apps")
            playStoreIntent.setPackage("com.android.vending")
            startActivity(playStoreIntent)
        }

        buttonMap.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse("geo:35.6473,139.7360")
            if(mapIntent.resolveActivity(packageManager) != null){
                startActivity(mapIntent)
            }
        }

        buttonBrowser.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data =Uri.parse("https://life-is-tech.com/")
            if(browserIntent.resolveActivity(packageManager) != null){
                startActivity(browserIntent)
            }
        }

        buttonGallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, readRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == readRequestCode && resultCode == Activity.RESULT_OK){
            data?.data?.also {
                imageView.setImageURI(it)
            }
        }
    }
}