/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
// Create a MarsApiStatus enum with the LOADING, ERROR, and DONE states
enum class MarsApiStatus { LOADING, ERROR, DONE }
/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // Change _status to type MarsApiStatus
    // Rename response LiveData to Status
    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Update the ViewModel to return a LiveData of List<MarsProperty>
    // Add the LiveData MarsProperty property with an internal Mutable and an external LiveData
    private val _properties = MutableLiveData<List<MarsProperty>>()

    val properties: LiveData<List<MarsProperty>>
    get() = _properties
    // TODO (05) Add a _navigateToSelectedProperty MutableLiveData externalized as LiveData
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
    get() =  _navigateToSelectedProperty

    // Create a coroutine Job and a CoroutineScope using the Main Dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    // Update get MarsRealEstateProperties to handle List<MarsProperty>
    private fun getMarsRealEstateProperties() {
        // Set the correct for LOADING, ERROR and DONE
        // Call the MarsApi to enqueue the Retrofit request, implementing the callbacks
        // Call coroutinesScope.launch and place the rest of the code in it
        coroutineScope.launch {
            // Call MarsApi.retrofitService.getProperties and call await on the Defered
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties()
            // Surround the Retrofit code with a try/catch, and set _response appropriately
            try {
                _status.value = MarsApiStatus.LOADING

                var listResult = getPropertiesDeferred.await()
                // Update to set _property to the first MarsProperty from ListResult
                   _status.value = MarsApiStatus.DONE
                   _properties.value = listResult
            } catch (e : Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }
    // Cancel the Coroutine Job when the ViewModel id finished in onCleared
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // TODO (06) Add displayPropertyDetails and displayPropertyDetailsComplete methods
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}
