package ir.devar.minipaint

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import ir.devar.minipaint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var binding: ActivityMainBinding
    private var currentStrokeCode: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paintingBtnFont1.setOnClickListener(this)
        binding.paintingBtnFont2.setOnClickListener(this)
        binding.paintingBtnFont3.setOnClickListener(this)
        binding.paintingBtnFont4.setOnClickListener(this)
        binding.paintingBtnFont5.setOnClickListener(this)

        binding.paintingBtnPencil.setOnClickListener(this)
        binding.paintingBtnEraser.setOnClickListener(this)

        binding.paintingRedBtn.setOnClickListener(this)
        binding.paintingOrangeBtn.setOnClickListener(this)
        binding.paintingYellowBtn.setOnClickListener(this)
        binding.paintingGreenBtn.setOnClickListener(this)
        binding.paintingBlueBtn.setOnClickListener(this)
        binding.paintingPurpleBtn.setOnClickListener(this)
        binding.paintingVioletBtn.setOnClickListener(this)
        binding.paintingBlackBtn.setOnClickListener(this)
        binding.paintingWhiteBtn.setOnClickListener(this)


        binding.paintingRedBtn.setOnLongClickListener(this)
        binding.paintingOrangeBtn.setOnLongClickListener(this)
        binding.paintingYellowBtn.setOnLongClickListener(this)
        binding.paintingGreenBtn.setOnLongClickListener(this)
        binding.paintingBlueBtn.setOnLongClickListener(this)
        binding.paintingPurpleBtn.setOnLongClickListener(this)
        binding.paintingVioletBtn.setOnLongClickListener(this)
        binding.paintingBlackBtn.setOnLongClickListener(this)
        binding.paintingWhiteBtn.setOnLongClickListener(this)

        Toast.makeText(this, "Long Click To Change Background", Toast.LENGTH_LONG).show()

    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.painting_btn_pencil -> {
                    selectPencil(it)
                    setColor(Color.BLACK, it)
                    setFont(10, it)
                    selectFontBtn(it, currentStrokeCode, Color.BLACK)
                }
                R.id.painting_btn_eraser -> {
                    selectEraser(it)
                    //default values of eraser
                    setFont(40, it)
                    selectFontBtn(it, currentStrokeCode, Color.BLACK)
                }
                R.id.painting_btn_font1 -> {
                    setFont(10, it)
                }
                R.id.painting_btn_font2 -> {
                    setFont(20, it)
                }
                R.id.painting_btn_font3 -> {
                    setFont(30, it)
                }
                R.id.painting_btn_font4 -> {
                    setFont(40, it)
                }
                R.id.painting_btn_font5 -> {
                    setFont(50, it)
                }
                //intentionally we pass pencil btn to be able to draw stroke around it
                R.id.painting_red_btn -> {
                    setColor(resources.getColor(R.color.colorPaintRed), binding.paintingBtnPencil)
                }
                R.id.painting_orange_btn -> {
                    setColor(resources.getColor(R.color.colorPaintOrange), binding.paintingBtnPencil)
                }
                R.id.painting_yellow_btn -> {
                    setColor(resources.getColor(R.color.colorPaintYellow), binding.paintingBtnPencil)
                }
                R.id.painting_green_btn -> {
                    setColor(resources.getColor(R.color.colorPaintGreen), binding.paintingBtnPencil)
                }
                R.id.painting_blue_btn -> {
                    setColor(resources.getColor(R.color.colorPaintBlue), binding.paintingBtnPencil)
                }
                R.id.painting_purple_btn -> {
                    setColor(resources.getColor(R.color.colorPaintPurple), binding.paintingBtnPencil)
                }
                R.id.painting_violet_btn -> {
                    setColor(resources.getColor(R.color.colorPaintViolet), binding.paintingBtnPencil)
                }
                R.id.painting_black_btn -> {
                    setColor(resources.getColor(R.color.black), binding.paintingBtnPencil)
                }
                R.id.painting_white_btn -> {
                    setColor(resources.getColor(R.color.white), binding.paintingBtnPencil)
                }
            }
        }
    }

    // draw color inside font btn by pressed and set strokeWidth
    private fun setFont(strokeWidth: Int, view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)

                if (child.contentDescription.contains("font")) {
                    if (child.isPressed) {
                        val color = binding.paintView.getDrawingColor()
                        child.backgroundTintList = ColorStateList.valueOf(color)
                    } else {
                        child.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.font_def_back))
                    }

                }

            }

        }
        setStroke(strokeWidth)

    }

    private fun setStroke(strokeWidth: Int) {
        binding.paintView.setLineWidth(strokeWidth)
        when (strokeWidth) {
            10 -> currentStrokeCode = 1
            20 -> currentStrokeCode = 2
            30 -> currentStrokeCode = 3
            40 -> currentStrokeCode = 4
            50 -> currentStrokeCode = 5
        }
    }

    private fun setColor(@ColorInt color: Int, view: View) {
        binding.paintView.setDrawingColor(color)


        val currentStroke = getCurrentStrokeCode(currentStrokeCode)
        setFont(currentStroke, view)
        selectFontBtn(view, currentStrokeCode, binding.paintView.getDrawingColor())
        unSelectEraser(view)

        //draw stroke around pencil
        val pencilButton = view as MaterialButton
        pencilButton.strokeWidth = dpToPx(3)
    }

    private fun selectPencil(view: View) {


        //draw stroke around pencil
        val pencilButton = view as MaterialButton
        pencilButton.strokeWidth = dpToPx(3)


        unSelectEraser(view)
    }

    //remove stroke around pencil btn
    private fun unSelectPencil(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)

                if (child.contentDescription.toString() == "pencil") {
                    val materialButton = child as MaterialButton
                    materialButton.strokeWidth = dpToPx(0)
                }

            }
        }
    }

    //draw color inside font btn by specify number of font btn --default is black
    private fun selectFontBtn(view: View, font: Int, color: Int) {
        val fontString = when (font) {
            1 -> "font1"
            2 -> "font2"
            3 -> "font3"
            4 -> "font4"
            5 -> "font5"
            else -> "font1"
        }
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)

                if (child.contentDescription.toString() == fontString) {
                    child.backgroundTintList = ColorStateList.valueOf(color)

                }

            }
        }
    }

    private fun selectEraser(view: View) {
        //enable erasing
        binding.paintView.setEraser()
        //draw stroke around eraser
        val eraserButton = view as MaterialButton
        eraserButton.strokeWidth = dpToPx(3)


        unSelectPencil(view)
    }

    //remove stroke around eraser btn
    private fun unSelectEraser(view: View) {
        val parent = view.parent
        if (parent is ViewGroup) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)

                if (child.contentDescription.toString() == "eraser") {
                    val materialButton = child as MaterialButton
                    materialButton.strokeWidth = dpToPx(0)
                }

            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    private fun getCurrentStrokeCode(currentFontCode: Int): Int {
        return when (currentFontCode) {
            1 -> 10
            2 -> 20
            3 -> 30
            4 -> 40
            5 -> 50
            else -> 10
        }
    }

    override fun onLongClick(v: View?): Boolean {
        v?.let {
            when (v.id) {
                R.id.painting_red_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintRed))
                }
                R.id.painting_orange_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintOrange))
                }
                R.id.painting_yellow_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintYellow))
                }
                R.id.painting_green_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintGreen))
                }
                R.id.painting_blue_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintBlue))
                }
                R.id.painting_purple_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintPurple))
                }
                R.id.painting_violet_btn -> {
                    setBackColor(resources.getColor(R.color.colorPaintViolet))
                }
                R.id.painting_black_btn -> {
                    setBackColor(resources.getColor(R.color.black))
                }
                R.id.painting_white_btn -> {
                    setBackColor(resources.getColor(R.color.white))
                }
            }
        }
        return true
    }

    private fun setBackColor(@ColorInt int: Int) {
        binding.paintView.setBackColor(int)
    }
}
