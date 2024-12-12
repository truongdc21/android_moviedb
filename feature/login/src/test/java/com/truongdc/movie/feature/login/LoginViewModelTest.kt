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
package com.truongdc.movie.feature.login

import com.truongdc.movie.core.model.User
import com.truongdc.movie.core.testing.repository.TestMainRepository
import com.truongdc.movie.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

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
class LoginViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val mainRepository = TestMainRepository()

    private lateinit var viewModel: LoginViewModel

    private val uiState: LoginViewModel.UiState
        get() = viewModel.uiStateFlow.value

    @Before
    fun setup() {
        viewModel = LoginViewModel(mainRepository = mainRepository)
    }

    @Test
    fun `onEmailChange updates email in UiState`() = runTest {
        val testEmail = "test@example.com"

        viewModel.onEmailChange(testEmail)

        assert(uiState.email == testEmail)
    }

    @Test
    fun `onPassChange updates password and checks validity`() = runTest {
        val testPass = "123456"

        viewModel.onPassChange(testPass)

        assert(uiState.pass == testPass)
        assert(uiState.isInValid) // Assume email isn't valid yet
    }

    @Test
    fun `onUpdateTextFieldFocus updates focus state`() = runTest {
        viewModel.onUpdateTextFiledFocus(true)

        assert(uiState.isTextFieldFocused)
    }

    @Test
    fun `onSubmitLogin emits LoginSuccess when credentials match`() = runTest {
        val email = "valid@example.com"
        val pass = "validpass"

        // Prepare mock data in repository
        mainRepository.saveUser(
            User(
                name = "AAAA",
                email = email,
                password = pass,
            ),
        )
        // Trigger login action
        viewModel.onSubmitLogin(email, pass)

        // Collect only the first event
        val event = viewModel.singleEvents.first()
        assertTrue(event is LoginViewModel.Event.LoginSuccess)
    }

    @Test
    fun `onSubmitLogin emits LoginFailed when credentials do not match`() = runTest {
        val email = "wrong@example.com"
        val pass = "wrongpass"

        // Prepare mock data in repository
        mainRepository.saveUser(
            User(
                name = "AAAA",
                email = email,
                password = pass,
            ),
        )

        // Trigger login action
        viewModel.onSubmitLogin("valid@example.com", "validpass")

        // Collect only the first event
        val event = viewModel.singleEvents.first()
        assertTrue(event is LoginViewModel.Event.LoginFailed)
    }

    @Test
    fun `checkInvalid updates isInValid in UiState`() = runTest {
        viewModel.onEmailChange("short")
        viewModel.onPassChange("short")

        assert(uiState.isInValid)

        viewModel.onEmailChange("validEmail@example.com")
        viewModel.onPassChange("validPass")

        assert(!uiState.isInValid)
    }
}
