package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("MYRA KYRA AI", appName)
  }

  @Test
  fun `verify 22 agents and 24 master tools`() {
    assertEquals(22, AppRepository.agents.size)
    assertEquals(24, AppRepository.masterTools.size)
    assertEquals(12, AppRepository.personas.size)
    assertTrue(AppRepository.voices.size >= 40)
  }
}
