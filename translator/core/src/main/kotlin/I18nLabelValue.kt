/*
 * Copyright (C) 2017/2021 e-voyageurs technologies
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

package ai.tock.translator

/**
 * A label value contains an unique key used to retrieve the translations from the database.
 *
 * There is also a default label used to generate the label if no translation is found, and optional
 * format pattern arguments for this current translation.
 */
class I18nLabelValue constructor(
    /**
     * Unique key of the label.
     */
    val key: String,
    /**
     * Namespace of the label.
     */
    namespace: String,
    /**
     * Category of the label.
     */
    category: String,
    /**
     * The default label if no translation is found.
     */
    val defaultLabel: CharSequence,
    /**
     * The optional format pattern arguments.
     */
    val args: List<Any?> = emptyList()
) : CharSequence by defaultLabel {

    constructor(label: I18nLabel) :
        this(
            label._id.toString(),
            label.namespace,
            label.category,
            label.i18n.last { it.interfaceType == UserInterfaceType.textChat }.label
        )

    /**
     * Namespace of the label.
     */
    val namespace: String = namespace.lowercase()
    /**
     * Category of the label.
     */
    val category: String = category.lowercase()

    /**
     * Returns the value with the given namespace.
     */
    fun withNamespace(newNamespace: String): I18nLabelValue =
        I18nLabelValue(key.replaceBefore("_", newNamespace), newNamespace, category, defaultLabel, args)

    override fun toString(): String {
        return defaultLabel.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as I18nLabelValue

        if (defaultLabel != other.defaultLabel) return false
        if (args != other.args) return false
        if (key != other.key) return false
        if (namespace != other.namespace) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = defaultLabel.hashCode()
        result = 31 * result + args.hashCode()
        result = 31 * result + key.hashCode()
        result = 31 * result + namespace.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}
