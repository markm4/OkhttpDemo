package com.hxf.flowchart

import android.graphics.Paint

class FlowChartText {
    val TAG = "FlowChartText"

    var textStr: String = ""
    var middlePercentageX = 10F
    var middlePercentageY = 10F
    var top: Int = 0
    var left: Int = 0
    var mX = 0
    var mY = 0
    var halfTextWidth:Int = 0
    var halfTextHeight:Int = 0
    public var textColor: Long = 0xff000000
    public var paint:Paint? = null
    var fontSize:Float = 32F

    public var rlTop :Int = 0
    public var rlLeft:Int = 0
    var prefix = ""
    var data = ""
       set(value) {
           field=value
           this.textStr = ""+this.prefix+value+this.postfix
       }
    var postfix = ""

    constructor(middlePercentageX:Float,middlePercentageY:Float,string: String,middle:Boolean,colorStr:String = "#000000ff",fontSize:Float) {
        this.middlePercentageX = middlePercentageX
        this.middlePercentageY = middlePercentageY
        this.textStr = string
        this.data = string
        this.paint = Paint()
        this.fontSize = fontSize
        if ( Utils.regexMatch("""#[A-Fa-f0-9]{8}""",colorStr)) {
            this.textColor = colorStr.substring(1).toLong(16)
        } else {
            this.textColor = 0xff000000
        }
        paint!!.color = this.textColor.toInt()
        paint!!.textSize = fontSize
        halfTextHeight =  Utils.getTextHeight(paint!!,fontSize)/2
        halfTextWidth =  Utils.getTextWidth(paint!!,this.textStr,fontSize)/2

    }

    constructor(middlePercentageX:Float,middlePercentageY:Float,prefix: String,data: String,postfix: String,middle:Boolean,colorStr:String = "#000000ff",fontSize:Float) {
        this.middlePercentageX = middlePercentageX
        this.middlePercentageY = middlePercentageY
        this.textStr = ""+prefix+data+postfix
        this.prefix = prefix
        this.data = data
        this.postfix = postfix
        this.paint = Paint()
        this.fontSize = fontSize
        if ( Utils.regexMatch("""#[A-Fa-f0-9]{8}""",colorStr)) {
            this.textColor = colorStr.substring(1).toLong(16)
        } else {
            this.textColor = 0xff000000
        }
        paint!!.color = this.textColor.toInt()
        paint!!.textSize = fontSize
        halfTextHeight =  Utils.getTextHeight(paint!!,fontSize)/2
        halfTextWidth =  Utils.getTextWidth(paint!!,this.textStr,fontSize)/2

    }

    public fun setRealPosition(scaleWidth:Float,scaleHeight:Float){

    }

    public fun setBitmapSize(width:Float,height:Float){
        this.mY = (height*middlePercentageY/100).toInt()
        this.mX = (width*middlePercentageX/100).toInt()
        this.top = mY -halfTextHeight
        this.left = mX - halfTextWidth
        rlTop = this.top
        rlLeft = this.left
    }
}