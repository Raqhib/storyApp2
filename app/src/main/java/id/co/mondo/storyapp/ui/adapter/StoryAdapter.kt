package id.co.mondo.storyapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.mondo.storyapp.databinding.ItemListBinding
import id.co.mondo.storyapp.data.network.response.ListStoryItem

class StoryAdapter(
    private val onItemClick: (ListStoryItem, ImageView, TextView) -> Unit
) : PagingDataAdapter<ListStoryItem, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

//    private val stories = mutableListOf<ListStoryItem>()
//
//    fun setStories(newStories: List<ListStoryItem>) {
//        Log.d("StoryAdapter", "Data diterima adapter: ${stories.size} cerita")
//        stories.clear()
//        stories.addAll(newStories)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
//        holder.bind(stories[position])
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

//    override fun getItemCount(): Int = stories.size

    inner class StoryViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.tvItemName.text = story.name
            binding.tvItemDeskripsi.text = story.description

            // Menggunakan Glide untuk memuat gambar
            Glide.with(binding.ivItemPhoto.context)
                .load(story.photoUrl)
                .into(binding.ivItemPhoto)

            binding.root.setOnClickListener {
                onItemClick(story, binding.ivItemPhoto, binding.tvItemName) // Panggil lambda dengan item yang diklik
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id // Bandingkan ID
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem // Bandingkan isi objek
            }
        }
    }
}
