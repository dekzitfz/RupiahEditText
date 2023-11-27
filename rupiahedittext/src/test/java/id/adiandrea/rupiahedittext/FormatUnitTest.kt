package id.adiandrea.rupiahedittext

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class FormatUnitTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val editText = RupiahEditText(context)
    @Test
    fun test0Inputed() {
        Assert.assertEquals("0", editText.validateValue("0"))
    }

    @Test
    fun testThousands() {
        Assert.assertEquals("1.234", editText.validateValue("1234"))
    }
}