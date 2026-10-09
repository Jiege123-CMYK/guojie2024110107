package cn.edu.sicnu.first.guojie.second

import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import cn.edu.sicnu.first.guojie.second.model.ProgramAdviser

/**
 * MVC 中的 Controller 层：负责监听界面事件、调用模型层查询，并把结果交还给 View 显示。
 *
 * 功能区 1：每点击一次 ADD TEXTVIEW，就在代码中 new 一个 TextView
 *           并动态添加到 ScrollView 嵌套的 LinearLayout 容器中。
 * 功能区 2：点击“确定”后读取 Spinner 选中项，查询 ProgramAdviser 模型并展示结果。
 */
class MainActivity : Activity() {

    /** Model 层：编程语言推荐顾问 */
    private lateinit var programAdviser: ProgramAdviser

    /** 区1：承载动态 TextView 的 LinearLayout（位于 ScrollView 内） */
    private lateinit var textViewContainer: LinearLayout
    private lateinit var scrollView: ScrollView

    /** 区2：类别选择器与结果展示 */
    private lateinit var categorySpinner: Spinner
    private lateinit var adviceResultView: TextView

    /** 已动态添加的 TextView 数量 */
    private var textViewCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        programAdviser = ProgramAdviser(resources)

        bindViews()
        setupListeners()
    }

    private fun bindViews() {
        textViewContainer = findViewById(R.id.text_view_container)
        scrollView = findViewById(R.id.scroll_view)
        categorySpinner = findViewById(R.id.sp_language_category)
        adviceResultView = findViewById(R.id.tv_advice_result)
    }

    private fun setupListeners() {
        // 区1：动态添加 TextView
        findViewById<Button>(R.id.btn_add_textview).setOnClickListener {
            addNewTextView()
        }

        // 区2：查询模型层并返回结果
        findViewById<Button>(R.id.btn_ok).setOnClickListener {
            showAdvice()
        }
    }

    /**
     * 区1核心：完全使用代码动态生成 TextView 并加入容器。
     * 文字、颜色、字号、间距全部来自资源文件，代码中不出现硬编码字符串。
     */
    private fun addNewTextView() {
        textViewCount++

        val textView = TextView(this).apply {
            text = getString(R.string.new_textview_format, textViewCount)
            setTextColor(getColor(R.color.area1_text_color))
            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.dynamic_text_size)
            )
            val padding = resources.getDimensionPixelSize(R.dimen.dynamic_text_padding)
            setPadding(padding, padding, padding, padding)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        textViewContainer.addView(textView)

        // 添加后自动滚动到底部，保证最新条目可见
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    /**
     * 区2核心（Controller）：读取 View 上的选择 -> 查询 Model -> 更新 View。
     */
    private fun showAdvice() {
        val category = categorySpinner.selectedItem.toString()
        val advice = programAdviser.advise(category)
        adviceResultView.text = advice
        adviceResultView.gravity = Gravity.CENTER
    }
}
