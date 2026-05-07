package me.proyecto.scalex.domain.usecase

import me.proyecto.scalex.domain.model.Motorcycle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CompareMotorcyclesUseCaseTest {

    private lateinit var useCase: CompareMotorcyclesUseCase

    @Before
    fun setUp() {
        useCase = CompareMotorcyclesUseCase()
    }

    @Test
    fun `execute returns comparison result with both motorcycles`() {
        val moto1 = Motorcycle(make = "Yamaha", model = "MT-07", year = "2023")
        val moto2 = Motorcycle(make = "Kawasaki", model = "Z650", year = "2023")

        val result = useCase.execute(moto1, moto2)

        assertNotNull(result)
        assertEquals(moto1, result.motorcycle1)
        assertEquals(moto2, result.motorcycle2)
    }

    @Test
    fun `execute includes differences map`() {
        val moto1 = Motorcycle(make = "Honda", model = "CBR500R", year = "2023", displacement = "500ccm")
        val moto2 = Motorcycle(make = "Yamaha", model = "YZF-R3", year = "2023", displacement = "321ccm")

        val result = useCase.execute(moto1, moto2)

        assertTrue(result.differences.containsKey("Cilindrada"))
        assertEquals("500ccm", result.differences["Cilindrada"]?.first)
        assertEquals("321ccm", result.differences["Cilindrada"]?.second)
    }

    @Test
    fun `findSimilar returns limited results`() {
        val base = Motorcycle(make = "Yamaha", model = "MT-07", year = "2023")
        val candidates = listOf(
            Motorcycle(make = "Kawasaki", model = "Z650", year = "2023"),
            Motorcycle(make = "Honda", model = "CB650R", year = "2023"),
            Motorcycle(make = "Suzuki", model = "SV650", year = "2023"),
            Motorcycle(make = "BMW", model = "F900R", year = "2023"),
            Motorcycle(make = "Ducati", model = "Monster", year = "2023")
        )

        val result = useCase.findSimilar(base, candidates, limit = 3)

        assertTrue(result.size <= 3)
    }

    @Test
    fun `similarity score between identical motorcycles is positive`() {
        val moto = Motorcycle(
            make = "Yamaha",
            model = "MT-07",
            year = "2023",
            totalLength = "2085 mm",
            totalWidth = "780 mm",
            totalHeight = "1105 mm",
            totalWeight = "184 kg",
            displacement = "689 ccm",
            power = "73.4 HP"
        )

        val result = useCase.execute(moto, moto)

        assertTrue(result.similarityScore >= 0.0)
    }
}
