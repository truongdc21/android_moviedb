package com.truongdc.movie_tmdb.core.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.truongdc.movie_tmdb.core.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

interface UserDataStore {

    suspend fun saveUser(user: User)

    fun getUser(): Flow<User>

    suspend fun clearAll()
}

@Singleton
class UserDataStoreImpl @Inject constructor(@ApplicationContext private val context: Context) :
    UserDataStore {

    companion object {
        private const val FILENAME = "user_proto_data_store_pb"
    }

    private val Context.dataStore: DataStore<UserProto> by dataStore(
        fileName = FILENAME,
        serializer = UserSerialize,
    )

    override suspend fun saveUser(user: User) {
        context.dataStore.updateData { userProto ->
            userProto.toBuilder().setName(user.name).setEmail(user.email).setPassword(user.password)
                .build()
        }
    }

    override fun getUser(): Flow<User> {
        return context.dataStore.data.map { userProto ->
            User(
                userProto.name,
                userProto.email,
                userProto.password
            )
        }
    }

    override suspend fun clearAll() {
        context.dataStore.updateData { userProto ->
            userProto.toBuilder().clear().build()
        }
    }

    private object UserSerialize : Serializer<UserProto> {
        override val defaultValue: UserProto = UserProto.getDefaultInstance()
        override suspend fun readFrom(input: InputStream): UserProto {
            try {
                return UserProto.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: UserProto, output: OutputStream) = t.writeTo(output)
    }

}
