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

package com.example.resumeskillapp.data.repository

import com.example.resumeskillapp.data.api.PokemonApi
import com.example.resumeskillapp.data.dao.PokemonDao
import com.example.resumeskillapp.data.dao.PokemonDetailsDao
import com.example.resumeskillapp.data.mappers.toPokemonData
import com.example.resumeskillapp.data.mappers.toPokemonDetails
import com.example.resumeskillapp.domain.model.Pokemon
import com.example.resumeskillapp.domain.model.PokemonData
import com.example.resumeskillapp.domain.model.PokemonDetails
import com.example.resumeskillapp.domain.repository.PokemonRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonDao: PokemonDao,
    private val pokemonDetailsDao: PokemonDetailsDao
): PokemonRepository {

    override fun getPokemon(limit: Int, offset: Int): Single<PokemonData> =
        pokemonApi.getPokemon(limit.toString(), offset).map {
            it.toPokemonData().also { items -> savePokemonToDB(items.results).subscribe() }
        }

    override fun getPokemonDetails(id: String): Single<PokemonDetails> =
        pokemonApi.getPokemonDetails(id).map {
            it.toPokemonDetails().also { item -> savePokemonDetailsToDB(item).subscribe() }
        }

    override fun getPokemonFromDB(): Flowable<List<Pokemon>> =
        pokemonDao.getItems()
            .map { it.sortedBy { pokemon -> pokemon.name } }

    override fun savePokemonToDB(items: List<Pokemon>) = pokemonDao.insertItems(items)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun savePokemonDetailsToDB(item: PokemonDetails) = pokemonDetailsDao.insertItem(item)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getPokemonDetailsFromDB(id: String) = pokemonDetailsDao.getItem(id)

}