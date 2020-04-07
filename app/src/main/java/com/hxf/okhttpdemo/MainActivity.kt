package com.hxf.okhttpdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity";

    private val PUB_KEY =
//    "-----BEGIN PUBLIC KEY-----\n" +
//    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxsx1/cEpUmSwUwwPU0SciWcVK\n" +
//    "mDORBGwSBjJg8SL2GrCMC1+Rwz81IsBSkhog7O+BiXEOk/5frE8ryZOpOm/3PmdW\n" +
//    "imEORkTdS94MilEsk+6Ozd9GnAz6Txyk07yDDwCEmA3DoFY2hfKg5vPoskKA0QBC\n" +
//    "894cUqq1aH9h44SwyQIDAQAB\n" //+
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxsx1/cEpUmSwUwwPU0SciWcVK" +
                "mDORBGwSBjJg8SL2GrCMC1+Rwz81IsBSkhog7O+BiXEOk/5frE8ryZOpOm/3PmdW" +
                "imEORkTdS94MilEsk+6Ozd9GnAz6Txyk07yDDwCEmA3DoFY2hfKg5vPoskKA0QBC" +
                "894cUqq1aH9h44SwyQIDAQAB" //+
    //    "-----END PUBLIC KEY-----\n";
    private val PRIV_KEY =
//    "-----BEGIN RSA PRIVATE KEY-----\n" +
        "MIICWwIBAAKBgQCxsx1/cEpUmSwUwwPU0SciWcVKmDORBGwSBjJg8SL2GrCMC1+R\n" +
                "wz81IsBSkhog7O+BiXEOk/5frE8ryZOpOm/3PmdWimEORkTdS94MilEsk+6Ozd9G\n" +
                "nAz6Txyk07yDDwCEmA3DoFY2hfKg5vPoskKA0QBC894cUqq1aH9h44SwyQIDAQAB\n" +
                "AoGAEH9Ato9R80MqHr5RGX2WXL/JS2jQZr7qmozFOg9A7+if6cx3gaSG9nOkt7W1\n" +
                "I8fjX1sHajNOmwq36eiDoyMX+EwloEJXmvDJocpObzIGGKcCIkowhdhpcgAE0qmv\n" +
                "FFi2LOK2Z48jcPXmOCHywXQLBs7GGFvWmgAqo8bP9OmdC7UCQQDqSuLEZjgWPS94\n" +
                "BgwzkHD6ZLgaKzOzPH8iXh7EVqReGUXf16owVkuMWLF7hq02fXt7G4kjdztlmfCy\n" +
                "8fZPM02VAkEAwinro2YxG4aGHvtVnizEArHp9YdIW+lBy5vrTgyYG2gpTBMfdore\n" +
                "hElI9eR6vhJMpudx1epcpjmS52cJn7OBZQJAFScvtCW6eJ+Lkp2RKnKnEKRZTtuJ\n" +
                "rmwO2m5+/qEH9Ar6GQyiq/yOk5xKYem1586KgIHq7s3MCg9NAQsBfwMVxQJAKmbN\n" +
                "NtnST5iJIarxf6F3DL+dwCjS/H9sBvL96AWIEjQlEJ/8dv7MqUb3z/sdcvS8GJbi\n" +
                "nTyZDxPzqOUvjNi+oQJAPumO909O6mbqrtDmvz100JMQdILNzc+wynM45hu/Yj5V\n" +
                "qMiC7KMtCE3xaEMd21EzRtusJvGWY3KhLHNCcUx4jg==\n" //+
    //    "-----END RSA PRIVATE KEY-----\n";
    private var btn: Button? = null;
    private var flow_chart: Button? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById<Button>(R.id.click)
        flow_chart = findViewById(R.id.flow_chart)
        addListener();


    }

    private fun addListener() {
        btn?.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            accessValidateAuthorCode();

        }
        flow_chart?.setOnClickListener {
            Toast.makeText(this, "流程图", Toast.LENGTH_SHORT).show();
            this.startActivity(Intent(this,FlowChart::class.java))
        }
    }

    private fun accessValidateAuthorCode() {
        /**
         * rest/PollutantSourceApi/LoginApi/ValidateAuthorCode
         * https://api.chsdl.net/NewWryWebProxy/rest/PollutantSourceApi/LoginApi/ValidateAuthorCode
         * {"AuthCode":"50206"}
         */

//            var mediaType = MediaType.parse("charset=utf-8")
//            val requestBody = "{\"AuthCode\":\"50206\"}"
        var requestBody = FormBody.Builder().add("AuthCode", "50206").build()
//           var a = ARSAUtils.keyStrToPublicKey(PUB_KEY)
//            Log.e(TAG,a.toString());
        var Authorization: String = "Bearer " + ARSAUtils.encryptDataByPublicKey(
            "50206".toByteArray(),
            ARSAUtils.keyStrToPublicKey(PUB_KEY)
        )
        Log.e(TAG, "Authorization = " + Authorization);
//            var a = ARSAUtils.encryptDataByPrivateKey("50206".toByteArray(),ARSAUtils.keyStrToPrivate(PRIV_KEY))
//            Log.e(TAG,"a = "+a);
//
//            var b = RsaUtils.encryptDataStr("50206".toByteArray(),RsaUtils.loadPublicKey(PUB_KEY))
//            Log.e(TAG,"b = "+b);


        var request = Request.Builder()
            .url("https://api.chsdl.net/NewWryWebProxy/rest/PollutantSourceApi/LoginApi/ValidateAuthorCode")
            .addHeader("Authorization", Authorization)
//                .post(RequestBody.create(mediaType,requestBody))
            .post(requestBody)
            .build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(
                    TAG,
                    response.protocol().toString() + " " + response.code() + " " + response.message()
                );
                var headers = response.headers();
                for (i in 0 until headers.size()) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i))
                }
                Log.d(TAG, "onResponse: " + response.body()?.string());
            }

        })
    }
}
