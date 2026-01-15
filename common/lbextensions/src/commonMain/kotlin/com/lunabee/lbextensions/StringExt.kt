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

package com.lunabee.lbextensions

/**
 * Replace matching regex by an empty string
 * @param regex The regular expression to match
 * @return The string with matching regular expression removed
 */
fun String.remove(regex: Regex): String = replace(regex, "")

/**
 * Replace the matching string by an empty string
 * @param string The string to match
 * @return The string with matching string removed
 */
fun String.remove(string: String): String = replace(string, "")
