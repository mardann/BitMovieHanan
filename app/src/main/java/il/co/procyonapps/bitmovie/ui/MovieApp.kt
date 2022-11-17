package il.co.procyonapps.bitmovie.ui

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import il.co.procyonapps.bitmovie.jobscheduler.ClearCacheScheduler
import java.time.Duration

@HiltAndroidApp
class MovieApp : Application() {
    val TAG = this::class.simpleName ?: "Unspecified"
    override fun onCreate() {
        super.onCreate()
        
        registerClearCacheScheduler()
    }
    private val JOB_ID = 1
    private fun registerClearCacheScheduler() {
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        
        if (jobScheduler.getPendingJob(JOB_ID) == null) {
            val serviceName = ComponentName(this, ClearCacheScheduler::class.java)
            val jobInfo = JobInfo.Builder(JOB_ID, serviceName)
                .setPeriodic(Duration.ofDays(1).toMillis(), Duration.ofHours(3).toMillis())
                .setPersisted(true)
                .setRequiresDeviceIdle(true)
                .build()
            val schedulerResult: Int = jobScheduler.schedule(jobInfo)
            Log.d(TAG, "registerJobScheduler: registered successfully? ${schedulerResult == JobScheduler.RESULT_SUCCESS}")
        }
    }
}