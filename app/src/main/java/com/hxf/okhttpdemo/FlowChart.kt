package com.hxf.okhttpdemo

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hxf.flowchart.FlowChartButton
import com.hxf.flowchart.FlowChartButtonListener
import com.hxf.flowchart.FlowChartText
import com.hxf.flowchart.FlowChartView


class FlowChart : AppCompatActivity() {

    val TAG = "FlowChart"
    var threadRun = true


    var flow_char_refresh_btn:Button? = null
    var click2:Button? = null
    var flow_char_view : FlowChartView? = null
    var set_landscape:Button? =null
    var IsScreenLandscape  = false
    var myTextList:ArrayList<FlowChartText> = ArrayList<FlowChartText>();
    var numsArray = ArrayList<Int>();
    var one: FlowChartText? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_chart)
        init()
    }

    override fun onPause() {
        super.onPause()
        threadRun=false
    }

    override fun onResume() {
        super.onResume()
        threadRun=true
        startMyThread()
    }

    var handler = Handler(Handler.Callback {
        msg ->
            flow_char_view?.setMyTextList(myTextList)
            flow_char_view?.invalidate()
        false
    })

    private fun startMyThread(){
        var a = Thread(Runnable {
            while (threadRun){
                Thread.sleep(2000)
                myTextList.forEachIndexed { index, flowChartText ->
                    flowChartText.data = (numsArray.get(index)+getDValue()).toString()
                }
                Log.e(TAG,""+Thread.currentThread().getId()+" run");
                handler.sendEmptyMessage(1)
            }
        })
           a.start()
    }

    private fun getDValue():Int{
        return  Math.round(Math.random()*10).toInt()
    }

    private fun init() {
        var currentActivity = this
        flow_char_view = findViewById(R.id.flow_char_view)
        set_landscape = findViewById(R.id.set_landscape)
        var context:Context = this

        flow_char_view?.backgroundImageSrc=R.mipmap.a
        flow_char_view?.addButton(
            FlowChartButton(
                12F,
                65F,
                200,
                200,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "原水箱", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                23F,
                75F,
                160,
                80,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "原水增压泵", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                32.5F,
                60F,
                120,
                160,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "砂滤", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                41.5F,
                60F,
                120,
                160,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "碳滤", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                52F,
                60F,
                120,
                160,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "树脂", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                61.5F,
                65F,
                80,
                160,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "精密过滤器", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                68F,
                65F,
                80,
                160,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "高压泵", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                79F,
                49F,
                200,
                200,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "一级R0系统", Toast.LENGTH_SHORT).show()
                    }
                })
        )

        flow_char_view?.addButton(
            FlowChartButton(
                93F,
                55F,
                200,
                200,
                "#ffff0000",
                object : FlowChartButtonListener {
                    override fun onClick() {
                        Toast.makeText(context, "纯水箱", Toast.LENGTH_SHORT).show()
                    }
                })
        )
        flow_char_view?.addText(
            FlowChartText(
                50F,
                15F,
                "雪迪龙智慧环保研究院",
                true,
                "#ff666666",
                60F
            )
        )

        //显示的数据
        //原水箱
        one = FlowChartText(
            12F,
            65F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as FlowChartText)
        flow_char_view?.addText(one as FlowChartText)
        //原水增压泵
        one = FlowChartText(
            23F,
            75F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as FlowChartText)
        flow_char_view?.addText(one as FlowChartText)
        //砂滤
        one = FlowChartText(
            32.5F,
            60F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as FlowChartText)
        flow_char_view?.addText(one as FlowChartText)
        //碳滤
        one = FlowChartText(
            41.5F,
            60F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as FlowChartText)
        flow_char_view?.addText(one as FlowChartText)
        //树脂
        one = FlowChartText(
            52F,
            60F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as  FlowChartText)
        flow_char_view?.addText(one as  FlowChartText)
        //高压泵
        one =  FlowChartText(
            68F,
            60F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as  FlowChartText)
        flow_char_view?.addText(one as  FlowChartText)
        //一级RO系统
        one =  FlowChartText(
            79F,
            49F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as  FlowChartText)
        flow_char_view?.addText(one as  FlowChartText)
        //纯水箱
        one =  FlowChartText(
            93F,
            55F,
            "",
            "35",
            "L",
            true,
            "#ff666666",
            32F
        )
        numsArray.add(35)
        myTextList.add(one as  FlowChartText)
        flow_char_view?.addText(one as  FlowChartText)
    }
}
