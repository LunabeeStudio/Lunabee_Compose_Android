package studio.lunabee.compose.core

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString

@Stable
abstract class LbcTextSpec {

    abstract val annotated: AnnotatedString
        @ReadOnlyComposable @Composable
        get

    abstract val string: String
        @ReadOnlyComposable @Composable
        get

    @Composable
    @ReadOnlyComposable
    protected fun Array<out Any>.resolveArgs(): Array<out Any> {
        return Array(this.size) { idx ->
            val entry = this[idx]
            if (entry is LbcTextSpec) {
                entry.string // marker as compile error due to Java(?)
            } else {
                this[idx]
            }
        }
    }

    companion object {
        fun fromString(value: String, vararg params: Any): LbcTextSpec {
            return Raw(value, params)
        }

        fun fromStringRes(
            @StringRes id: Int,
            vararg params: Any,
        ): LbcTextSpec {
            return StringResource(id, params)
        }

        fun fromPluralsRes(
            @PluralsRes id: Int,
            count: Int,
            vararg params: Any,
        ): LbcTextSpec {
            return PluralsResource(id, count, params)
        }

        fun fromAnnotated(value: AnnotatedString): LbcTextSpec {
            return Annotated(value)
        }

        val Empty: LbcTextSpec = fromString(value = "")
    }

    private class Raw(private val value: String, private val args: Array<out Any>) : LbcTextSpec() {
        override val annotated: AnnotatedString
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = if (args.isEmpty()) {
                AnnotatedString(value)
            } else {
                AnnotatedString(value.format(*args.resolveArgs()))
            }

        override val string: String
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = if (args.isEmpty()) {
                value
            } else {
                value.format(*args.resolveArgs())
            }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Raw

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): String {
            return "value = $value"
        }
    }

    private class Annotated(private val value: AnnotatedString) : LbcTextSpec() {
        override val annotated: AnnotatedString
            @Composable
            @ReadOnlyComposable
            get() = value

        override val string: String
            @Composable
            @ReadOnlyComposable
            get() = value.text

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Annotated

            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            return value.hashCode()
        }

        override fun toString(): String {
            return "value = ${value.text}"
        }
    }

    private class StringResource(
        @StringRes val id: Int,
        private val args: Array<out Any>,
    ) : LbcTextSpec() {
        override val annotated: AnnotatedString
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = AnnotatedString(stringResource(id, *args.resolveArgs()))

        override val string: String
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = stringResource(id, *args.resolveArgs())

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringResource

            if (id != other.id) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + args.contentHashCode()
            return result
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private class PluralsResource(
        @PluralsRes private val id: Int,
        private val count: Int,
        private val args: Array<out Any>,
    ) : LbcTextSpec() {

        override val annotated: AnnotatedString
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = AnnotatedString(pluralStringResource(id, count, *args.resolveArgs()))

        override val string: String
            @Composable
            @ReadOnlyComposable
            @Suppress("SpreadOperator")
            get() = pluralStringResource(id, count, *args.resolveArgs())

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PluralsResource

            if (id != other.id) return false
            if (count != other.count) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + count
            result = 31 * result + args.contentHashCode()
            return result
        }
    }
}
