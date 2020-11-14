package io.github.iamraf.jokes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.iamraf.jokes.R
import io.github.iamraf.jokes.databinding.ItemJokeBinding
import io.github.iamraf.jokes.domain.entity.Joke

class JokesAdapter : ListAdapter<Joke, JokesAdapter.JokeViewHolder>(DiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemJokeBinding.inflate(inflater, parent, false)

        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class JokeViewHolder(private val binding: ItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                binding.expand.callOnClick()
            }
        }

        fun bind(item: Joke) {
            with(binding) {
                category.text = item.category
                setup.text = item.setup
                delivery.text = item.delivery

                expand.setOnClickListener {
                    if (layout.visibility == View.GONE) {
                        layout.visibility = View.VISIBLE
                        expand.setImageResource(R.drawable.ic_baseline_expand_less_24)
                    } else {
                        layout.visibility = View.GONE
                        expand.setImageResource(R.drawable.ic_baseline_expand_more_24)
                    }
                }
            }
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem == newItem
        }
    }
}