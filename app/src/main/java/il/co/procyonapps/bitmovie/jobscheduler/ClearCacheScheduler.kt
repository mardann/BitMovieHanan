package il.co.procyonapps.bitmovie.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import androidx.work.Configuration
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class ClearCacheScheduler: JobService() {
    
    init {
       Configuration.Builder().setJobSchedulerJobIdRange(1,1).build()
    }
    
    private val internalJob = Job()
    private val scope = MainScope() + internalJob
    override fun onStartJob(params: JobParameters?): Boolean {
        scope.launch(Dispatchers.IO) {
            Glide.get(this@ClearCacheScheduler).clearDiskCache()
            jobFinished(params, true)
        }
        return true
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        internalJob.complete()
        return true
    }
}