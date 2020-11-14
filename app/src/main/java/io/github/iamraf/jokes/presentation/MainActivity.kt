package io.github.iamraf.jokes.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import io.github.iamraf.jokes.R
import io.github.iamraf.jokes.databinding.ActivityMainBinding
import io.github.iamraf.jokes.domain.entity.Joke
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: JokesViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val jokesAdapter: JokesAdapter by lazy { JokesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)

        setContentView(binding.root)

        setupAppBar()
        setupViews()
        setupObservers()
    }

    private fun setupAppBar() {
        with(binding) {
            setSupportActionBar(toolbar)
        }
    }

    private fun setupViews() {
        with(binding) {
            recycler.apply {
                setHasFixedSize(true)
                adapter = jokesAdapter
                itemAnimator = FadeInDownAnimator()
            }

            swipe.apply {
                setColorSchemeColors(ContextCompat.getColor(context, R.color.white))
                setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context, R.color.background))
            }

            swipe.setOnRefreshListener {
                jokesAdapter.submitList(null)
                viewModel.fetchJokes()
                swipe.isRefreshing = false
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            jokes.observe(this@MainActivity, {
                updateJokes(it)
            })

            loading.observe(this@MainActivity, {
                updateProgress(it)
            })

            error.observe(this@MainActivity, {
                showError(it)
            })

            fetchJokes()
        }
    }

    private fun updateJokes(jokes: List<Joke>) {
        jokesAdapter.submitList(jokes)

        binding.recycler.isVisible = true
    }

    private fun updateProgress(flag: Boolean) {
        when (flag) {
            true -> {
                binding.recycler.isVisible = false
                binding.error.isVisible = false
                binding.progress.isVisible = true
            }
            false -> binding.progress.isVisible = false
        }
    }

    private fun showError(message: String) {
        binding.error.text = message
        binding.recycler.isVisible = false
        binding.error.isVisible = true
    }
}