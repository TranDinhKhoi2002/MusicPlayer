package pj.tdk.musicplayer.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pj.tdk.musicplayer.activities.SongViewActivity
import pj.tdk.musicplayer.databinding.ItemMusicBinding
import pj.tdk.musicplayer.models.MusicModel


class MusicAdapter(private val items: ArrayList<MusicModel>) :
    RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onClick(view: View?, position: Int, isLongClick: Boolean)
    }

    inner class ViewHolder(val binding: ItemMusicBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener, View.OnLongClickListener {
        private lateinit var itemClickListener: ItemClickListener

        fun bindItem(song: MusicModel) {
            binding.imgAvatar.setImageResource(song.img)
            binding.txtSong.text = song.song
            binding.txtSinger.text = song.singer

            binding.llComponent.setOnClickListener(this)
            binding.llComponent.setOnLongClickListener(this)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this@ViewHolder.itemClickListener = itemClickListener
        }

        override fun onClick(view: View?) {
            //TODO("Not yet implemented")
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        override fun onLongClick(view: View?): Boolean {
            //TODO("Not yet implemented")
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
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

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                //TODO("Not yet implemented")
                val intent: Intent = Intent(view?.context, SongViewActivity::class.java)

                val bundle: Bundle = Bundle()
                bundle.putString("song", items.get(position).song)
                bundle.putString("singer", items.get(position).singer)
                intent.putExtras(bundle)

                view?.context?.startActivity(intent, bundle)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }
}