import gg.shaded.vuei.DequeParserContext
import gg.shaded.vuei.HtmlLanguage
import gg.shaded.vuei.ItemFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class HtmlLanguageTest {
    @Test
    fun testParser() {
        val language = HtmlLanguage(TestItemFactory())
        val context = DequeParserContext("""
            <a attr="value123">
                <b />
                <c />
                <d>
                    <e />
                    <f />
                    <g />
                </d>
            </a>
        """.trimIndent())

        val document = language.parse(context)

        Assertions.assertEquals(document.count(), 1)

        val children = document.first().children

        Assertions.assertEquals(children.count(), 3)

        Assertions.assertEquals(children[0].children.count(), 0)
        Assertions.assertEquals(children[1].children.count(), 0)
        Assertions.assertEquals(children[2].children.count(), 3)
    }
}

class TestItemFactory: ItemFactory {
    override fun create(type: String, name: String, description: List<String>): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return TestItemStack(material)
    }
}

class TestItemStack(private val material: Material): ItemStack(material) {
    override fun toString(): String {
        return "{type=$material}"
    }

    override fun isSimilar(stack: ItemStack?): Boolean {
        return false
    }
}