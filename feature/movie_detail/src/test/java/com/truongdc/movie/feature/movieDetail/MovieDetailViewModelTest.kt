/*
 * Designed and developed by 2024 truongdc21 (Dang Chi Truong)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongdc.movie.feature.movieDetail

import androidx.lifecycle.SavedStateHandle
import com.truongdc.movie.core.testing.data.movieTestData
import com.truongdc.movie.core.testing.repository.TestMovieRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

/**
 * To learn more about how this test handles Flows created with stateIn, see
 * https://developer.android.com/kotlin/flow/test#statein
 *
 * These tests use Robolectric because the subject under test (the ViewModel) uses
 * `SavedStateHandle.toRoute` which has a dependency on `android.os.Bundle`.
 *
 * TODO: Remove Robolectric if/when AndroidX Navigation API is updated to remove Android dependency.
 *  *  See b/340966212.
 */
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val movieRepository = TestMovieRepository()

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    private val uiState: MovieDetailViewModel.UiState
        get() = viewModel.uiStateFlow.value

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle(mapOf("movie" to movieTestData[0]))
        viewModel = MovieDetailViewModel(
            movieRepository = movieRepository,
            stateHandle = savedStateHandle,
        )
    }

    @Test
    fun `initializes correctly with movie id from SavedStateHandle`() {
        // Assert
        assertEquals(movieTestData[0].id, uiState.movieId)
    }

    @Test
    fun `requests movie details and updates UI state on success`() = runTest {
        // Arrange
        val expectedMovie = movieTestData[0]
        movieRepository.sendMovies(movies = listOf(expectedMovie))

        // Act
        viewModel.requestMovie(uiState.movieId)
        advanceUntilIdle()

        // Assert
        assertEquals(expectedMovie, uiState.movie)
    }
}
