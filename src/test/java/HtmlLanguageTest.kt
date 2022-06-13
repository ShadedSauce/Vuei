import gg.shaded.vuei.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HtmlLanguageTest {
    @Test
    fun testAttributes() {
        val language = HtmlLanguage(TestItemFactory(), SimpleI18n())
        val context = DequeParserContext("""
            <a 
                some-attr="some value" 
                some-other-attr="some other value"
                :some-binding="someVal"
                @some-event="someEvent"
            />
        """)

        val document = language.parse(context).first()

        assertEquals(document.values["some-attr"], "some value")
        assertEquals(document.values["some-other-attr"], "some other value")
        assertEquals(document.bindings["some-binding"], "someVal")
        assertEquals(document.bindings["emit:some-event"], "someEvent")
    }

    @Test
    fun testNesting() {
        val language = HtmlLanguage(TestItemFactory(), SimpleI18n())
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