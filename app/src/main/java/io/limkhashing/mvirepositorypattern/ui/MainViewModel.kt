package io.limkhashing.mvirepositorypattern.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.limkhashing.mvirepositorypattern.DataState
import io.limkhashing.mvirepositorypattern.models.Blog
import io.limkhashing.mvirepositorypattern.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    @ExperimentalCoroutinesApi
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogsEvent -> {
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                MainStateEvent.None -> {
                    // who cares
                }
            }
        }
    }

}

sealed class MainStateEvent {
    object GetBlogsEvent : MainStateEvent()
    object None : MainStateEvent()
}
