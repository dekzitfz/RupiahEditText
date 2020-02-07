package id.adiandrea.rupiahedittext;

import android.content.Context;
import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.test.core.app.ApplicationProvider;
import static org.junit.Assert.assertEquals;

@Config(sdk = Build.VERSION_CODES.O_MR1)
@RunWith(RobolectricTestRunner.class)
public class FormatUnitTest {

    private Context context = ApplicationProvider.getApplicationContext();
    private RupiahEditText editText = new RupiahEditText(context);

    @Test
    public void test0Inputed(){
        assertEquals("0", editText.validateValue("0"));
    }

    @Test
    public void testThousands(){
        assertEquals("1.234", editText.validateValue("1234"));
    }

}
