package goodmetrics

/**
 * Not thread safe.
 */
class Metrics internal constructor(
    internal val name: String,
    internal var timestampMillis: Long,
    internal val startNanoTime: Long,
) {
    sealed interface Dimension {
        val name: String
        val value: Any
        data class StringDimension(override val name: String, override val value: String): Dimension
        data class NumberDimension(override val name: String, override val value: Long): Dimension
        data class BooleanDimension(override val name: String, override val value: Boolean): Dimension
    }
    internal val metricMeasurements: MutableMap<String, Number> = mutableMapOf()
    internal val metricDistributions: MutableMap<String, Long> = mutableMapOf()
    internal val metricDimensions: MutableMap<String, Dimension> = mutableMapOf()

    fun dimension(dimension: String, value: Boolean) {
        metricDimensions[dimension] = Dimension.BooleanDimension(dimension, value)
    }

    fun dimension(dimension: String, value: Long) {
        metricDimensions[dimension] = Dimension.NumberDimension(dimension, value)
    }

    fun dimension(dimension: String, value: String) {
        metricDimensions[dimension] = Dimension.StringDimension(dimension, value)
    }

    fun measure(name: String, value: Long) {
        metricMeasurements[name] = value
    }

    fun measure(name: String, value: Int) {
        metricMeasurements[name] = value.toLong()
    }

    fun measure(name: String, value: Double) {
        metricMeasurements[name] = value
    }

    fun measure(name: String, value: Float) {
        metricMeasurements[name] = value.toDouble()
    }

    fun distribution(name: String, value: Long) {
        metricDistributions[name] = value
    }

    fun distribution(name: String, value: Int) {
        metricDistributions[name] = value.toLong()
    }
}
