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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

// Create PhotoGridAdapter that expands the RecyclerView ListAdapter with DiffCallback
// TODO (08) Have PhotoGridAdapter take the onClickListener class as a constructor property parameter
class PhotoGridAdapter(val onClickListener: OnClickListener) : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback){
    // Create and implement the MarsPropertyViewHolder inner class
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
fun bind(marsProperty: MarsProperty) {
    binding.property = marsProperty
    binding.executePendingBindings()
}
    }
    // Add unimplemented members for PhotoGridAdapter, create and implement DiffCallback companion
    companion object DiffCallback:DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }

    // Override and implement onCreateViewHolder and onBindViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): PhotoGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        // TODO (09) Call in the onClick function from the onClickListener in a lambda from setOnClickListener
        holder.itemView.setOnClickListener{
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    // TODO (07) Create an ClickListener class with a lambda in its constructor that initializes a marsProperty parameter and contains
    class OnClickListener(val clickListener: (marsProperty:MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }

}



