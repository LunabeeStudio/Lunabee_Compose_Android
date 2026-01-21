/*
 * Copyright (c) 2026 Lunabee Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package studio.lunabee.extension

/**
 *  As default Lazy use SynchronizedLazyImpl (which correspond to LazyThreadSafetyMode.SYNCHRONIZED mode),
 *  so if you use it in this way you are going to be thread safe.
 *
 *  In case you are using this in the Main Thread and you are sure that it’s not going to be used
 *  in different threads, then you can avoid all of this overhead to make it Thread Safe and just
 *  use this [lazyFast] function
 *
 *  @see LazyThreadSafetyMode
 *  @see Lazy
 */
fun <T> lazyFast(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
