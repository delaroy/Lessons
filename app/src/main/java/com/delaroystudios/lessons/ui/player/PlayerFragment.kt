package com.delaroystudios.lessons.ui.player

import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.delaroystudios.lessons.R
import com.delaroystudios.lessons.binding.FragmentDataBindingComponent
import com.delaroystudios.lessons.databinding.PlayerFragmentBinding
import com.delaroystudios.lessons.db.LessonDao
import com.delaroystudios.lessons.db.RecentlyWatched
import com.delaroystudios.lessons.di.Injectable
import com.delaroystudios.lessons.util.autoCleared
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.DefaultEventListener
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PlayerFragment : Fragment(), Injectable {

    @Inject
    lateinit var lessonDao: LessonDao
    // mutable for testing
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<PlayerFragmentBinding>()

    private val params by navArgs<PlayerFragmentArgs>()
    private var mPlayer: SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<PlayerFragmentBinding>(
            inflater,
            R.layout.player_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding

        /*dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
*/        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.show()
        binding.args = params
        getPlayer(params.mediaurl)
        binding.title.text  = params.name
        binding.chapter.text = params.chapter

        (activity as AppCompatActivity).supportActionBar?.title = params.name
        mPlayer!!.addListener(object : DefaultEventListener() {
            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int
            ) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    // media actually playing
                    GlobalScope.launch {
                        insertRecentlyWatched()
                    }

                } else if (playWhenReady) {
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                } else {
                    // player paused in any state
                }
            }
        })

    }

    suspend fun insertRecentlyWatched() {
        return withContext(Dispatchers.IO) {
            val mlessonsData = RecentlyWatched(params.id, params.subject, params.name, params.mediaurl, params.chapter)
            lessonDao.insertRecentlyWatched(mlessonsData)
        }
    }

    private fun getPlayer(url : String) {
        val trackSelector = DefaultTrackSelector()
        mPlayer = ExoPlayerFactory.newSimpleInstance(context!!, trackSelector)
        binding.videoView?.player = mPlayer

        val userAgent = Util.getUserAgent(context!!, "Exo")
        val mediaUri = Uri.parse(url)
        val mediaSource = ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(context!!, userAgent), DefaultExtractorsFactory(), null, null)

        mPlayer?.prepare(mediaSource)

        val componentName = ComponentName(context!!, "Exo")
        mediaSession = MediaSessionCompat(context!!, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Release the player when it is not needed
        mPlayer!!.release()
    }
}