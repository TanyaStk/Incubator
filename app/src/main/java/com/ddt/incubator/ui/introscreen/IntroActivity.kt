package com.ddt.incubator.ui.introscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ddt.incubator.R
import com.ddt.incubator.data.models.IntroView
import com.ddt.incubator.ui.MainActivity
import com.ddt.incubator.ui.introscreen.adapters.ViewPagerIntroAdapter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    lateinit var introView: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        addToIntroView()

        viewPager2.adapter = ViewPagerIntroAdapter(introView)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        circleIndicator.setViewPager(viewPager2)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position == 2) {
                    animationButton()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun animationButton() {
        btn_start_app.visibility = View.VISIBLE

        btn_start_app.animate().apply {
            duration = 1400
            alpha(1f)

            btn_start_app.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun addToIntroView() {
        //Create some items that you want to add to your viewpager

        introView = listOf(
            IntroView("Welcome to Incubator!", R.drawable.ic_blue_egg),
            IntroView(
                "Create good habits\n" +
                        "Plan your day\n" + "Reach your goal with ease", R.drawable.ic_yellow_egg
            ),
            IntroView(
                "Good luck!\n" +
                        "Tap on the button below to get started with using the app!",
                R.drawable.ic_pink_egg
            ),
        )
    }
}
