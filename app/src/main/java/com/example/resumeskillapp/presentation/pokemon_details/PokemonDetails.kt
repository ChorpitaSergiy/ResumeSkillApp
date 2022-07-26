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

package com.example.resumeskillapp.presentation.pokemon_details

import com.example.resumeskillapp.R
import com.example.resumeskillapp.core.base.BaseFragment
import com.example.resumeskillapp.core.utils.loadPicture
import com.example.resumeskillapp.core.utils.viewBinding
import com.example.resumeskillapp.databinding.FragmentPokemonDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PokemonDetails : BaseFragment(R.layout.fragment_pokemon_details) {

    private val binding: FragmentPokemonDetailsBinding by viewBinding()
    private val viewModel: PokemonDetailsViewModel by viewModel()

    override fun initViews() {
        super.initViews()

        val pokemonDetails = PokemonDetailsArgs
            .fromBundle(requireArguments()).pokemonDetails

        with(binding) {
            icon.loadPicture(pokemonDetails.imageUrl)
            name.text = pokemonDetails.name
            weight.text = pokemonDetails.weight.toString().plus(getString(R.string.kg))
            height.text = pokemonDetails.height.toString().plus(getString(R.string.ft))
            firstAbility.text = pokemonDetails.firstAbility
            experience.text = pokemonDetails.base_experience.toString()
        }
    }

}