package pj.tdk.musicplayer.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pj.tdk.musicplayer.databinding.ItemSongBinding
import pj.tdk.musicplayer.models.SongModel

class SongsAdapter(private val items: ArrayList<SongModel>): RecyclerView.Adapter<SongsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(song: SongModel) {
            binding.ivAvatarImage.setImageResource(song.image)
            binding.tvTitle.text = song.title
            binding.tvSinger.text = song.singer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = items[position]

        holder.bindItem(model)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}