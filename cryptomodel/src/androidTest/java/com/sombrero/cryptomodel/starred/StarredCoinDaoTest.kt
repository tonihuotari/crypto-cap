package com.sombrero.cryptomodel.starred

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sombrero.cryptomodel.AppDatabase
import com.sombrero.cryptomodel.ticker.TickerCoin
import com.sombrero.cryptomodel.ticker.TickerCoinDao
import com.sombrero.cryptomodel.utils.getValue
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StarredCoinDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var starredDao: StarredCoinDao
    private lateinit var tickerDao: TickerCoinDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase::class.java).build()
        starredDao = database.starredDao()
        tickerDao = database.tickerDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertStarredCoin() {
        val expectedId = 12L
        val exptectedCoin = StarredCoin(expectedId)

        starredDao.insertStarredCoin(exptectedCoin)

        val actualId = getValue(starredDao.getStarredCoin(expectedId))?.coinId
        assertEquals(expectedId, actualId)
    }

    @Test
    fun insertStarredCoinAndTickerCoin() {
        val expectedId = 12L
        val exptectedCoin = StarredCoin(expectedId)

        starredDao.insertStarredCoin(exptectedCoin)

        val coinWithoutTicker = starredDao.getStarredCoin(expectedId)
        // Making sure TickerCoin has not been added yet
        assertNull(getValue(coinWithoutTicker)?.coin)

        val expectedTickerCoin = TickerCoin(expectedId, "Coin", "Symbol", 12, 12.00, 12.00, 12.00, 12.00, 12.00, 12.00)
        tickerDao.insertTicker(expectedTickerCoin)

        val actualTickerCoin = getValue(tickerDao.getSpecific(expectedId))
        assertEquals(expectedTickerCoin, actualTickerCoin)

        val actualCoin = starredDao.getStarredCoin(expectedId)
        assertEquals(expectedTickerCoin, getValue(actualCoin)?.coin)
    }


}