package com.sombrero.cryptomodel.coin

import android.arch.paging.LivePagedListBuilder
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sombrero.cryptomodel.AppDatabase
import com.sombrero.cryptomodel.utils.getValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: CoinDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase::class.java).build()
        dao = database.coinDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getEmptyList() {
        val list = getValue(LivePagedListBuilder(dao.getCoins(), 4).build())
        assertTrue(list != null)
        assertTrue(list?.isEmpty() ?: false)
    }

    @Test
    fun getAllCoins() {
        val coin1 = Coin(1, "coin1", null, null)
        val coin2 = Coin(2, "coin1", null, null)
        val coin3 = Coin(3, "sea", null, null)
        val coin4 = Coin(4, "search", null, null)

        val list = listOf(coin1, coin2, coin3, coin4).sortedBy { it.name }

        dao.insertCoins(list)

        val actualList = getValue(LivePagedListBuilder(dao.getCoins(), 4).build())

        list.forEachIndexed { i, coin ->
            assertEquals(coin, actualList?.get(i))
        }
    }

    @Test
    fun filterCoins() {
        val coin1 = Coin(1, "coin1", null, null)
        val coin2 = Coin(2, "coin1", null, null)
        val coin3 = Coin(3, "sea", null, null)
        val coin4 = Coin(4, "search", null, null)

        val list = listOf(coin1, coin2, coin3, coin4).sortedBy { it.name }

        dao.insertCoins(list)

        val actualList = getValue(LivePagedListBuilder(dao.getCoins("se%"), 4).build())

        assertEquals(actualList?.size, 2)
        assertEquals(actualList?.get(0), coin3)
        assertEquals(actualList?.get(1), coin4)
    }
}