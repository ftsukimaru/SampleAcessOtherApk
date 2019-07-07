package com.aishihai.acess

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val libraryContext = applicationContext.createPackageContext(
            "com.aishihai.library",
            Context.CONTEXT_IGNORE_SECURITY or Context.CONTEXT_INCLUDE_CODE
        )
        val loader = libraryContext.classLoader

        val clazz = Class.forName("com.aishihai.library.Util", true, loader)
        val instance = clazz.newInstance()

        val getStringMethod = clazz.getDeclaredMethod("getText", Context::class.java)
        val addTextViewMethod =
            clazz.getDeclaredMethod("addTextView", Context::class.java, Activity::class.java, Int::class.java)
        val startActivityMethod = clazz.getDeclaredMethod("startActivity", Context::class.java, Context::class.java)

        get_string.setOnClickListener {
            Toast.makeText(
                applicationContext,
                getStringMethod.invoke(instance, libraryContext) as String,
                Toast.LENGTH_LONG
            ).show()
        }

        add_view.setOnClickListener {
            addTextViewMethod.invoke(instance, libraryContext, this, R.id.root)
        }

        start_browser.setOnClickListener {
            startActivityMethod.invoke(instance, libraryContext, applicationContext)
        }
    }
}
