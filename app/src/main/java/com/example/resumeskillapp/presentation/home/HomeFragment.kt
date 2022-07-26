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

package com.example.resumeskillapp.presentation.home

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.baoyz.widget.PullRefreshLayout
import com.example.resumeskillapp.R
import com.example.resumeskillapp.core.base.BaseFragment
import com.example.resumeskillapp.core.utils.getCurrentTheme
import com.example.resumeskillapp.core.utils.observeData
import com.example.resumeskillapp.core.utils.viewBinding
import com.example.resumeskillapp.databinding.FragmentHomeBinding
import com.example.resumeskillapp.domain.model.Pokemon
import com.example.resumeskillapp.presentation.home.adapter.PokemonAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private var adapter = PokemonAdapter(::onClick)

    override fun initViews() {
        super.initViews()
        if (binding.rvPokemon.adapter == null)
            binding.rvPokemon.adapter = adapter

        binding.swipeRefreshLayout.apply {
            setRefreshStyle(PullRefreshLayout.STYLE_SMARTISAN)
            setOnRefreshListener { viewModel.getPokemonFromNetwork(LIMIT, OFFSET) }
            requireContext().getCurrentTheme({ setColor(Color.WHITE) }, { setColor(Color.BLACK) })
        }
    }

    private fun onClick(resultModel: Pokemon) {
        viewModel.getPokemonDetails(resultModel.id.toString())
    }

    override fun observeLiveData() {
        super.observeLiveData()
        observeData(viewModel.getPokemonLiveData(), adapter::setDataList)
        observeData(viewModel.getPokemonDetailsLiveData()) {
            findNavController().navigate(HomeFragmentDirections
                .actionHomeFragmentToPokemonDetails(it))
        }
        observeData(viewModel.getShowRefreshLayout()) {
            binding.swipeRefreshLayout.setRefreshing(it)
        }
        viewModel.getPokemonFromNetwork(LIMIT, OFFSET)
    }

    companion object {
        const val OFFSET = 0
        const val LIMIT = 100
    }

}