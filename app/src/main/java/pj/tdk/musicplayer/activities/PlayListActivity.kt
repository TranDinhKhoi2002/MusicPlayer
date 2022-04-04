package pj.tdk.musicplayer.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import pj.tdk.musicplayer.adapters.MusicAdapter
import pj.tdk.musicplayer.databinding.ActivityPlayListBinding
import pj.tdk.musicplayer.objects.MusicList
import pj.tdk.musicplayer.objects.PlayList

class PlayListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imgPlayList.setImageResource(PlayList.playList.img)
        binding.txtTitle.text = PlayList.playList.title
        binding.txtSubTitle.text = PlayList.playList.sub_title

        val adapter = MusicAdapter(MusicList.songList)
        binding.rvMusic.adapter = adapter

        val layoutRecentSongManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rvMusic.layoutManager = layoutRecentSongManager

        binding.imgBackArrow.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                //TODO("Not yet implemented")
                finish()
            }
        })

        val cornerBorder = binding.appBar.background as MaterialShapeDrawable
        cornerBorder.setShapeAppearanceModel(
            cornerBorder.getShapeAppearanceModel().toBuilder().setBottomLeftCorner(CornerFamily.ROUNDED, 64f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 64f)
                .build()
        )
    }
}