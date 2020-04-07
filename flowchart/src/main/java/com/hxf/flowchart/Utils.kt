package com.hxf.flowchart

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt

class Utils {
    companion object{
        public val DEBUGGER = true
        var TAG = "Utils"
        fun regexMatch(regex:String,tag:String):Boolean{
            val regex = regex.toRegex()
            return regex.matches(tag)
        }
        /**
         * 获取文本的宽度
         * @param paint
         * @param str
         * @return
         */

        fun getTextWidth(paint: Paint, str: String?,fontSize:Float = 32F): Int {
            var iRet = 0
            paint.textSize = fontSize
            if (str != null && str.length > 0) {
                val len = str.length
                val widths = FloatArray(len)
                paint.getTextWidths(str, widths)
                for (j in 0 until len) {
                    iRet += Math.ceil(widths[j].toDouble()).toInt()
                }
            }
//            return iRet
            var measureTextWidth = paint.measureText(str).toInt()
            return measureTextWidth
        }

        fun getBaseLine(mTextPaint:Paint,y:Int,fontSize: Float):Int{
            mTextPaint.textSize = fontSize
            val fontMetrics: FontMetricsInt = mTextPaint.getFontMetricsInt()
            val baseline: Int =
                y-(fontMetrics.bottom - fontMetrics.top) / 2
            return baseline
        }

        fun getTextHeight (mTextPaint:Paint,fontSize: Float):Int {
            mTextPaint.textSize = fontSize
            val fontMetrics: FontMetricsInt = mTextPaint.getFontMetricsInt()
            return fontMetrics.top+fontMetrics.bottom
        }

        public fun findActivity(context: Context): Activity? {
            if (context is Activity) {
                return  context as Activity;
            }
            if (context is ContextWrapper) {
                var wrapper: ContextWrapper = context as ContextWrapper;
                return findActivity(wrapper.getBaseContext());
            } else {
                return null;
            }
        }
    }
}