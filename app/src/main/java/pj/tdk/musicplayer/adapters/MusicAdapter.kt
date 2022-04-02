package pj.tdk.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pj.tdk.musicplayer.databinding.ItemMusicBinding
import pj.tdk.musicplayer.models.MusicModel

class MusicAdapter(private val items: ArrayList<MusicModel>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(song: MusicModel) {
            binding.imgAvatar.setImageResource(song.img)
            binding.txtSong.text = song.song
            binding.txtSinger.text = song.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items.get(position)

        holder.bindItem(model)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}