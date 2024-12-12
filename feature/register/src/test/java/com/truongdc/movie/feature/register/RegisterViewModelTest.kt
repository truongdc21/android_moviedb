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
package com.truongdc.movie.feature.register

import com.truongdc.movie.core.model.User
import com.truongdc.movie.core.testing.repository.TestMainRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
class RegisterViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val mainRepository = TestMainRepository()

    private lateinit var viewModel: RegisterViewModel

    private val uiState: RegisterViewModel.UiState
        get() = viewModel.uiStateFlow.value

    @Before
    fun setup() {
        viewModel = RegisterViewModel(mainRepository = mainRepository)
    }

    @Test
    fun `onNameChange updates name and checks validity`() = runTest {
        val name = "test"

        viewModel.onNameChange(name)

        assert(uiState.name == name)
        assertTrue(uiState.isInValid)
    }

    @Test
    fun `onEmailChange updates email and checks validity`() = runTest {
        val email = "test"

        viewModel.onEmailChange(email)

        assert(uiState.email == email)
        assertTrue(uiState.isInValid)
    }

    @Test
    fun `onPassChange updates pass and checks validity`() = runTest {
        val pass = "test"

        viewModel.onPassChange(pass)

        assert(uiState.pass == pass)
        assertTrue(uiState.isInValid)
    }

    @Test
    fun `onUpdateTextFiledFocus and test check update on UiState`() = runTest {
        val isFocus = true
        viewModel.onUpdateTextFiledFocus(isFocus)
        assert(uiState.isTextFieldFocused == isFocus)
    }

    @Test
    fun `checkInvalid updates isInValid in UiState`() = runTest {
        viewModel.onNameChange("short")
        viewModel.onEmailChange("short")
        viewModel.onPassChange("short")

        assertTrue(uiState.isInValid)

        viewModel.onNameChange("Hello word")
        viewModel.onEmailChange("truongdc21@")
        viewModel.onPassChange("123456")

        assertFalse(uiState.isInValid)
    }

    @Test
    fun `onSubmitRegister emits RegisterSuccess when credentials match`() = runTest {
        val email = "valid@example.com"
        val pass = "validpass"

        val user = User(
            name = "AAAA",
            email = email,
            password = pass,
        )
        // Prepare mock data in repository
        mainRepository.saveUser(user = user)

        // Trigger register action
        viewModel.onSubmitRegister(user = user)

        // Collect only the first event
        val event = viewModel.singleEvents.first()
        assertTrue(event is RegisterViewModel.Event.RegisterSuccess)
    }
}
