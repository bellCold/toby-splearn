package tobyspring.splearn

import org.assertj.core.api.AssertProvider
import org.springframework.test.json.JsonPathValueAssert
import java.util.function.Consumer


class AssertThatUtils {
    companion object {
        fun notNull(): Consumer<AssertProvider<JsonPathValueAssert>> {
            return Consumer { provider ->
                provider.assertThat().isNotNull()
            }
        }

        fun equalsTo(request: String): Consumer<AssertProvider<JsonPathValueAssert>> {
            return Consumer { provider ->
                provider.assertThat().isEqualTo(request)
            }
        }
    }
}