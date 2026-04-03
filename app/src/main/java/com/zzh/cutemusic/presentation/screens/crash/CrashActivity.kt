package com.zzh.cutemusic.presentation.screens.crash

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zzh.cutemusic.R
import com.zzh.cutemusic.presentation.theme.CuteMusicTheme
import java.io.PrintWriter
import java.io.StringWriter

class CrashActivity : ComponentActivity() {

    companion object {
        private const val EXTRA_STACK_TRACE = "stack_trace"
        private const val EXTRA_THREAD_NAME = "thread_name"

        fun newIntent(context: Context, throwable: Throwable): Intent {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            
            return Intent(context, CrashActivity::class.java).apply {
                putExtra(EXTRA_STACK_TRACE, stringWriter.toString())
                putExtra(EXTRA_THREAD_NAME, Thread.currentThread().name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stackTrace = intent.getStringExtra(EXTRA_STACK_TRACE) ?: "No stack trace available"
        val threadName = intent.getStringExtra(EXTRA_THREAD_NAME) ?: "Unknown thread"

        setContent {
            CuteMusicTheme(artImageBitmap = null) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CrashScreen(
                        stackTrace = stackTrace,
                        threadName = threadName,
                        onCopyCrashLog = { copyToClipboard(stackTrace) },
                        onRestartApp = { restartApp() }
                    )
                }
            }
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Crash Log", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, R.string.crash_log_copied, Toast.LENGTH_SHORT).show()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

@Composable
fun CrashScreen(
    stackTrace: String,
    threadName: String,
    onCopyCrashLog: () -> Unit,
    onRestartApp: () -> Unit
) {
    val scrollState = rememberScrollState()
    val deviceInfo = buildString {
        appendLine("Device Info:")
        appendLine("Android Version: ${Build.VERSION.SDK_INT} (${Build.VERSION.RELEASE})")
        appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        appendLine("Thread: $threadName")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.crash_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.crash_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 设备信息
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Text(
                text = deviceInfo,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 崩溃日志
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            Text(
                text = stackTrace,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 按钮区域
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCopyCrashLog,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(stringResource(R.string.crash_copy_log))
            }

            Button(
                onClick = onRestartApp,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.crash_restart))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
