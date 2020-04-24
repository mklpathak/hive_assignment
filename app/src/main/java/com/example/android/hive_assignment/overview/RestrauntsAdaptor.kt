package com.example.android.hive_assignment.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hive_assignment.databinding.GridViewItemBinding
import com.example.android.hive_assignment.model.Restaurant


/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class RestrauntsAdaptor(val onClickListener: OnClickListener, val onSaveListner: OnSaveListner) :
        ListAdapter<Restaurant, RestrauntsAdaptor.RestrauntViewHolder>(DiffCallback) {

    private var headerContent = HashMap<String, Int>()

    /**
     * The RestrauntViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Restaurant] information.
     */
    class RestrauntViewHolder(private var binding: GridViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant, isHeader: Boolean, onSaveListne: OnSaveListne) {
            binding.property = restaurant
            binding.isHeader = isHeader
            binding.sa = onSaveListne

            binding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                        binding.imageView.toTransitionGroup(),
                        binding.rating.toTransitionGroup(),
                        binding.textView2.toTransitionGroup(),
                        binding.textView3.toTransitionGroup(),
                        binding.textView4.toTransitionGroup(),
                        binding.textView5.toTransitionGroup()


                )

                it.findNavController().navigate(OverviewFragmentDirections.actionShowDetail(restaurant), extras)
            }


            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Restaurant]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.restaurant!!.id == newItem.restaurant!!.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RestrauntViewHolder {

        return RestrauntViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RestrauntViewHolder, position: Int) {
        val restaurant = getItem(position)

        //Removed for support for shared element transistions
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(restaurant)
//        }
        var cusines = restaurant.restaurant?.cuisines



        if (headerContent.containsKey(cusines) &&
                headerContent[restaurant.restaurant?.cuisines] != position
        ) {
            holder.bind(restaurant, false, object : OnSaveListne {
                override fun onSave(isChecked: Boolean) {
                    onSaveListner.onSave(restaurant, isChecked)

                }
            })
        } else {
            holder.bind(restaurant, true, object : OnSaveListne {
                override fun onSave(isChecked: Boolean) {
                    onSaveListner.onSave(restaurant, isChecked)
                }
            })

            cusines?.let {
                headerContent[cusines] = position

            }
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Restaurant]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Restaurant]
     */
    class OnClickListener(val clickListener: (restaurant: Restaurant) -> Unit) {
        fun onClick(restaurant: Restaurant) = clickListener(restaurant)
    }

    interface OnSaveListner {
        fun onSave(restaurant: Restaurant, isSaved: Boolean)
    }


}


interface OnSaveListne {
    fun onSave(restaurant: Boolean)
}


fun View.toTransitionGroup() = this to transitionName

