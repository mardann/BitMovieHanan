package il.co.procyonapps.bitmovie.ui.videoplayer

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.navArgs
import com.google.android.youtube.player.*
import il.co.procyonapps.bitmovie.api.APIConst
import il.co.procyonapps.bitmovie.databinding.FragmntYoutubeBinding

class YouTubeActivity : YouTubeBaseActivity() {
    val TAG = this::class.simpleName ?: "Unspecified"
    val args: YouTubeActivityArgs by navArgs()
    var binder: FragmntYoutubeBinding? = null
    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        binder = FragmntYoutubeBinding.inflate(layoutInflater)
        setContentView(binder!!.root)
        setActionBar(binder!!.myToolbar)
        
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        
        
        onViewCreated(binder!!.root, p0)
    }
    
    private fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binder!!.player.initialize(APIConst.YOUTUBE_DEV_KEY, playerInitialization)
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
    private val playerInitialization = object : YouTubePlayer.OnInitializedListener {
        override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
            if (!wasRestored && player != null) {
                player.cueVideo(args.videoId)
                binder?.player?.postDelayed({
                    player.play()
                }, 2000)
            }
        }
        
        override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
            Log.e(TAG, "onInitializationFailure: ${p1?.name}")
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        binder = null
    }
}