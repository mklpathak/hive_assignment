package com.example.android.hive_assignment.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.hive_assignment.database.RestrauntDao
import com.example.android.hive_assignment.model.Restaurant
import com.example.android.hive_assignment.model.ZomatoResponse
import com.example.android.hive_assignment.network.ZomatoApi
import com.example.android.hive_assignment.network.ZomatoFilters
import kotlinx.coroutines.*

enum class ZomatoApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    var filters: ZomatoFilters? = null

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ZomatoApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ZomatoApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of Zomato Requests
    // with new values
    private val _properties = MutableLiveData<List<Restaurant?>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<Restaurant?>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Restaurant>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Restaurant>
        get() = _navigateToSelectedProperty

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getRestarauntList() on init so we can display status immediately.
     */
    init {
        getRestarauntList("Indian", null)
    }

    /**
     * Gets filtered Zomato restraunt information from the Zomato API Retrofit service and
     * updates the [Restaurant] [List] and [ZomatoApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [ZomatoFilters] that is sent as part of the web server request
     */
    private fun getRestarauntList(searchQuery: String, filters: ZomatoFilters?) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getPropertiesDeferred: Deferred<ZomatoResponse>? = null
            if (filters != null)
                getPropertiesDeferred = ZomatoApi.RETROFIT_SERVICE.getRestrauntBySorting(searchQuery, filters.value, "asc")
            else
                getPropertiesDeferred = ZomatoApi.RETROFIT_SERVICE.getRestraunt(searchQuery)
            try {
                _status.value = ZomatoApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = ZomatoApiStatus.DONE
                if (filters == null) {
                    _properties.value = listResult.restaurants.sortedWith(compareBy { it!!.restaurant!!.cuisines })
                } else {

                    _properties.value = listResult.restaurants


                }
            } catch (e: Exception) {
                Log.e("error", e.message)
                _status.value = ZomatoApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param restaurant The [Restaurant] that was clicked on.
     */
    fun displayPropertyDetails(restaurant: Restaurant) {
        _navigateToSelectedProperty.value = restaurant
    }


    fun saveOffline(database: RestrauntDao, restaurant: Restaurant) {

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.insert(restraunt = restaurant)
            }
        }


        // _navigateToSelectedProperty.value = restaurant
    }


    fun applyFilters(filters: ZomatoFilters?) {
        this.filters = filters
    }

    fun offlineMode(database: RestrauntDao) {


        coroutineScope.launch {
            try {
                _status.value = ZomatoApiStatus.LOADING
                _properties.value = getAllOfflineRestraunts(database)
                _status.value = ZomatoApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ZomatoApiStatus.ERROR

                Log.e("Error", e.message)
            }
        }


    }

    fun onlineMode() {
        getRestarauntList("Cafe", filters)

    }

    private suspend fun getAllOfflineRestraunts(database: RestrauntDao): List<Restaurant> {

        return withContext(Dispatchers.IO) {
            database.getAllRestraunts()
        }

    }

    fun removeOffline(database: RestrauntDao, restaurant: Restaurant) {

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                database.delete(id = restaurant.restaurant?.id!!)
            }
        }


        // _navigateToSelectedProperty.value = restaurant
    }


    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


    fun searchRestraunt(searchQuery: String) {

        getRestarauntList(searchQuery, filters)
    }

}