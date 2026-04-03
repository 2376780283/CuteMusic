@file:OptIn(KoinExperimentalAPI::class)

package com.zzh.cutemusic.domain

import android.app.Application
import android.app.Activity
import android.content.Intent
import android.os.Process
import com.zzh.cutemusic.di.appModule
import com.zzh.cutemusic.presentation.screens.crash.CrashActivity
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class App : Application(), KoinStartup {

    private var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate() {
        super.onCreate()
        setupCrashHandler()
    }

    private fun setupCrashHandler() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            handleUncaughtException(thread, throwable)
        }
    }

    private fun handleUncaughtException(thread: Thread, throwable: Throwable) {
        // 保存崩溃日志到文件（可选）
        saveCrashLog(throwable)

        // 启动 CrashActivity
        val intent = CrashActivity.newIntent(this, throwable)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

        // 杀死当前进程
        Process.killProcess(Process.myPid())
        exitProcess(1)
    }

    private fun saveCrashLog(throwable: Throwable) {
        try {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            val crashLog = stringWriter.toString()
            
            // 可以在这里添加保存到文件的逻辑
            // 例如保存到外部存储或应用内部目录
            android.util.Log.e("CuteMusic", "Crash: $crashLog")
        } catch (e: Exception) {
            android.util.Log.e("CuteMusic", "Failed to save crash log", e)
        }
    }

    override fun onKoinStartup() = koinConfiguration {
        androidContext(this@App)
        modules(appModule)
    }
}