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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.resumeskillapp.core.base.BaseViewModel
import com.example.resumeskillapp.domain.model.Pokemon
import com.example.resumeskillapp.domain.model.PokemonDetails
import com.example.resumeskillapp.domain.usecase.PokemonUseCase

class HomeViewModel(private val pokemonUseCase: PokemonUseCase) : BaseViewModel() {

    private val pokemonLiveData = MutableLiveData<List<Pokemon>>()
    fun getPokemonLiveData(): LiveData<List<Pokemon>> = pokemonLiveData

    private val pokemonDetailsLiveData = MutableLiveData<PokemonDetails>()
    fun getPokemonDetailsLiveData(): LiveData<PokemonDetails> = pokemonDetailsLiveData

    private val showRefreshLayout = MutableLiveData<Boolean>()
    fun getShowRefreshLayout(): LiveData<Boolean> = showRefreshLayout

    init { getPokemonFromDB() }

    private fun getPokemonFromDB() = pokemonUseCase
        .getPokemonFromDb().getWith(pokemonLiveData)

    fun getPokemonFromNetwork(limit: Int, offset: Int) =
        pokemonUseCase.getPokemon(limit, offset).getInfo(
            doOnComplete = { showRefreshLayout.value = false })

    fun getPokemonDetails(id: String) = pokemonUseCase
        .getPokemonDetails(id).getWith(pokemonDetailsLiveData, doOnError = {
            pokemonUseCase.getPokemonDetailsFromDB(id).getWith(pokemonDetailsLiveData)
        })

}