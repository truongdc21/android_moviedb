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
package com.truongdc.movie.core.data.testdoubles

import com.truongdc.movie.core.datastore.UserDataSource
import com.truongdc.movie.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestUserDataSource : UserDataSource {

    private val user = User(
        name = "",
        email = "",
        password = "",
    )

    // In-memory storage to simulate UserDataStore
    private val userFlow = MutableStateFlow(user)

    override suspend fun saveUser(user: User) {
        userFlow.value = user
    }

    override fun getUser(): Flow<User> = userFlow

    override suspend fun clearAll() {
        userFlow.value = this.user
    }
}
