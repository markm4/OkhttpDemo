package com.hxf.flowchart

class FlowChartButton {

    constructor(
        top: Int = 0,
        left: Int = 0,
        width: Int = 200,
        height: Int = 100,
        borderColor: String = "#ff000000",
        onClickListener: FlowChartButtonListener?,
        isScreenLandscape:Boolean
    ) {
        this.top = top
        this.left = left
        this.width = width
        this.height = height
        if (Utils.regexMatch("""#[A-Fa-f0-9]{8}""",borderColor)) {
            this.borderColor = borderColor.substring(1).toLong(16)
        } else {
            this.borderColor = 0xff000000
        }

        this.onClickListener = onClickListener
        this.isScreenLandscape = isScreenLandscape
    }

    constructor(
        top: Int,
        left: Int,
        width: Int,
        height: Int,
        onClickListener: FlowChartButtonListener?,
        isScreenLandscape:Boolean=false
    ) {
        FlowChartButton(
            top,
            left,
            width,
            height,
            "#ff000000",
            onClickListener,
            false
        );
    }

    constructor(
        top: Int,
        left: Int,
        onClickListener: FlowChartButtonListener?
    ) {
        FlowChartButton(
            top,
            left,
            200,
            100,
            "#ff000000",
            onClickListener,
            false
        );
    }

    constructor() {
        FlowChartButton(
            0,
            0,
            200,
            100,
            "#ff000000",
            object : FlowChartButtonListener {
                override fun onClick() {

                }
            },
            false
        );
    }

    constructor(
        middlePercentageX: Float = 10F,
        middlePercentageY: Float = 10F,
        width: Int = 200,
        height: Int = 100,
        borderColor: String = "#ff000000",
        onClickListener: FlowChartButtonListener?
    ) {
        this.middlePercentageY = middlePercentageY
        this.middlePercentageX = middlePercentageX
        this.width = width
        this.height = height
        if (Utils.regexMatch("""#[A-Fa-f0-9]{8}""",borderColor)) {
            this.borderColor = borderColor.substring(1).toLong(16)
        } else {
            this.borderColor = 0xff000000
        }

        this.onClickListener = onClickListener
    }

    var middlePercentageX = 10F
    var middlePercentageY = 10F
    var mX = 0
    var mY = 0
    public var top: Int = 0
    public var left: Int = 0
    public var width: Int = 200
    public var height: Int = 100
    public var borderColor: Long = 0xff000000
    public var onClickListener: FlowChartButtonListener? = null;

    public var rlTop :Int = 0
    public var rlLeft:Int = 0
    public var portraitTop:Int = 0
    public var portraitLeft:Int = 0
    public var landscapeTop:Int = 0
    public var landscapeLeft:Int = 0
    public var isScreenLandscape = false

    var rlPositionY = 0
    var rlPositionX = 0

    public fun onclick(){
        onClickListener?.onClick()
    }

    public fun setBitmapSize(bWidth:Float,bHeight:Float){
        this.mY = (bHeight*middlePercentageY/100).toInt()
        this.mX = (bWidth*middlePercentageX/100).toInt()
        this.top = mY -this.height/2
        this.left = mX - this.width/2
        rlTop = this.top
        rlLeft = this.left
    }
}