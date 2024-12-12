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
package com.truongdc.movie.core.datastore.serializes

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.truongdc.movie.core.datastore.AppStateProto
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class AppStateSerialize @Inject constructor() : Serializer<AppStateProto> {
    override val defaultValue: AppStateProto = AppStateProto.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): AppStateProto {
        try {
            return AppStateProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AppStateProto, output: OutputStream) = t.writeTo(output)
}
