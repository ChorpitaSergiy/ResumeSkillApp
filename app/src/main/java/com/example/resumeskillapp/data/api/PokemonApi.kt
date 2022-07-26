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

package com.example.resumeskillapp.data.api

import com.example.resumeskillapp.data.NetworkApi.LIMIT
import com.example.resumeskillapp.data.NetworkApi.OFFSET
import com.example.resumeskillapp.data.NetworkApi.POKEMON
import com.example.resumeskillapp.data.NetworkApi.POKEMON_DETAILS
import com.example.resumeskillapp.data.NetworkApi.POKEMON_ID
import com.example.resumeskillapp.data.model.PokemonDetailsResponse
import com.example.resumeskillapp.data.model.PokemonResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET(POKEMON)
    fun getPokemon(@Query(LIMIT) limit: String,
                   @Query(OFFSET) offset: Int) : Single<PokemonResponse>

    @GET(POKEMON_DETAILS)
    fun getPokemonDetails(@Path(POKEMON_ID) id: String) : Single<PokemonDetailsResponse>

}