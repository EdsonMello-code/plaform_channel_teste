package com.example.native_teste

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result


class MainActivity : FlutterActivity() {
    companion object {
        private const val CHANNEL = "com.example/channel"
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call: MethodCall, result: Result ->
            when (call.method) {
                "urlLaunch" -> launchUrl(call.arguments as String, result, call)
                "canLaunch" -> hasLaunch(result)
                else -> {
                    result.notImplemented()

                }
            }
        }
    }

    private fun hasLaunch(result: Result): Unit {
        try {
            val packageManager: PackageManager = packageManager

            val canLaunch = intent.resolveActivity(packageManager) != null
            result.success(canLaunch)
        } catch (error: ActivityNotFoundException) {
            result.error("200", error.message, null)
        }
    }

    private fun launchUrl(url: String, result: Result, call: MethodCall): Unit {
        val appScheme = call.arguments.toString()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(appScheme)

        if (call.arguments == null) {
            result.error("200", "Url está nullo!", null)
        }
        try {

            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            result.error("200", null, null)
        }
    }

}
