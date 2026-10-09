package cn.edu.sicnu.first.guojie.second.model

import android.content.res.Resources
import cn.edu.sicnu.first.guojie.second.R

/**
 * MVC 中的 Model 层：ProgramAdviser（编程语言推荐顾问）。
 *
 * 模型只负责数据与查询逻辑，不持有任何 View，也不直接写死字符串：
 * “查询类别 -> 推荐编程语言”的对应表通过字符串资源
 * R.array.advice_categories 与 R.array.advice_programs 间接构建。
 */
class ProgramAdviser(resources: Resources) {

    private val adviceTable: Map<String, String>

    init {
        val categories = resources.getStringArray(R.array.advice_categories)
        val programs = resources.getStringArray(R.array.advice_programs)
        adviceTable = categories.zip(programs).toMap()
    }

    /**
     * 根据用户选择的类别查询推荐的编程语言。
     * @param category Spinner 中选中的类别
     * @return 模型层返回的查询结果
     */
    fun advise(category: String): String {
        return adviceTable[category].orEmpty()
    }
}
