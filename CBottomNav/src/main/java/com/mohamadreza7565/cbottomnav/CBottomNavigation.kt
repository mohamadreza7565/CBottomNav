package com.mohamadreza7565.cbottomnav

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.forEachIndexed


@SuppressLint("RestrictedApi")
class CBottomNavigation(
    val mContext: Context,
    val attr: AttributeSet
) :
    LinearLayout(mContext, attr) {

    private var mTag: Int? = null
    private lateinit var menu: Menu
    var selectedItemId: Int = 0
        get() = field


    private var onTabItemClickListener: OnTabItemClickListener? = null

    private var linearLayout: LinearLayout? = null

    private val margin = 5

    fun setTabs(menu: Menu) {
        this.menu = menu
        createTabs()
    }

    fun setOnTabItemClickListener(onTabItemClickListener: OnTabItemClickListener?) {
        this.onTabItemClickListener = onTabItemClickListener
    }

    interface OnTabItemClickListener {
        fun onTabClicked(item: MenuItem, reselect: Boolean)
    }

    init {
        linearLayout = LinearLayout(context)
        linearLayout!!.orientation = HORIZONTAL
        val layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        linearLayout!!.layoutParams = layoutParams
        linearLayout!!.setPadding(0, 0, 0, 0)
        addView(linearLayout)
        orientation = VERTICAL

        val a = context.obtainStyledAttributes(attr, R.styleable.CustomBottomNav)
        val menuId = a.getResourceId(R.styleable.CustomBottomNav_menuChildView, 0)
        if (menuId != 0) {
            try {
                menu = MenuBuilder(context)
                MenuInflater(context).inflate(menuId, menu)
                createTabs()
            } catch (e: Exception) {
                Log.e("Base bottom navigation", "ERROR")
            }
        }


    }


    private fun createTabs() {
        menu.forEachIndexed { index, tab ->
            var layoutParams: LayoutParams

            @SuppressLint("InflateParams") val inflate = LayoutInflater.from(
                context
            ).inflate(R.layout.item_bottom_bar, null)

            inflate.tag = tab.itemId
            (inflate.findViewById<View>(R.id.ivIcon) as AppCompatImageView).setImageDrawable(
                tab.icon
            )
            (inflate.findViewById(R.id.tvTitle) as AppCompatTextView).text = tab.title
            (inflate.findViewById(R.id.tvBadge) as AppCompatTextView).visibility = View.GONE
            inflate.setOnClickListener { v: View ->
                setSelectedTab(
                    tab
                )
            }
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(margin, margin, margin, margin)
            layoutParams.weight = 1.0f
            linearLayout!!.addView(inflate, 0, layoutParams)
            if (index == 0) {
                makeViolet(inflate)
                mTag = tab.itemId
                selectedItemId = tab.itemId
            } else {
                makeGray(inflate)
            }
        }
    }

    fun setBadgeCount(badge: Int, menuId: Int) {
        val tabItem = findViewWithTag<View>(menuId)
        val badgeTv = (tabItem.findViewById<View>(R.id.tvBadge) as AppCompatTextView)
        badgeTv.text = badge.toString()
    }

    fun setShowBadge(menuItem: Int, show: Boolean) {
        val tabItem = findViewWithTag<View>(menuItem)
        if (show) {
            tabItem.findViewById<View>(R.id.tvBadge).visibility = VISIBLE
        } else {
            tabItem.findViewById<View>(R.id.tvBadge).visibility = GONE
        }
    }

    fun setSelectedTab(menuItem: MenuItem) {
        makeViolet(findViewWithTag(menuItem.itemId))
        if (menuItem.itemId != mTag) {
            if (mTag != null) {
                makeGray(findViewWithTag(mTag))
            }
            mTag = menuItem.itemId
            selectedItemId = menuItem.itemId
            if (onTabItemClickListener != null) {
                onTabItemClickListener!!.onTabClicked(menuItem, false)
            }
        } else {
            if (onTabItemClickListener != null) {
                onTabItemClickListener!!.onTabClicked(menuItem, true)
            }
        }
    }

    private fun makeViolet(view: View) {
        view.findViewById<MotionLayout>(R.id.bottomNavMotionLyt).transitionToStart()
    }

    private fun makeGray(view: View) {
        view.findViewById<MotionLayout>(R.id.bottomNavMotionLyt).transitionToEnd()
    }

}
