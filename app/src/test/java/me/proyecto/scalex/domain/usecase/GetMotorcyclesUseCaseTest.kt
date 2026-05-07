package me.proyecto.scalex.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import me.proyecto.scalex.core.Result
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IMotorcycleRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMotorcyclesUseCaseTest {

    private val repository: IMotorcycleRepository = mockk()
    private lateinit var useCase: GetMotorcyclesUseCase

    @Before
    fun setUp() {
        useCase = GetMotorcyclesUseCase(repository)
    }

    @Test
    fun `byModel returns motorcycles when repository succeeds`() = runTest {
        val motorcycles = listOf(
            Motorcycle(make = "Yamaha", model = "MT-07", year = "2023"),
            Motorcycle(make = "Yamaha", model = "MT-09", year = "2023")
        )
        coEvery { repository.searchByModel("MT") } returns Result.Success(motorcycles)

        val result = useCase.byModel("MT")

        assertTrue(result is Result.Success)
        assertEquals(2, (result as Result.Success).data.size)
    }

    @Test
    fun `byModel returns error when repository fails`() = runTest {
        coEvery { repository.searchByModel("FAKE") } returns Result.Error(Exception("API Error"))

        val result = useCase.byModel("FAKE")

        assertTrue(result is Result.Error)
    }

    @Test
    fun `byMake returns motorcycles`() = runTest {
        val motorcycles = listOf(
            Motorcycle(make = "Honda", model = "CBR500R", year = "2023")
        )
        coEvery { repository.searchByMake("Honda") } returns Result.Success(motorcycles)

        val result = useCase.byMake("Honda")

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
    }

    @Test
    fun `byYear returns motorcycles`() = runTest {
        val motorcycles = listOf(
            Motorcycle(make = "Kawasaki", model = "Ninja 400", year = "2023")
        )
        coEvery { repository.searchByYear("2023") } returns Result.Success(motorcycles)

        val result = useCase.byYear("2023")

        assertTrue(result is Result.Success)
    }
}
