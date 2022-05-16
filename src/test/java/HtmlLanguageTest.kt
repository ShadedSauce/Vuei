import gg.shaded.vuei.DequeParserContext
import gg.shaded.vuei.HtmlLanguage
import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.TestItemFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HtmlLanguageTest {
    @Test
    fun testAttributes() {
        val language = HtmlLanguage(TestItemFactory())
        val context = DequeParserContext("""
            <a some-attr="some value" some-other-attr="some other value" />
        """.trimIndent())

        val document = language.parse(context).first()

        assertEquals(document.values["some-attr"], "some value")
        assertEquals(document.values["some-other-attr"], "some other value")
    }

    @Test
    fun testArrays() {
        val language = HtmlLanguage(TestItemFactory())
        val context = DequeParserContext("""
            <tag array="['some value', 'some other value']" />
        """.trimIndent())

        val document = language.parse(context).first()
        val array = document.values["array"] as? List<String>

        assertNotNull(array)
        assertEquals(array, listOf("some value", "some other value"))
    }

    @Test
    fun testNesting() {
        val language = HtmlLanguage(TestItemFactory())
        val context = DequeParserContext("""
            <parent>
                <child>
                    <grand-child />
                    <grand-child />
                    <grand-child />
                </child>
            </parent>
        """.trimIndent())

        val document = language.parse(context).first()

        assertEquals(document.children.count(), 1)
        assertEquals(document.children.first().children.count(), 3)
        assertEquals(document.children.first().children.flatMap { it.children }.count(), 0)
    }
}