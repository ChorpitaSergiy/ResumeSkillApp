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

package com.example.resumeskillapp.data.mappers

import com.example.resumeskillapp.data.NetworkApi.PNG
import com.example.resumeskillapp.data.NetworkApi.POKEMON_IMAGE
import com.example.resumeskillapp.data.model.PokemonDetailsResponse
import com.example.resumeskillapp.data.model.PokemonResponse
import com.example.resumeskillapp.data.model.ResultResponse
import com.example.resumeskillapp.domain.model.PokemonData
import com.example.resumeskillapp.domain.model.PokemonDetails
import com.example.resumeskillapp.domain.model.Pokemon

fun PokemonResponse.toPokemonData() =
    PokemonData(
        count = count,
        next = next,
        previous = previous?.toString(),
        results = results.map { result -> result.toResult() }
    )

fun ResultResponse.toResult(): Pokemon {
    val id = url.substring(34, url.lastIndex).toInt()
    return Pokemon(
        id = id,
        name = name,
        url = url,
        imageUrl = "$POKEMON_IMAGE$id$PNG"
    )
}

fun PokemonDetailsResponse.toPokemonDetails() = PokemonDetails(
    id = id.toString(),
    base_experience = base_experience,
    height = height,
    name = name,
    firstAbility = abilities[0].ability.name,
    weight = weight,
    imageUrl = "$POKEMON_IMAGE$id$PNG"
)