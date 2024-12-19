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

import androidx.datastore.core.DataStore
import com.truongdc.movie.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface UserDataSource {

    suspend fun saveUser(user: User)

    fun getUser(): Flow<User>

    suspend fun clearAll()
}

@Singleton
class UserDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<UserProto>,
) :
    UserDataSource {

    override suspend fun saveUser(user: User) {
        dataStore.updateData { userProto ->
            userProto.toBuilder().setName(user.name).setEmail(user.email).setPassword(user.password)
                .build()
        }
    }

    override fun getUser(): Flow<User> {
        return dataStore.data.map { userProto ->
            User(
                userProto.name,
                userProto.email,
                userProto.password,
            )
        }
    }

    override suspend fun clearAll() {
        dataStore.updateData { userProto ->
            userProto.toBuilder().clear().build()
        }
    }
}