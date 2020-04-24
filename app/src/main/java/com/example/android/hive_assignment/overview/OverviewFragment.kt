package com.example.android.hive_assignment.overview

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.hive_assignment.R
import com.example.android.hive_assignment.database.RestrauntDao
import com.example.android.hive_assignment.database.RestrauntDataBase
import com.example.android.hive_assignment.databinding.FragmentOverviewBinding
import com.example.android.hive_assignment.model.Restaurant
import com.example.android.hive_assignment.network.ZomatoFilters
import com.google.android.material.snackbar.Snackbar


/**
 * This fragment shows the the status of the Zomato Restraunts.
 */
class OverviewFragment : Fragment() {

    private lateinit var dataSource: RestrauntDao
    private var filters: ZomatoFilters? = null
    var menu: Menu? = null
    var rootView: View? = null


    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProviders.of(this).get(OverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        rootView = binding.root


        val application = requireNotNull(this.activity).application
        //Requires Application context to open database for saving offline restraunts
        dataSource = RestrauntDataBase.getInstance(application).databaseDao


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel


        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.photosGrid.adapter = RestrauntsAdaptor(RestrauntsAdaptor.OnClickListener {
            viewModel.displayPropertyDetails(it)
        }, object : RestrauntsAdaptor.OnSaveListner {
            override fun onSave(restaurant: Restaurant, isSaved: Boolean) {
                if (isSaved) {
                    viewModel.saveOffline(dataSource, restaurant)

                    Snackbar.make(rootView!!, "Saving Offline", Snackbar.LENGTH_SHORT).show()
                } else {

                    viewModel.removeOffline(dataSource, restaurant)
                    Snackbar.make(rootView!!, "Removing from offline", Snackbar.LENGTH_SHORT).show()

                }
            }


        })

        waitForTransition(binding.photosGrid)


        //commented as navigating moved into adaptor itself for shared element transition
        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
//        viewModel.navigateToSelectedProperty.observe(this, Observer {
//            if ( null != it ) {
//
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                viewModel.displayPropertyDetailsComplete()
//            }
//        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        this.menu = menu


        // Associate searchable configuration with the SearchView
        val searchManager = context!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText.length > 3)
                        viewModel.searchRestraunt(newText)

                    return false
                }

            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    /**
     * Updates the filter in the [OverviewViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.action_offline -> {
                item.isChecked = !item.isChecked
                if (item.isChecked) {
                    menu?.let {

                        Snackbar.make(rootView!!, "Switching to Offline Mode", Snackbar.LENGTH_SHORT).show()


                        it.findItem(R.id.action_search).isVisible = false
                        it.findItem(R.id.by_cost).isVisible = false
                        it.findItem(R.id.by_rating).setVisible(false)


                    }
                    dataSource.let {
                        viewModel.offlineMode(it)

                    }
                } else {
                    menu?.let {

                        Snackbar.make(rootView!!, "Switching to Online Mode", Snackbar.LENGTH_SHORT).show()


                        it.findItem(R.id.action_search).isVisible = true
                        it.findItem(R.id.by_cost).isVisible = true
                        it.findItem(R.id.by_rating).setVisible(true)


                    }

                    viewModel.onlineMode()

                }

            }
            R.id.by_cost -> {

                item.isChecked = !item.isChecked
                menu?.let {

                    Snackbar.make(rootView!!, "Sorting by Price", Snackbar.LENGTH_SHORT).show()
                    it.findItem(R.id.by_rating).isChecked = false
                }

                if (item.isChecked)
                    filters = ZomatoFilters.PRICE
                else filters = null


            }
            R.id.by_rating -> {
                Snackbar.make(rootView!!, "Sorting by Rating", Snackbar.LENGTH_SHORT).show()

                item.isChecked = !item.isChecked
                menu?.let {
                    it.findItem(R.id.by_cost).isChecked = false
                }
                filters = ZomatoFilters.RATINGS

                if (item.isChecked)
                    filters = ZomatoFilters.PRICE
                else filters = null


            }
        }

        viewModel.applyFilters(filters)


        return false
    }


    fun Fragment.waitForTransition(targetView: View) {
        postponeEnterTransition()
        targetView.doOnPreDraw { startPostponedEnterTransition() }
    }

}
