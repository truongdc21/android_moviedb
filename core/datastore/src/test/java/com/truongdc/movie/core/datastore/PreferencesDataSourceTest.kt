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
package com.truongdc.movie.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.truongdc.movie.core.datastore.fakes.FakeDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertFalse

class PreferencesDataSourceTest {

    private lateinit var preferencesDataSource: PreferencesDataSource

    private lateinit var fakeDataStore: FakeDataStore<Preferences>

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        fakeDataStore = FakeDataStore(emptyPreferences())
        preferencesDataSource = PreferencesDataSourceImpl(
            dataStore = fakeDataStore,
        )
    }

    @Test
    fun setIsLogIn_should_success() = testScope.runTest {
        preferencesDataSource.setIsLogIn(true)

        // Assert
        assertEquals(true, preferencesDataSource.isLogIn.first())
    }

    @Test
    fun setIsLogIn_should_failure() = testScope.runTest {
        preferencesDataSource.setIsLogIn(false)

        // Assert
        assertFalse(preferencesDataSource.isLogIn.first())
    }

    @Test
    fun isLogIn_should_return_false_by_default() = testScope.runTest {
        // Assert
        assertEquals(false, preferencesDataSource.isLogIn.first())
    }
}
