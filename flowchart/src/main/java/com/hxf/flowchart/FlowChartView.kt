package com.hxf.flowchart

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


class FlowChartView: View {

    val TAG = "FlowChartView"

    var currentActivity:Activity? = null

    var paint:Paint? = null
    var btnPaint:Paint? = null
    var textPaint:Paint? = null
    var bitmap:Bitmap? = null
    var num:Int = 789;
    public var bHeight:Float = 0f
    public var bWidth:Float = 0f
    var nHeight:Float = 0f
    var nWidth:Float = 0f
    var lastEvent:MotionEvent? = null
    var x1 = 0f
    var y1 = 0f
    var x2 = 0f
    var y2 = 0f
    var positionX = 0f
    var positionY = 0f
    var buttonList:ArrayList< FlowChartButton> = ArrayList< FlowChartButton>();
    var textList:ArrayList<FlowChartText> = ArrayList<FlowChartText>()
    var paintList:ArrayList<Paint> = ArrayList<Paint>();

    // width of screen
    var screenWidth = 0;
    // height of screen
    var screenHeight = 0;
    // last scale
    var lastScaleHeight = 1F;
    var lastScaleWidth = 1F;
    //size of parent layout
    var mParentWidth = 0
    var mParentHeight = 0
    //frontier of view
    var frontierHeight = 0
    public var isScreenLandscape = true
    var isClick = false
    var backgroundImageSrc:Int = 0
        set(value) {
            field = value
            invalidate()
        }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //init(context)要在retrieveAttributes(attrs)前调用
        //因为属性赋值，会直接赋值到控件上去。如:
        //调用label = ""时，相当于调用了label的set方法。
        backgroundImageSrc = attrs?.getAttributeIntValue("http://schemas.android.com/apk/res-auto","ImageSrc",R.drawable.ic_launcher)?:R.drawable.ic_launcher
        init(context)
        //retrieveAttributes(attrs: AttributeSet)方法只接受非空参数
//        attrs?.let { retrieveAttributes(attrs) }
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //获取所在父布局的高度
        /**
         * 显示区域计算，宽度为屏幕宽度
         * 高度为控件设置的高度或者屏幕高度
         */
        val mViewGroup = parent as ViewGroup
        if (null != mViewGroup) {
            mParentWidth = mViewGroup.width
            //实际高度
            mParentHeight = mViewGroup.measuredHeight
            Log.e(TAG,"mParentHeight = "+mParentHeight+",this.measuredHeight = "+this.measuredHeight)
            if(mParentHeight>this.measuredHeight) {
                frontierHeight = this.measuredHeight
            } else {
                frontierHeight = mParentHeight
            }
        }


        Log.e(TAG,"onMeasure："+widthMeasureSpec)
        bitmap = BitmapFactory.decodeResource(context?.resources,backgroundImageSrc)
        bHeight = bitmap!!.height.toFloat()
        nHeight = bitmap!!.height.toFloat()
        bWidth = bitmap!!.width.toFloat()
        nWidth = bitmap!!.width.toFloat()
        /**
         * 屏幕旋转，需要重新计算屏幕宽度
         */
        var windowManager = currentActivity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels
        Log.e(TAG,"screenWidth = "+screenWidth+",screenHeight = "+screenHeight)
        nHeight = screenWidth/bWidth*bHeight
        nWidth = screenWidth.toFloat()
        Log.e(TAG,"nWidth = "+nWidth+"bWidth = "+bWidth+"nHeight = "+nHeight+"bHeight = "+bHeight)
//        val scaleWidth = BigDecimal.valueOf(nWidth.toDouble()).divide(BigDecimal.valueOf(bWidth.toDouble()),2,BigDecimal.ROUND_DOWN).toFloat()
        val scaleWidth = nWidth / bWidth
        val scaleHeight = nHeight / bHeight
//        val scaleHeight = BigDecimal.valueOf(nHeight.toDouble()).divide(BigDecimal.valueOf(bHeight.toDouble()),2,BigDecimal.ROUND_DOWN).toFloat()
        Log.e(TAG,"scaleWidth = "+scaleWidth+",scaleHeight = "+scaleHeight)
        // 取得想要缩放的matrix参数.
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        positionX = 0F
        positionY = 0F
        if(frontierHeight>nHeight) {
            positionY = -(frontierHeight-nHeight)/2
            Log.e(TAG,"positionY = "+positionY)
        }
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, (bitmap!!.width).toInt(), (bitmap!!.height).toInt(), matrix, true)

        bHeight = nHeight
        bWidth = nWidth
    }

    override fun onDisplayHint(hint: Int) {
        super.onDisplayHint(hint)
    }

    private fun init(context: Context) {
        Log.e("hxf","init")
        currentActivity =  Utils.findActivity(context)
        Log.e("hxf","init："+currentActivity)
        paint = Paint()
        btnPaint = Paint()
        textPaint = Paint()
        textPaint!!.color = Color.RED
        textPaint!!.textSize = 32F

    }

    public fun addButton(button:  FlowChartButton){
        buttonList.add(button)
        Log.e(TAG,"paintList.size = "+paintList.size)
        this.invalidate()
    }

    public fun addText(text:FlowChartText){
        textList.add(text)
        this.invalidate()
    }

    public fun setMyButtonList(list:ArrayList< FlowChartButton>){
        Log.e(TAG,"paintList.size = "+paintList.size)
        (paintList as MutableList< FlowChartButton>).clear()
        (paintList as MutableList< FlowChartButton>).addAll(list)
        Log.e(TAG,"paintList.size = "+paintList.size)
        this.invalidate()
    }

    public fun setMyTextList(flowChartTextList:ArrayList<FlowChartText>){
        textList = flowChartTextList
        this.invalidate()
    }

    public fun setMyNum(num:Int):Unit{
        this.num = num
    }

    override fun onDraw(canvas: Canvas?) {
        Log.e("hxf","onDraw")

        super.onDraw(canvas)
        val scaleWidth = nWidth as Float / bWidth
        val scaleHeight = nHeight as Float / bHeight
        // 取得想要缩放的matrix参数.
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片.
        var nBitmap :Bitmap? = bitmap
//        if (positionX >= 0 && positionY >= 0&&bWidth-positionX>0&&bHeight-positionY>0) {
//            nBitmap = Bitmap.createBitmap(bitmap, positionX.toInt(), positionY.toInt(), (bWidth-positionX).toInt(), (bHeight-positionY).toInt(), matrix, true)
//        } else if (positionX >= 0 && positionY >= 0&&bWidth-positionX<=100&&bHeight-positionY<=100) {
//            // 防止图片偏出显示区域，导致报错
//            nBitmap = Bitmap.createBitmap(bitmap, (positionX-100).toInt(), (positionY-100).toInt(), 100, 100, matrix, true)
//        } else if (positionX < 0&&positionX >= 0-(screenWidth/4)) {
//            /**
//             * 图片向右下移动，减少绘制的图片大小
//             */
//            nBitmap = Bitmap.createBitmap(bitmap, 0, 0, (bWidth+positionX).toInt(), (bHeight).toInt(), matrix, true)
//
//        } else {
//            nBitmap = Bitmap.createBitmap(bitmap, 0, 0, bWidth.toInt(), bHeight.toInt(), matrix, true)
//        }

        if (lastScaleHeight!=scaleHeight||lastScaleWidth!=scaleWidth) {
            nBitmap = Bitmap.createBitmap(bitmap, 0, 0, bWidth.toInt(), bHeight.toInt(), matrix, true)
        }

        /**
         * 实现图片向右下移动
         */
        canvas?.drawBitmap(nBitmap,-positionX,-positionY,paint)

        Log.e("hxf","buttonList.size = "+buttonList.size)
        var rlPositionY = positionY
        var rlPositionX = positionX
        if (isScreenLandscape||  Utils.DEBUGGER) {
            buttonList.forEachIndexed { index, flowChartButton ->
                flowChartButton.setBitmapSize(nWidth,nHeight);
                flowChartButton.rlPositionY = rlPositionY.toInt()
                flowChartButton.rlPositionX = rlPositionX.toInt()
                if ( Utils.DEBUGGER) {
                    btnPaint!!.color = flowChartButton.borderColor.toInt()
                    btnPaint!!.style = Paint.Style.STROKE
                } else {
                    btnPaint!!.color = 0x00000000
                }
                canvas?.drawRect(RectF(flowChartButton.rlLeft-rlPositionX,flowChartButton.rlTop-rlPositionY,(flowChartButton.rlLeft+flowChartButton.width-rlPositionX).toFloat(),(flowChartButton.rlTop+flowChartButton.height-rlPositionY).toFloat()),btnPaint)
            }
        }

        Log.e(TAG,"textList.size = "+textList.size)
        textList.forEachIndexed { index, flowChartText ->
            Log.e(TAG,"textList.mX = "+flowChartText.mX+",textList.mY = "+flowChartText.mY)
            flowChartText.setBitmapSize(nWidth,nHeight);
            Log.e(TAG,"flowChartText.rlTop = "+flowChartText.rlTop)
            textPaint!!.textSize = flowChartText.fontSize
            canvas?.drawText(flowChartText.textStr,flowChartText.rlLeft-rlPositionX, flowChartText.rlTop-rlPositionY,textPaint)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!!.pointerCount >1) {
            Log.e("hxf","x[1] = "+event!!.getX(1).toString())
        }
        var actionMasked = event!!.getActionMasked();//获得多点触控检测点
        when (actionMasked){
            MotionEvent.ACTION_DOWN->{
//                Log.e("hxf","ACTION_DOWN")
                x1 = event!!.getX(0)
                y1 = event!!.getY(0)
                isClick = true;
            }//第一个触控点按下

            MotionEvent.ACTION_POINTER_DOWN->{
//                Log.e("hxf","ACTION_POINTER_DOWN")
                x1 = event!!.getX(0)
                y1 = event!!.getY(0)
                x2 = event!!.getX(1)
                y2 = event!!.getY(1)
                if (event.pointerCount >1) {
                    isClick = false;
                }
//                pointArrayList.add(id,new BNPoint(event.getX(id),event.getY(id),getColor(),id));//触控点按下时，将获得(0 ~ 个数-1)中
                //可用的最小的id号，即后续按下的点可以获得
                //先前已经抬起的触控点的id
            }//第一个之后的触控点按下

            MotionEvent.ACTION_MOVE->{
                if (event.pointerCount >1) {
                    setNewSize(event)
                    getNewBitmap()
                } else if (event.pointerCount == 1) {
                    changePosition(event)
                }
//                Log.e("hxf","ACTION_MOVE")
            }//主、辅点移动

            //pointArrayList.get(id).setLocation(event.getX(id),event.getY(id));//id=0，即如果没有循环遍历，则只会更新主控点
            MotionEvent.ACTION_UP->{
//                Log.e("hxf","ACTION_UP")
                if (isClick&&(isScreenLandscape||  Utils.DEBUGGER)) {
                    buttonList.forEachIndexed { index, flowChartButton ->
                        if (event.x>flowChartButton.rlLeft-flowChartButton.rlPositionX
                            &&event.y>flowChartButton.rlTop-flowChartButton.rlPositionY
                            &&event.x<(flowChartButton.rlLeft+flowChartButton.width-flowChartButton.rlPositionX)
                            &&event.y<(flowChartButton.rlTop+flowChartButton.height-flowChartButton.rlPositionY)) {
                            flowChartButton.onclick();
                        }
                    }
                }
            }//最后一个点抬起

            MotionEvent.ACTION_POINTER_UP->{
//                Log.e("hxf","ACTION_POINTER_UP")
                if(event.pointerCount == 1) {
                    x1 = event!!.getX(0)
                    y1 = event!!.getY(0)
                }
            }//非最后一个点抬起
        }
        return super.onTouchEvent(event)
    }

    fun getNewBitmap() {
        // 计算缩放比例. 移至onDraw
        this.invalidate()
    }

    fun changePosition(event: MotionEvent?) {
        /**
         * y方向向上为 位移负，坐标在减小
         * 沿y轴向下划 位移为正值，坐标增大
         *
         *  x轴向左移动 位移为负数，坐标减小
         */
        var nextX = (positionX+(x1 - event!!.getX(0))/10)
        if (nextX<=nWidth-(screenWidth*3/4)&&nextX>0-(screenWidth/4)) {
            /**
             * next>0 图片左边界为最左侧，不能离开屏幕左边界
             * nextX<=nWidth-bWidth 图片有边界不能移除屏幕
             *
             * 理想情况左侧可移入屏幕1/4，且最多移入1/4，需要屏幕宽度信息
             * 显示部分不能超过图片宽度，因为多余部分没有数据，渲染时导致崩溃
             */
            positionX = nextX*scaleX
        }
        var nextY = (positionY+(y1 - event!!.getY(0))/10)
        Log.e(TAG,"nextY = "+nextY+",frontierHeight = "+frontierHeight+", frontierHeight/4 = "+(frontierHeight/4)+", nHeight-(frontierHeight/4*3) = "+(nHeight-(frontierHeight/4*3)))
        if (nextY<=nHeight-(frontierHeight/4*3)&&nextY>0-(frontierHeight/4)) {
            positionY = nextY*scaleY
        }
        Log.e(TAG,"y1 - event!!.getY(0) = "+(y1 - event!!.getY(0))+",x1 - event!!.getX(0) = "+(x1 - event!!.getX(0)))
        if(Math.abs(x1 - event!!.getX(0))>10||Math.abs(y1 - event!!.getY(0))>10){
            isClick = false
        }
        this.invalidate()
    }

    fun setNewSize(event: MotionEvent?) {
        var nx1 = event!!.getX(0)
        var ny1 = event!!.getY(0)
        var nx2 = event!!.getX(1)
        var ny2 = event!!.getY(1)
        nWidth = nWidth+(Math.abs(nx2-nx1) - Math.abs(x2-x1))
        nHeight = nHeight+(Math.abs(ny2-ny1) - Math.abs(y2-y1))
        x1 = nx1
        x2 = nx2
        y1 = ny1
        y2 = ny2
        lastEvent = event
    }
}