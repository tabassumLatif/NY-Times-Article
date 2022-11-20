package com.tabi.nytimes.ui.artical.fragments.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tabi.nytimes.databinding.FragmentArticlesBinding
import com.tabi.nytimes.ui.artical.viewModel.ArticlesViewModel
import com.tabi.nytimes.ui.artical.fragments.articles.adapter.ArticleAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding

    @Inject
    lateinit var articleAdapter: ArticleAdapter
    private val articlesViewModel: ArticlesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerViewAdapter()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = articlesViewModel

        onArticleClickListener()
        initObservers()

    }

    private fun initObservers() {
        articlesViewModel.refreshArticles.observe(viewLifecycleOwner) {
            if (articlesViewModel.isMenuClicked) {
                articlesViewModel.isMenuClicked = false
                articlesViewModel.isLoading.postValue(true)
                articlesViewModel.isResultFound.postValue(false)
                binding.rvArticles.visibility = View.GONE
                articlesViewModel.getArticles(it)
            }
        }
    }

    private fun initializeRecyclerViewAdapter() {
        binding.rvArticles.adapter = articleAdapter
    }

    private fun onArticleClickListener() {
        articleAdapter.onItemClick = {
            articlesViewModel.clickedArticle.postValue(it)
            val detailFragmentDirection =
                ArticlesFragmentDirections.actionArticleFragmentToDetailFragment()
            findNavController().navigate(detailFragmentDirection)
        }
    }

}