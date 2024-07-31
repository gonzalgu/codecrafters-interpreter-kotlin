import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EvalKtTest{
    @Test
    fun testEval(){
        val text = "2 + 3"
        runCommand("evaluate", text)
    }
    @Test
    fun testTrue(){
        val text = "true"
        runCommand("evaluate", text)
    }
    @Test
    fun testFalse(){
        val text = "false"
        runCommand("evaluate", text)
    }
    @Test
    fun testNil(){
        val text = "nil"
        runCommand("evaluate", text)
    }
    @Test
    fun testHello(){
        val text = "\"hello world!\""
        runCommand("evaluate", text)
    }
    @Test
    fun testNumber(){
        val text = "10.40"
        runCommand("evaluate", text)
    }
    @Test
    fun testNumber2(){
        val text = "10"
        runCommand("evaluate", text)
    }
    @Test
    fun testError1(){
        val text = "-\"hello world!\""
        runCommand("evaluate", text)
    }
}