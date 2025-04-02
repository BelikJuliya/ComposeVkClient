package com.example.composeapp.ui.vk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeapp.domain.BaseModel

abstract class BaseViewModel<T: BaseModel>(
    private val itemFactory: (Int) -> T
) : ViewModel() {

    // Инициализация списка через фабрику
    private val initialList = List(50) { index ->
        itemFactory(index)
    }

    private val _items = MutableLiveData(initialList)
    val items: LiveData<List<T>> = _items

    fun updateItems(newItems: List<T>) {
        _items.value = newItems
    }

    fun deleteItem(model: T) {
        val newItems = _items.value?.toMutableList() ?: mutableListOf()
        newItems.remove(model)
        _items.value = newItems
    }
}