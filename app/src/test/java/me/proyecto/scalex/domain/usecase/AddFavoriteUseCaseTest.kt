package me.proyecto.scalex.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IUserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddFavoriteUseCaseTest {

    private val userRepository: IUserRepository = mockk()
    private lateinit var useCase: AddFavoriteUseCase

    @Before
    fun setUp() {
        useCase = AddFavoriteUseCase(userRepository)
    }

    @Test
    fun `execute returns true when repository succeeds`() = runTest {
        val motorcycle = Motorcycle(make = "Yamaha", model = "MT-07", year = "2023")
        coEvery { userRepository.addFavorite(motorcycle) } returns true

        val result = useCase.execute(motorcycle)

        assertTrue(result)
        coVerify { userRepository.addFavorite(motorcycle) }
    }

    @Test
    fun `execute returns false when repository fails`() = runTest {
        val motorcycle = Motorcycle(make = "Honda", model = "CBR500R", year = "2023")
        coEvery { userRepository.addFavorite(motorcycle) } returns false

        val result = useCase.execute(motorcycle)

        assertFalse(result)
    }

    @Test
    fun `remove calls repository removeFavorite`() = runTest {
        val motorcycleId = "honda_cbr500r_2023"
        coEvery { userRepository.removeFavorite(motorcycleId) } returns true

        val result = useCase.remove(motorcycleId)

        assertTrue(result)
        coVerify { userRepository.removeFavorite(motorcycleId) }
    }

    @Test
    fun `isFavorite calls repository isFavorite`() = runTest {
        val motorcycleId = "yamaha_mt07_2023"
        coEvery { userRepository.isFavorite(motorcycleId) } returns true

        val result = useCase.isFavorite(motorcycleId)

        assertTrue(result)
    }

    @Test
    fun `getAll returns favorites from repository`() = runTest {
        val favorites = listOf(
            Motorcycle(make = "Kawasaki", model = "Ninja 400", year = "2023")
        )
        coEvery { userRepository.getAllFavorites() } returns favorites

        val result = useCase.getAll()

        assertEquals(1, result.size)
        assertEquals("Kawasaki", result[0].make)
    }

    @Test
    fun `toggle calls repository toggleFavorite`() = runTest {
        val motorcycle = Motorcycle(make = "Suzuki", model = "SV650", year = "2023")
        coEvery { userRepository.toggleFavorite(motorcycle) } returns true

        val result = useCase.toggle(motorcycle)

        assertTrue(result)
        coVerify { userRepository.toggleFavorite(motorcycle) }
    }

    @Test
    fun `getIds returns set of favorite IDs`() = runTest {
        val ids = setOf("yamaha_mt07_2023", "honda_cbr500r_2023")
        coEvery { userRepository.getFavoriteIds() } returns ids

        val result = useCase.getIds()

        assertEquals(2, result.size)
        assertTrue(result.contains("yamaha_mt07_2023"))
    }
}
