import com.springBoot.scalaTest.controller.HelloWorldController;
import com.taobao.stresstester.core.StressTask;

import static com.taobao.stresstester.StressTestUtils.testAndPrint;


public class StreeTest {

    public static void main(String[] args)
    {
        testAndPrint(10000, 1000000, new StressTask()
        {
            public Object doTask() throws Exception
            {
                HelloWorldController helloWorldController = new HelloWorldController();
                String index = helloWorldController.index();
                /*System.out.println(index);*/
                return null;
            }
        });
    }
}
