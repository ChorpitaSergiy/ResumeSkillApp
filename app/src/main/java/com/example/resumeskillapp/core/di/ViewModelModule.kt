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

package com.example.resumeskillapp.core.di

import com.example.resumeskillapp.data.repository.PokemonRepositoryImpl
import com.example.resumeskillapp.domain.repository.PokemonRepository
import com.example.resumeskillapp.domain.usecase.PokemonUseCase
import com.example.resumeskillapp.presentation.home.HomeViewModel
import com.example.resumeskillapp.presentation.main.MainViewModel
import com.example.resumeskillapp.presentation.pokemon_details.PokemonDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // Repository
    factory<PokemonRepository> { PokemonRepositoryImpl(get(), get(), get()) }
    // ...

    // UseCase
    factory { PokemonUseCase(get()) }
    // ...

    // ViewModel
    viewModel { HomeViewModel(get()) }
    viewModel { PokemonDetailsViewModel() }
    viewModel { MainViewModel() }
    // ...
}