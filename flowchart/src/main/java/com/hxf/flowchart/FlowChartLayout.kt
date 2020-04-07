package com.hxf.flowchart

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RelativeLayout

class FlowChartLayout :RelativeLayout{

    private var currentActivity: Activity? = null
    var RootView:RelativeLayout? = null
    var set_landscape:Button? = null
    var fcView:FlowChartView? = null

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context)
    }

    private fun init(context: Context?) {
        RootView = LayoutInflater.from(context).inflate(R.layout.flow_chart_layout,this) as RelativeLayout
        fcView = RootView?.findViewById<FlowChartView>(R.id.flow_char_view)
        set_landscape = RootView!!.findViewById<Button>(R.id.set_landscape)

        context.apply {
            currentActivity =  Utils.findActivity(context!!)
            if (fcView!!.isScreenLandscape) {
                currentActivity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            } else {
                currentActivity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }
            set_landscape?.setOnClickListener {
                if (fcView!!.isScreenLandscape) {
                    fcView!!.isScreenLandscape = false
                    currentActivity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                } else {
                    fcView!!.isScreenLandscape = true
                    currentActivity!!.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                }
            }
        }
    }
}