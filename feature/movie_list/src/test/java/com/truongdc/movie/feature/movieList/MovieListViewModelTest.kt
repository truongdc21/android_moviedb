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
package com.truongdc.movie.feature.movieList

import com.truongdc.movie.core.testing.data.movieTestData
import com.truongdc.movie.core.testing.repository.TestMovieRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertNotNull

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
class MovieListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val movieRepository = TestMovieRepository()

    private lateinit var viewModel: MovieListViewModel

    private val uiState: MovieListViewModel.UiState
        get() = viewModel.uiStateFlow.value

    @Before
    fun setup() {
        viewModel = MovieListViewModel(movieRepository = movieRepository)
    }

    @Test
    fun `lazyGridState is initialized`() = runTest {
        // Assert first visible item index and scroll offset should be 0
        assertEquals(0, uiState.lazyGridState.firstVisibleItemIndex)
        assertEquals(0, uiState.lazyGridState.firstVisibleItemScrollOffset)
    }

    @Test
    fun `requestMovie updates UiState with flow paging data is not null`() = runTest {
        // Prepare test data
        val testMovies = movieTestData

        // Send movies to the repository
        movieRepository.sendMovies(testMovies)

        // Assert
        assertNotNull(uiState.flowPagingMovie)
    }
}
