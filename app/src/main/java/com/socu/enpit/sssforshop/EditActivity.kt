package com.socu.enpit.sssforshop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_edit.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class EditActivity : AppCompatActivity() {

    private val nadapter =  NewsAdapter(ArrayList(), this)
    private val madapter = MenuAdapter(ArrayList(), this)
    private lateinit var addse: SoundPool
    private var soundResId = 0
    var setImageNum = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setTitle(R.string.title_bar_edit)

        CloudDataManager.getShopImage {bmp -> shopImage.setImageBitmap(bmp)}

        findViewById<View>(R.id.editImageButton).setOnClickListener {
            setImageNum = 1
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
        }

        findViewById<View>(R.id.setMenuImageButton).setOnClickListener {
            setImageNum = 2
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, RESULT_PICK_IMAGEFILE)
        }

        shopNameText.text = CloudDataManager.getShopName()

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = nadapter
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = madapter

        nadapter.removeAllItems()
        val nlist = CloudDataManager.getNewsDataList()
        nadapter.addItemList(nlist)

        madapter.removeAllItems()
        val mlist = CloudDataManager.getMenuDataList()
        madapter.addItemList(mlist)

        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }

        editNewsButton.setOnClickListener {
            addse.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f)
            if(newsContentsEditText != null && newsTitleEditText != null){
                val item = NewsData( newsTitleEditText.text.toString(), newsContentsEditText.text.toString(), DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString())
                nadapter.addItem(item)
                newsTitleEditText.text.clear()
                newsContentsEditText.text.clear()
                CloudDataManager.addNewsData(item)
            }
        }

        editMenuButton.setOnClickListener {
            addse.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f)
            if(menuContentsEditText != null && menuTitleEditText != null){
                val item = MenuData(menuTitleEditText.text.toString(), menuContentsEditText.text.toString(), DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString())
                madapter.addItem(item)
                menuTitleEditText.text.clear()
                menuContentsEditText.text.clear()
                CloudDataManager.addMenuData(item)
            }
        }
    }

    //画像入れ替え
    @SuppressLint("MissingSuperCall")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            var uri: Uri? = null
            if (resultData != null && setImageNum == 1) {
                uri = resultData.data

                try {
                    val bmp = getBitmapFromUri(uri)
                    shopImage.setImageBitmap(bmp)
                    CloudDataManager.setShopImage(bmp)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }else if (resultData != null && setImageNum == 2) {
                uri = resultData.data

                try {
                    val bmp = getBitmapFromUri(uri)
                    menuImage.setImageBitmap(bmp)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri?): Bitmap {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!, "r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    companion object {
        private val RESULT_PICK_IMAGEFILE = 1000
    }

    //ボタンSE
    override fun onResume() {
        super.onResume()
        addse = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        soundResId = addse.load(this, R.raw.add_se, 1)
    }

    override fun onPause() {
        super.onPause()
        addse.release()
    }

    // メニューを表示させる処理
    // この関数をオーバーライドして「menu.xml」を指定することで表示される
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // メニューを選択したときの動作をここに書く
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        when (itemId) {
            // メニューの「ログアウト」を押したとき
            R.id.menu_logout -> {
                // MainActivity（ログイン画面）に遷移する
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // キーボード隠すやつ
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editlayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        return super.dispatchTouchEvent(ev)
    }
}
