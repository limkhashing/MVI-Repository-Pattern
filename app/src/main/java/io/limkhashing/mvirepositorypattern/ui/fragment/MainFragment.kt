// If you have to setup actionbar with navcontroller then FragmentContainerView will not
// be able to find the associated NavController.
// In order to resolve this issue, simply replace it with <fragment> and
// find the controller traditionally like
// navController = findNavController(R.id.default_navHost_fragment)
// Bug Report: https://issuetracker.google.com/issues/142847973
package io.limkhashing.mvirepositorypattern.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.limkhashing.mvirepositorypattern.DataState
import io.limkhashing.mvirepositorypattern.R
import io.limkhashing.mvirepositorypattern.models.Blog
import io.limkhashing.mvirepositorypattern.ui.MainStateEvent
import io.limkhashing.mvirepositorypattern.ui.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment
constructor(
    private val someString: String
): Fragment(R.layout.main_fragment) {

    private val TAG: String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogsEvent)

        Log.d(TAG, "Hey look! $someString")
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?){
        if(message != null) text.text = message else text.text = "Unknown error."
    }

    private fun appendBlogTitles(blogs: List<Blog>){
        val sb = StringBuilder()
        for(blog in blogs){
            sb.append(blog.title + "\n")
        }
        text.text = sb.toString()
    }

    private fun displayProgressBar(isDisplayed: Boolean){
        progress_bar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }
}
