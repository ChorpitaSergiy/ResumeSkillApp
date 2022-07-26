/*
 *  MIT License
 *
 *  Copyright (C) 2022 Serhii Chorpita
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.resumeskillapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.resumeskillapp.R
import com.example.resumeskillapp.core.utils.loadPicture
import com.example.resumeskillapp.databinding.ItemPokemonBinding
import com.example.resumeskillapp.domain.model.Pokemon

class PokemonAdapter(private val callback: (Pokemon) -> Unit):
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val items:MutableList<Pokemon> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = DataBindingUtil.inflate<ItemPokemonBinding>(LayoutInflater
            .from(parent.context), R.layout.item_pokemon, parent,false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pokemon) {
            with(binding) {
                pokemonTitle.text = item.name
                pokemonIcon.loadPicture(item.imageUrl)
                root.setOnClickListener { callback.invoke(item) }
                itemView.animation = AnimationUtils
                    .loadAnimation(itemView.context,
                        R.anim.fade_scale_top_animation)
            }
        }
    }

    fun setDataList(pokemonItems: List<Pokemon>) {
        items.clear()
        items.addAll(pokemonItems)
        notifyDataSetChanged()
    }
}